package com.riseuplabs.ureport_r4v.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemDownloadFlowListBinding;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class FlowDownloadListAdapter extends BaseRecyclerViewAdapter<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow, ItemDownloadFlowListBinding> {

    private final Context context;
    private final List<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow> checkedList = new ArrayList<>();
    private List<Flow> selectedList = new ArrayList<>();
    List<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow> new_pre_selected_flows = new ArrayList<>();

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


        holder.binding.checkbox.setChecked(items.get(position).isSelected());
        if(items.get(position).isSelected()){
            checkedList.add(items.get(position));
        }

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
