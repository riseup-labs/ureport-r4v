package com.risuplabs.ureport_r4v.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.risuplabs.ureport_r4v.databinding.ItemPollTitleBinding;
import com.risuplabs.ureport_r4v.databinding.ItemResultCategoryBinding;
import com.risuplabs.ureport_r4v.model.results.ModelPolls;
import com.risuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;

public class PollTitlesAdapter extends BaseRecyclerViewAdapter<ModelPolls, ItemPollTitleBinding> {

    Context context;

    public PollTitlesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_poll_title;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemPollTitleBinding> holder, int position) {
        holder.binding.topicTitle.setText(items.get(position).title);
        holder.binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt(IntentConstant.POLL_ID,items.get(position).id);
                b.putString(IntentConstant.ACTIVITY_NAME,items.get(position).title);
                Navigator.navigateWithBundle(context, PollsActivity.class,IntentConstant.INTENT_DATA,b);
            }
        });
    }
}
