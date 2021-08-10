package com.risuplabs.ureport_r4v.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.risuplabs.ureport_r4v.databinding.ItemDownloadFlowListBinding;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Flow;
import com.risuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class FlowDownloadListAdapter extends BaseRecyclerViewAdapter<com.risuplabs.ureport_r4v.surveyor.net.responses.Flow, ItemDownloadFlowListBinding> {

    private final Context context;
    private final List<com.risuplabs.ureport_r4v.surveyor.net.responses.Flow> checkedList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.item_download_flow_list;
    }

    public FlowDownloadListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemDownloadFlowListBinding> holder, int position) {

        prefManager = new SharedPrefManager(context);
        holder.binding.checkbox.setText(items.get(position).getName());
        holder.binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(holder.binding.checkbox.isChecked()){
                items.get(position).setSelected(true);
                checkedList.add(items.get(position));
            }
            else if(!holder.binding.checkbox.isChecked()){
                items.get(position).setSelected(false);
                checkedList.remove(items.get(position));
            }
        });
    }


    public List<Flow> getCheckedList(){
        return checkedList;
    }
}
