package com.riseuplabs.ureport_r4v.adapter.result;

import android.content.Context;

import androidx.annotation.NonNull;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemPollAgeParentBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByAge;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByGender;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

public class PollGenderAdapter extends BaseRecyclerViewAdapter<ModelResultsByGender, ItemPollAgeParentBinding> {

    Context context;
    int set;

    public PollGenderAdapter(Context context, int set) {
        this.context = context;
        this.set = set;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_poll_age_parent;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemPollAgeParentBinding> holder, int position) {

        prefManager = new SharedPrefManager(context);
        holder.binding.label.setText(items.get(position).label);
        holder.binding.recyclerViewParent.setHasFixedSize(true);
        PollAgeChildAdapter mAdapter = new PollAgeChildAdapter(context,items.get(position).set);
        holder.binding.recyclerViewParent.setAdapter(mAdapter);

        mAdapter.addItems(items.get(position).categories);

    }
}
