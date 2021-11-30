package com.riseuplabs.ureport_r4v.ui.results.polls;

import android.content.Context;
import android.view.View;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.result.PollAgeAdapter;
import com.riseuplabs.ureport_r4v.adapter.result.PollGenderAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemPollGenderBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestion;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionCategory;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByAge;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByGender;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.util.List;

public class SetupGenderResult {

    public static void setupGenderResult(Context context, SharedPrefManager prefManager, ModelQuestion item, ItemPollGenderBinding binding) {

        List<ModelResultsByGender> results_by_gender = item.results_by_gender;

        binding.recyclerView.setHasFixedSize(true);
        PollGenderAdapter mAdapter = new PollGenderAdapter(context,item.results.set);
        binding.recyclerView.setAdapter(mAdapter);

        mAdapter.addItems(results_by_gender);

    }


}
