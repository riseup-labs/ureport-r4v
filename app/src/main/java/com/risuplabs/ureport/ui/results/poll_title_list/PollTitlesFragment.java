package com.risuplabs.ureport.ui.results.poll_title_list;


import android.os.Bundle;
import android.util.Log;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.adapter.PollTitlesAdapter;
import com.risuplabs.ureport.base.BaseFragment;
import com.risuplabs.ureport.databinding.FragmentPollTitlesBinding;
import com.risuplabs.ureport.ui.results.ResultsViewModel;
import com.risuplabs.ureport.utils.IntentConstant;
import com.risuplabs.ureport.utils.pref_manager.PrefKeys;

import javax.inject.Inject;

public class PollTitlesFragment extends BaseFragment<FragmentPollTitlesBinding> {

    PollTitlesAdapter mAdapter;
    String categoryName = "";
    int org_id = 0;

    @Inject
    ResultsViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_poll_titles;
    }

    @Override
    public void onViewReady() {

        org_id = prefManager.getInt(PrefKeys.ORG_ID);
        initRecyclerView();

        Bundle b = getActivity().getIntent().getBundleExtra(IntentConstant.INTENT_DATA);
        categoryName  = b.getString(IntentConstant.CATEGORY_NAME);

        viewModel.getPollTitlesFromLocal(categoryName,org_id).observe(getViewLifecycleOwner(), response->{
            Log.d(TAG, "onViewReady: "+response.size());
            mAdapter.addItems(response);
        });


    }
    public void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        mAdapter = new PollTitlesAdapter(getContext());
        binding.recyclerView.setAdapter(mAdapter);
    }

}