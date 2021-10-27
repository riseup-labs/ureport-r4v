package com.riseuplabs.ureport_r4v.ui.results.polls;

import android.content.Context;
import android.view.View;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.PollStatisticsAdapter;
import com.riseuplabs.ureport_r4v.adapter.PollTitlesAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemPollStatisticsBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestion;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionCategory;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.util.List;

public class SetupPollStatistics {

    public static void setUpStatistics(Context context, SharedPrefManager prefManager, ModelQuestion item, ItemPollStatisticsBinding binding) {
        List<ModelQuestionCategory> category = item.results.categories;

        binding.recyclerView.setHasFixedSize(true);
        PollStatisticsAdapter mAdapter = new PollStatisticsAdapter(context,item.results.set);
        binding.recyclerView.setAdapter(mAdapter);

        mAdapter.addItems(category);

    }


}
