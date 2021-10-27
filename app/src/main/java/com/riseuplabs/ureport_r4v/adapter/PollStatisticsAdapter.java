package com.riseuplabs.ureport_r4v.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemPollStatisticsProgressbarBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionCategory;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

public class PollStatisticsAdapter extends BaseRecyclerViewAdapter<ModelQuestionCategory, ItemPollStatisticsProgressbarBinding> {

    Context context;
    int set;

    public PollStatisticsAdapter(Context context, int set) {
        this.context = context;
        this.set = set;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_poll_statistics_progressbar;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemPollStatisticsProgressbarBinding> holder, int position) {

        prefManager = new SharedPrefManager(context);

        holder.binding.textViewLabel1.setText(items.get(position).label);
        holder.binding.rounded1.setMax(set);
        holder.binding.rounded1.setProgress(items.get(position).count);
        int percentageTemp = 0;
        if (set > 0) {
            percentageTemp = (items.get(position).count * 100) / set;
        }
        holder.binding.textViewPercentage.setText("" + percentageTemp + "%");


        switch(prefManager.getInt(PrefKeys.ORG_ID)){
            case AppConstant.BRAZIL_ORG_ID :
                holder.binding.rounded1.setProgressColor(context.getResources().getColor(R.color.color_brasil));
                break;
            case AppConstant.ECUADOR_ORG_ID :
                holder.binding.rounded1.setProgressColor(context.getResources().getColor(R.color.color_ecuador));
                break;
            case AppConstant.BOLIVIA_ORG_ID :
                holder.binding.rounded1.setProgressColor(context.getResources().getColor(R.color.color_bolivia));
                break;

        }
    }
}
