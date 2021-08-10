package com.risuplabs.ureport_r4v.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseRecyclerViewAdapter;
import com.risuplabs.ureport_r4v.databinding.ItemStoriesBinding;
import com.risuplabs.ureport_r4v.model.story.ModelStory;
import com.risuplabs.ureport_r4v.ui.stories.details.StoryDetailsActivity;
import com.risuplabs.ureport_r4v.utils.DateFormatUtils;
import com.risuplabs.ureport_r4v.utils.ImageDownloader;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.risuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.text.ParseException;

import static com.risuplabs.ureport_r4v.utils.ShowUtils.loadImage;

public class StoryListAdapter extends BaseRecyclerViewAdapter<ModelStory, ItemStoriesBinding> {

    private final Context context;
    LifecycleOwner owner;
    int org_id;
    ImageDownloader downloader;

    @Override
    public int getLayoutId() {
        return R.layout.item_stories;
    }

    public StoryListAdapter(LifecycleOwner owner,Context context,int org_id) {
        this.context = context;
        this.owner = owner;
        this.org_id = org_id;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemStoriesBinding> holder, int position) {

        prefManager = new SharedPrefManager(context);
        downloader = new ImageDownloader();

        if(position == 0){
            String tag =PrefKeys.LAST_LOCAL_STORY_UPDATE_TIME+org_id;
            String last_update_date = prefManager.getString(tag);
            if(last_update_date.equals("")){
                holder.binding.rootLayout.setVisibility(View.GONE);
                holder.binding.lastUpdate.setVisibility(View.GONE);
            }else{
                holder.binding.rootLayout.setVisibility(View.GONE);
                holder.binding.lastUpdate.setVisibility(View.VISIBLE);
                holder.binding.lastUpdate.setText("Last update: "+last_update_date+"\nPull down to refresh");
            }
        } else{
            holder.binding.rootLayout.setVisibility(View.VISIBLE);
            holder.binding.lastUpdate.setVisibility(View.GONE);
            holder.binding.storyTitle.setText(items.get(position-1).title);
            holder.binding.summery.setText(items.get(position-1).summary);
//            holder.binding.storyContentImage.setImageURI(null);
            if (items.get(position-1).images.size() > 0) {
                String filename = "org_"+org_id+"_content_image_" + items.get(position-1).id+ ".jpg";
                downloader.getImageUri(context,filename).observe(owner,uri ->{
                    if(uri != null){
                        loadImage(context,uri,holder.binding.storyContentImage);
                    }else{
                        holder.binding.storyContentImage.setImageResource(R.drawable.no_image);
                    }
                });
            }else{
                holder.binding.storyContentImage.setImageResource(R.drawable.no_image);
            }

            try {
                holder.binding.storyDate.setText(DateFormatUtils.getDate(items.get(position-1).created_on));
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d(TAG, "onBindViewHolder: " + e.getMessage());
            }

            holder.binding.storySeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putInt(IntentConstant.CONTENT_ID, items.get(position-1).id);
                    Navigator.navigateWithBundle(context, StoryDetailsActivity.class, IntentConstant.INTENT_DATA, b);
                }
            });
        }
    }

    public int getItemCount() {
        return items.size()+1;
    }

}
