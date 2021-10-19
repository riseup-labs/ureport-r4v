package com.risuplabs.ureport_r4v.ui.results.polls;

import androidx.annotation.Nullable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.richpath.RichPath;
import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.adapter.PollsAdapter;
import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityPollsNewBinding;
import com.risuplabs.ureport_r4v.model.results.ModelPolls;
import com.risuplabs.ureport_r4v.model.results.ModelResultsByLocation;
import com.risuplabs.ureport_r4v.network.data.ApiConstants;
import com.risuplabs.ureport_r4v.ui.results.ResultsViewModel;
import com.risuplabs.ureport_r4v.ui.results.search.ResultsSearchActivity;
import com.risuplabs.ureport_r4v.utils.AppConstant;
import com.risuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.risuplabs.ureport_r4v.utils.DateFormatUtils;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;
import com.risuplabs.ureport_r4v.utils.StaticMethods;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.risuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class PollsActivity extends BaseActivity<ActivityPollsNewBinding> {

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
        return R.layout.activity_polls_new;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getAllCategoriesCountFromLocal(org_id).observe(PollsActivity.this, count -> {
            int pollCount = count;
            if (pollCount == 0) {
                visible(binding.emptyListWarning);
                if(ConnectivityCheck.isConnected(PollsActivity.this)) {
                    binding.progressBar.setIndeterminate(true);
                    binding.loadingTvTitle.setText(R.string.fetch_polls);
                    visible(binding.loadingLayout);
                    gone(binding.noInternetLayout);
                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    getRemoteData(ApiConstants.POLLS+org_id + "/?limit=30/featured");
                }else{
                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    visible(binding.noInternetLayout);
                    gone(binding.loadingLayout);
                }
            }else{
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

            if(count != 0){
                visible(binding.mainLayout);
                if (id == 0) {

                    viewModel.getLatestQuestionsFromLocal(org_id).observe(this, response -> {
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

                            switch (prefManager.getString(PrefKeys.ORG_LABEL)) {
                                case AppConstant.BRAZIL_LABEL: {
                                    RichPath initialRichPath = binding.richPathView.findRichPathByName("Brasil");
                                    highlight(model, pathStack, initialRichPath, binding, PollsActivity.this);
                                    break;
                                }
                                case AppConstant.ECUADOR_LABEL: {
                                    RichPath initialRichPath = binding.richPathView.findRichPathByName("Ecuador");
                                    highlight(model, pathStack, initialRichPath, binding, PollsActivity.this);
                                    break;
                                }
                                case AppConstant.BOLIVIA_LABEL: {
                                    RichPath initialRichPath = binding.richPathView.findRichPathByName("Bolivia");
                                    highlight(model, pathStack, initialRichPath, binding, PollsActivity.this);
                                    break;
                                }
                            }


                            binding.richPathView.setOnPathClickListener(richPath -> {
                                if (richPath.getName() != null) {
                                    highlight(model, pathStack, richPath, binding, PollsActivity.this);
                                }
                            });

                        } else {
                            binding.femaleNumber.setText("---");
                            binding.maleNumber.setText("---");

                            binding.femalePercent.setText("0 %");
                            binding.malePercent.setText("0 %");
                        }


                    });
                } else {
                    viewModel.getQuestionsFromLocal(id, org_id).observe(this, response -> {

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

                            switch (prefManager.getString(PrefKeys.ORG_LABEL)) {
                                case AppConstant.BRAZIL_LABEL: {
                                    RichPath initialRichPath = binding.richPathView.findRichPathByName("Brasil");
                                    highlight(model, pathStack, initialRichPath, binding, PollsActivity.this);
                                    break;
                                }
                                case AppConstant.ECUADOR_LABEL: {
                                    RichPath initialRichPath = binding.richPathView.findRichPathByName("Ecuador");
                                    highlight(model, pathStack, initialRichPath, binding, PollsActivity.this);
                                    break;
                                }
                                case AppConstant.BOLIVIA_LABEL: {
                                    RichPath initialRichPath = binding.richPathView.findRichPathByName("Bolivia");
                                    highlight(model, pathStack, initialRichPath, binding, PollsActivity.this);
                                    break;
                                }
                            }


                            binding.richPathView.setOnPathClickListener(richPath -> {
                                if (richPath.getName() != null) {
                                    highlight(model, pathStack, richPath, binding, PollsActivity.this);
                                }
                            });

                        } else {
                            binding.femaleNumber.setText("---");
                            binding.maleNumber.setText("---");

                            binding.femalePercent.setText("0 %");
                            binding.malePercent.setText("0 %");
                        }


                    });
                }
            }else{
                gone(binding.mainLayout);
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

    }

    public void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        mAdapter = new PollsAdapter(this);
        binding.recyclerView.setAdapter(mAdapter);
    }

    public void saveData(){

        viewModel.response.observe(PollsActivity.this, response -> {

            list.addAll(response.data.results);
            String next_url = response.data.next;
            count = response.data.count;
            progressValue += 30;
            binding.progressBar.setIndeterminate(false);
            binding.progressBar.setProgress(progressValue);
            binding.progressBar.setMax(count);
            binding.loadingTvProgress.setText("("+progressValue+"/"+count+")");
            if(next_url != null){
                getRemoteData(next_url);
            }else{
                progressValue = 0;
                gone(binding.loadingLayout);
                binding.loadingTvTitle.setText("");
                binding.loadingTvProgress.setText("");
                for(int i = 0; i < list.size() ; i++){
                    list.get(i).category_tag = list.get(i).category.name;
                    viewModel.insertPolls(list.get(i));
                }
                String tag = PrefKeys.LAST_LOCAL_POLL_UPDATE_TIME + org_id;
                StaticMethods.setLocalUpdateDate(prefManager, tag);
            }
        });
    }

    @Override
    public void onBackPressed() {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        super.onBackPressed();
    }

    public static void highlight(
            List<ModelResultsByLocation> model,
            RichPath[] pathStack,
            RichPath richPath,
            ActivityPollsNewBinding binding,
            Context context) {
        float scalex = 1.005f;
        pathStack[1] = richPath;
        binding.countryName.setText(richPath.getName().toUpperCase());
        richPath.setScaleX(scalex);
        richPath.setFillColor(context.getResources().getColor(R.color.colorPrimary));
        if (pathStack[0] != null) {
            if (pathStack[0] == pathStack[1]) {
                pathStack[0].setFillColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                pathStack[0].setFillColor(context.getResources().getColor(R.color.mapColor));
            }
            //saving previous view
            pathStack[0] = pathStack[1];
        } else {
            //Insert initial view
            pathStack[0] = pathStack[1];
        }
        for (int i = 0; i < model.size(); i++) {
            if (model.get(i).label.equals(richPath.getName())) {
                setLocationData(binding, model.get(i), context);
            }
        }

    }

    public static void setLocationData(ActivityPollsNewBinding binding, ModelResultsByLocation model, Context context) {
        int responses = model.set;
        binding.countryName.setText(String.valueOf(responses));
        if (responses > 0) {
            int total_response = model.set + model.unset;
            int set = model.set;
            binding.countryName.setText(model.label);
            binding.respondedCountry.setText(set + " " + context.getResources().getString(R.string.respondents_small) + " // " + total_response + " " + context.getResources().getString(R.string.surveyed));
        } else {
            binding.countryName.setText("---");
            binding.respondedCountry.setText("---");
        }
    }

    public void getRemoteData(String url) {
        viewModel.getResults(url);
    }

    public void refresh() {
        if(ConnectivityCheck.isConnected(PollsActivity.this)) {
            ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
            ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1000).start();
            visible(binding.loadingLayout);
            gone(binding.noInternetLayout);
            binding.loadingTvTitle.setText(R.string.updating_polls);
            binding.progressBar.setIndeterminate(true);
            getRemoteData(ApiConstants.POLLS+org_id + "/?limit=30");
        }else{
            ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
            ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
            visible(binding.noInternetLayout);
            gone(binding.loadingLayout);
        }
    }

}