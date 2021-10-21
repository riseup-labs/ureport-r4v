package com.risuplabs.ureport_r4v.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.model.results.ModelPolls;
import com.risuplabs.ureport_r4v.model.search.CategoryResults;
import com.risuplabs.ureport_r4v.model.story.ModelStory;
import com.risuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.risuplabs.ureport_r4v.ui.stories.details.StoryDetailsActivity;
import com.risuplabs.ureport_r4v.utils.DateFormatUtils;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;

import java.text.ParseException;
import java.util.ArrayList;

public class ResultSearchListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<CategoryResults> categoryList;
    private ArrayList<CategoryResults> originalList;
    int org_id;

    public ResultSearchListAdapter(Activity activity,Context context, ArrayList<CategoryResults> categoryList, int org_id) {
        this.activity = activity;
        this.context = context;
        this.org_id = org_id;
        this.categoryList = new ArrayList<CategoryResults>();
        this.categoryList.addAll(categoryList);
        this.originalList = new ArrayList<CategoryResults>();
        this.originalList.addAll(categoryList);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ModelPolls> countryList = categoryList.get(groupPosition).getTitleList();
        return countryList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        ModelPolls modelPolls = (ModelPolls) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.child_row, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);

        String sourceString = null;
        try {
            sourceString = modelPolls.title.trim()+ "  <b>" + DateFormatUtils.getPollDate(modelPolls.poll_date) + "</b> ";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        name.setText(Html.fromHtml(sourceString));

        name.setOnClickListener(v ->{
            Bundle b = new Bundle();
            b.putInt(IntentConstant.POLL_ID,modelPolls.id);
            b.putString(IntentConstant.ACTIVITY_NAME,modelPolls.title);
            b.putString(IntentConstant.CATEGORY_NAME,modelPolls.category_tag);
            Navigator.navigateWithBundle(context, PollsActivity.class,IntentConstant.INTENT_DATA,b);
            activity.finish();
        });

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ModelPolls> countryList = categoryList.get(groupPosition).getTitleList();
        return countryList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        CategoryResults category = (CategoryResults) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group_row, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(category.getName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){

        query = query.toLowerCase();
        categoryList.clear();

        if(query.isEmpty()){
            categoryList.addAll(originalList);
        }
        else {

            for(CategoryResults category : originalList){

                ArrayList<ModelPolls> countryList = category.getTitleList();
                ArrayList<ModelPolls> newList = new ArrayList<ModelPolls>();
                for(ModelPolls modelPolls: countryList){
                    if(modelPolls.title.toLowerCase().contains(query) ||
                            modelPolls.title.toLowerCase().contains(query)){
                        newList.add(modelPolls);
                    }
                }
                if(newList.size() > 0){
                    CategoryResults nCategory = new CategoryResults(category.getName(),newList);
                    categoryList.add(nCategory);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(categoryList.size()));
        notifyDataSetChanged();

    }

}
