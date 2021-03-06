package com.riseuplabs.ureport_r4v.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.riseuplabs.ureport_r4v.databinding.ItemResultCategoryBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelPollCategory;
import com.riseuplabs.ureport_r4v.ui.results.poll_title_list.PollTitlesActivity;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

public class ResultCategoryAdapter extends BaseRecyclerViewAdapter<ModelPollCategory, ItemResultCategoryBinding> {

    Context context;

    public ResultCategoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_result_category;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemResultCategoryBinding> holder, int position) {
        prefManager = new SharedPrefManager(context);
        if(position == 0){
            int org_id = prefManager.getInt(PrefKeys.ORG_ID);
            String tag = PrefKeys.LAST_LOCAL_POLL_UPDATE_TIME+org_id;
            String last_update_date = prefManager.getString(tag);
            if(last_update_date.equals("")){
                holder.binding.rootLayout.setVisibility(View.GONE);
                holder.binding.ureportLastUpdate.setVisibility(View.GONE);
            }else{
                holder.binding.rootLayout.setVisibility(View.GONE);
                holder.binding.ureportLastUpdate.setVisibility(View.VISIBLE);
                holder.binding.ureportLastUpdate.setText(context.getResources().getString(R.string.last_update)+": "+last_update_date+"\n"+context.getResources().getString(R.string.please_refresh));
            }

        }else{
            ModelPollCategory category = items.get(position-1);
            holder.binding.topicTitle.setText(category.name);
            holder.binding.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString(IntentConstant.CATEGORY_NAME,category.name);
                    Navigator.navigateWithBundle(context, PollTitlesActivity.class,IntentConstant.INTENT_DATA,b);
                }
            });
        }

    }

    public int getItemCount() {
        return items.size()+1;
    }
}
