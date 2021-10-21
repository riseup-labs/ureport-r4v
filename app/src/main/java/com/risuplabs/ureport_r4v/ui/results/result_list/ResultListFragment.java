package com.risuplabs.ureport_r4v.ui.results.result_list;

import android.animation.ObjectAnimator;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.adapter.ResultCategoryAdapter;
import com.risuplabs.ureport_r4v.base.BaseFragment;
import com.risuplabs.ureport_r4v.databinding.FragmentResultListBinding;
import com.risuplabs.ureport_r4v.model.results.ModelPolls;
import com.risuplabs.ureport_r4v.network.data.ApiConstants;
import com.risuplabs.ureport_r4v.ui.results.ResultsViewModel;
import com.risuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.risuplabs.ureport_r4v.utils.StaticMethods;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ResultListFragment extends BaseFragment<FragmentResultListBinding> {

    ResultCategoryAdapter mAdapter;
    int org_id;
    int progressValue = 0;
    int count = 0;
    List<ModelPolls> list = new ArrayList<>();

    @Inject
    ResultsViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_result_list;
    }

    @Override
    public void onViewReady() {

        org_id = prefManager.getInt(PrefKeys.ORG_ID);

        initRecyclerView();

        saveData();

        viewModel.getAllCategoriesFromLocal(org_id).observe(getViewLifecycleOwner(), categories -> {
            mAdapter.addItems(categories);
        });

        binding.swipeToRefresh.setOnRefreshListener(() -> {
            binding.swipeToRefresh.setRefreshing(false);
            refresh();
        });

        binding.btnRetry.setOnClickListener(v->{
            refresh();
        });

        binding.btnHide.setOnClickListener(v->{
            gone(binding.noInternetLayout);
        });

    }

    public void saveData(){
        viewModel.response.observe(getViewLifecycleOwner(), response -> {
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
    public void onStart() {
        super.onStart();
        viewModel.getAllCategoriesCountFromLocal(org_id).observe(getViewLifecycleOwner(), count -> {
            int pollCount = count;
            if (pollCount == 0) {
                StaticMethods.setLanguage(getActivity(),prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
                visible(binding.emptyListWarning);
                if(ConnectivityCheck.isConnected(getContext())) {
                    binding.progressBar.setIndeterminate(true);
                    binding.loadingTvTitle.setText(R.string.fetch_polls);
                    visible(binding.loadingLayout);
                    gone(binding.noInternetLayout);
                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    getRemoteData(ApiConstants.POLLS+org_id + "/?limit=30");
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

    public void refresh() {
            if(ConnectivityCheck.isConnected(getContext())) {
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

    public void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        mAdapter = new ResultCategoryAdapter(getContext());
        binding.recyclerView.setAdapter(mAdapter);
    }

    public void getRemoteData(String url) {
        viewModel.getResults(url);
    }
}