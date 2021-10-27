package com.riseuplabs.ureport_r4v.ui.results.polls;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.richpath.RichPath;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.PollsAdapter;
import com.riseuplabs.ureport_r4v.base.BaseActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityPollsBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelPolls;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByLocation;
import com.riseuplabs.ureport_r4v.network.data.ApiConstants;
import com.riseuplabs.ureport_r4v.ui.results.ResultsViewModel;
import com.riseuplabs.ureport_r4v.ui.results.search.ResultsSearchActivity;
import com.riseuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.riseuplabs.ureport_r4v.utils.DateFormatUtils;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialog;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialogComponent;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialogInterface;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class PollsActivity extends BaseActivity<ActivityPollsBinding> {

    int id = 0;
    int org_id = 0;
    PollsAdapter mAdapter;
    final RichPath[] pathStack = new RichPath[2];
    int progressValue = 0;
    int count = 0;
    List<ModelPolls> list = new ArrayList<>();

    @Inject
    ResultsViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_polls;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getAllCategoriesCountFromLocal(org_id).observe(PollsActivity.this, count -> {
            int pollCount = count;
            if (pollCount == 0) {
                StaticMethods.setLanguage(this, prefManager.getString(PrefKeys.SELECTED_LANGUAGE, "es"), prefManager.getString(PrefKeys.SELECTED_COUNTRY));
                visible(binding.emptyListWarning);
                if (ConnectivityCheck.isConnected(PollsActivity.this)) {
                    binding.progressBar.setIndeterminate(true);
                    binding.loadingTvTitle.setText(R.string.fetch_polls);
                    visible(binding.loadingLayout);
                    gone(binding.noInternetLayout);
//                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
//                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    getRemoteData("https://"+prefManager.getString(PrefKeys.ORG_LABEL)+".ureport.in/api/v1/"+ApiConstants.POLLS + org_id + "/featured/?limit=30");
                } else {
//                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
//                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    visible(binding.noInternetLayout);
                    gone(binding.loadingLayout);
                }
            } else {
                gone(binding.emptyListWarning);
            }
        });

    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        saveData();

        Bundle b = getIntent().getBundleExtra(IntentConstant.INTENT_DATA);

        if (b != null) {
            id = b.getInt(IntentConstant.POLL_ID);
        }


        org_id = prefManager.getInt(PrefKeys.ORG_ID);

        initRecyclerView();

        viewModel.getAllCategoriesCountFromLocal(org_id).observe(PollsActivity.this, count -> {

            if (count != 0) {
                if (id == 0) {

                    viewModel.getLatestQuestionsFromLocal(org_id).observe(this, response -> {

                        prefManager.putInt(PrefKeys.LATEST_OPINION, response.get(0).id);

                        mAdapter.addItems(response.get(0).questions);

                        binding.pollTitle.setText(response.get(0).title);
                        binding.category.setText(response.get(0).category_tag);
                        try {
                            Log.d("DATE", "getPollDate: " + DateFormatUtils.getPollDate(response.get(0).poll_date));
                            binding.date.setText(DateFormatUtils.getPollDate(response.get(0).poll_date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (response.get(0).questions.size() > 0) {

                            if (response.get(0).questions.get(0).results_by_gender.size() == 3) {
                                int set = response.get(0).questions.get(0).results.set;
                                double total = set + response.get(0).questions.get(0).results.unset;
                                if (total != 0) {
                                    double percent_all = (set / total) * 100;
                                    binding.responseRate.setText((int) Math.round(percent_all) + " %");
                                }

                                binding.totalRespondents.setText(set + "");


                                int male_responded = response.get(0).questions.get(0).results_by_gender.get(0).set;
                                int female_responded = response.get(0).questions.get(0).results_by_gender.get(1).set;
                                int other_responded = response.get(0).questions.get(0).results_by_gender.get(2).set;

                                double gender_total = male_responded + female_responded + other_responded;

                                if (gender_total != 0) {
                                    double percent_male = (male_responded / gender_total) * 100;
                                    binding.malePercent.setText((int) Math.round(percent_male) + " %");
                                    double percent_female = (female_responded / gender_total) * 100;
                                    binding.femalePercent.setText((int) Math.round(percent_female) + " %");
                                    double percent_other = (other_responded / gender_total) * 100;
                                    binding.otherPercent.setText((int) Math.round(percent_other) + " %");
                                } else {
                                    binding.malePercent.setText("-");
                                    binding.femalePercent.setText("-");
                                    binding.otherPercent.setText("-");
                                }

                                binding.femaleNumber.setText(female_responded + "");
                                binding.maleNumber.setText(male_responded + "");
                                binding.otherNumber.setText(other_responded + "");

                                binding.body.setVisibility(View.VISIBLE);
                                binding.stateGenderLayout.setVisibility(View.VISIBLE);
                                binding.stateGenderLayout2.setVisibility(View.GONE);

                            } else {
                                int set = response.get(0).questions.get(0).results.set;
                                double total = set + response.get(0).questions.get(0).results.unset;
                                if (total != 0) {
                                    double percent_all = (set / total) * 100;
                                    binding.responseRate.setText((int) Math.round(percent_all) + " %");
                                }

                                binding.totalRespondents.setText(set + "");

                                int male_responded = response.get(0).questions.get(0).results_by_gender.get(0).set;
                                int female_responded = response.get(0).questions.get(0).results_by_gender.get(1).set;
                                double gender_total = male_responded + female_responded;
                                if (gender_total != 0) {
                                    double percent_male = (male_responded / gender_total) * 100;
                                    binding.malePercent2.setText((int) Math.round(percent_male) + " %");
                                    double percent_female = (female_responded / gender_total) * 100;
                                    binding.femalePercent2.setText((int) Math.round(percent_female) + " %");
                                } else {
                                    binding.malePercent2.setText("-");
                                    binding.femalePercent2.setText("-");
                                }

                                binding.femaleNumber2.setText(female_responded + "");
                                binding.maleNumber2.setText(male_responded + "");
                                binding.body.setVisibility(View.VISIBLE);
                                binding.stateGenderLayout.setVisibility(View.GONE);
                                binding.stateGenderLayout2.setVisibility(View.VISIBLE);
                            }

                        } else {
                            binding.femaleNumber.setText("---");
                            binding.maleNumber.setText("---");

                            binding.femalePercent.setText("0 %");
                            binding.malePercent.setText("0 %");
                            binding.body.setVisibility(View.VISIBLE);
                            binding.stateGenderLayout2.setVisibility(View.VISIBLE);
                        }


                    });
                } else {
                    viewModel.getQuestionsFromLocal(id, org_id).observe(this, response -> {

                        if (response.get(0).id == prefManager.getInt(PrefKeys.LATEST_OPINION)) {
                            binding.textLatest.setVisibility(View.VISIBLE);
                        } else {
                            binding.textLatest.setVisibility(View.GONE);
                        }

                        mAdapter.addItems(response.get(0).questions);

                        binding.pollTitle.setText(response.get(0).title);
                        binding.category.setText(response.get(0).category_tag);
                        try {
                            Log.d("DATE", "getPollDate: " + DateFormatUtils.getPollDate(response.get(0).poll_date));
                            binding.date.setText(DateFormatUtils.getPollDate(response.get(0).poll_date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (response.get(0).questions.size() > 0) {
                            int set = response.get(0).questions.get(0).results.set;
                            double total = set + response.get(0).questions.get(0).results.unset;
                            double percent_all = (set / total) * 100;

                            binding.totalRespondents.setText(set + "");
                            binding.responseRate.setText((int) percent_all + " %");

                            int male_responded = response.get(0).questions.get(0).results_by_gender.get(0).set;
                            int female_responded = response.get(0).questions.get(0).results_by_gender.get(1).set;
                            double gender_total = male_responded + female_responded;
                            double percent_male = (male_responded / gender_total) * 100;
                            double percent_female = (female_responded / gender_total) * 100;

                            binding.femaleNumber.setText(female_responded + "");
                            binding.maleNumber.setText(male_responded + "");

                            binding.femalePercent.setText((int) percent_female + " %");
                            binding.malePercent.setText((int) percent_male + " %");

                            final RichPath[] pathStack = new RichPath[2];
                            List<ModelResultsByLocation> model = response.get(0).questions.get(0).results_by_location;


                        } else {
                            binding.femaleNumber.setText("---");
                            binding.maleNumber.setText("---");

                            binding.femalePercent.setText("0 %");
                            binding.malePercent.setText("0 %");
                        }


                    });
                }
            } else {
                gone(binding.body);
            }

        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.searchLayout.setOnClickListener(v -> {
            Navigator.navigate(this, ResultsSearchActivity.class);
            finish();
        });

        binding.swipeToRefresh.setOnRefreshListener(() -> {
            binding.swipeToRefresh.setRefreshing(false);
            refresh();
        });

    }

    public void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        if (mAdapter == null) {
            mAdapter = new PollsAdapter(this);
            binding.recyclerView.setAdapter(mAdapter);
            binding.recyclerView.setHasFixedSize(true);
        }

    }

    public void saveData() {

        viewModel.response.observe(PollsActivity.this, response -> {

            if(response.statusCode.equals("200")) {
                list.addAll(response.data.results);
                String next_url = response.data.next;
                count = response.data.count;
                progressValue = list.size();
                if (progressValue > response.data.count) {
                    progressValue = response.data.count;
                }
                binding.progressBar.setIndeterminate(false);
                binding.progressBar.setProgress(progressValue);
                binding.progressBar.setMax(count);
                binding.loadingTvProgress.setText("(" + progressValue + "/" + count + ")");
                if (next_url != null) {
                    getRemoteData(next_url);
                } else {
                    progressValue = 0;
                    gone(binding.loadingLayout);
                    binding.loadingTvTitle.setText("");
                    binding.loadingTvProgress.setText("");
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).category_tag = list.get(i).category.name;
                        viewModel.insertPolls(list.get(i));
                    }
                    String tag = PrefKeys.LAST_LOCAL_POLL_UPDATE_TIME + org_id;
                    StaticMethods.setLocalUpdateDate(prefManager, tag);
                }
            }else{
                gone(binding.emptyListWarning);
                gone(binding.loadingLayout);
                new CustomDialog(this).displayCustomDialog(new CustomDialogComponent()
                                .setSubTextVisible(View.VISIBLE)
                                .setSubText(getString(R.string.wait_for_retry))
                                .setMainText(getString(R.string.server_error))
                                .setButtonYes(getString(R.string.retry))
                                .setButtonNo(getString(R.string.cancel)),
                        new CustomDialogInterface() {
                            @Override
                            public void retry() {
                                visible(binding.loadingLayout);
                                getRemoteData("https://"+prefManager.getString(PrefKeys.ORG_LABEL)+".ureport.in/api/v1/"+ApiConstants.POLLS + org_id + "/featured/?limit=30");
                            }

                            @Override
                            public void cancel() {
                                finish();
                            }
                        });
            }

        });


    }

    @Override
    public void onBackPressed() {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        super.onBackPressed();
    }

    public void getRemoteData(String url) {
        viewModel.getResults(url);
    }

    public void refresh() {
        if (ConnectivityCheck.isConnected(PollsActivity.this)) {
//            ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
//            ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1000).start();
            visible(binding.loadingLayout);
            gone(binding.noInternetLayout);
            binding.loadingTvTitle.setText(R.string.updating_polls);
            binding.progressBar.setIndeterminate(true);
            getRemoteData("https://"+prefManager.getString(PrefKeys.ORG_LABEL)+".ureport.in/api/v1/"+ApiConstants.POLLS + org_id + "/featured/?limit=30");
        } else {
//            ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
//            ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
            visible(binding.noInternetLayout);
            gone(binding.loadingLayout);
        }
    }

}