package com.riseuplabs.ureport_r4v.ui.results.polls;

import android.content.Context;
import android.view.View;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.result.PollAgeAdapter;
import com.riseuplabs.ureport_r4v.adapter.result.PollStatisticsAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemPollAgeBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestion;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionCategory;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionResult;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByAge;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.util.List;


public class SetupAgeResult {

    public static void setUpAge(Context context, SharedPrefManager prefManager, ModelQuestion item, ItemPollAgeBinding binding) {

        List<ModelResultsByAge> results_by_age = item.results_by_age;

        binding.recyclerView.setHasFixedSize(true);
        PollAgeAdapter mAdapter = new PollAgeAdapter(context,item.results.set);
        binding.recyclerView.setAdapter(mAdapter);

        mAdapter.addItems(results_by_age);

    }


}
