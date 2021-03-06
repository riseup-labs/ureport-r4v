package com.riseuplabs.ureport_r4v.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.model.search.CategoryStory;
import com.riseuplabs.ureport_r4v.model.story.ModelStory;
import com.riseuplabs.ureport_r4v.ui.stories.details.StoryDetailsActivity;
import com.riseuplabs.ureport_r4v.utils.DateFormatUtils;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;

import java.text.ParseException;
import java.util.ArrayList;

public class StorySearchListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CategoryStory> categoryList;
    private ArrayList<CategoryStory> originalList;
    int org_id;
    TextView name;
    String queryText = "";

    public StorySearchListAdapter(Context context, ArrayList<CategoryStory> categoryList, int org_id) {
        this.context = context;
        this.org_id = org_id;
        this.categoryList = new ArrayList<CategoryStory>();
        this.categoryList.addAll(categoryList);
        this.originalList = new ArrayList<CategoryStory>();
        this.originalList.addAll(categoryList);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ModelStory> countryList = categoryList.get(groupPosition).getTitleList();
        return countryList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        ModelStory modelStory = (ModelStory) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.child_row, null);
        }

        name = (TextView) view.findViewById(R.id.name);
        String sourceString = null;
        try {
            sourceString = modelStory.title.trim()+ "  <b>" + DateFormatUtils.getDate(modelStory.created_on) + "</b> ";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        name.setText(Html.fromHtml(sourceString));
        setHighLightedText(name, queryText);
        name.setOnClickListener(v ->{
            Bundle b = new Bundle();
            b.putInt(IntentConstant.CONTENT_ID, modelStory.id);
            b.putString(IntentConstant.TITLE, modelStory.title);
            b.putString(IntentConstant.IMAGE_NAME, "org_" + org_id + "_content_image_" + modelStory.id + ".jpg");
            try {
                b.putString(IntentConstant.STORY_DATE, DateFormatUtils.getDate(modelStory.created_on));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Navigator.navigateWithBundle(context, StoryDetailsActivity.class, IntentConstant.INTENT_DATA, b);
        });

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ModelStory> countryList = categoryList.get(groupPosition).getTitleList();
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

        CategoryStory category = (CategoryStory) getGroup(groupPosition);
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
        queryText = query;
        query = query.toLowerCase();
        categoryList.clear();

        if(query.isEmpty()){
            categoryList.addAll(originalList);
        }
        else {

            for(CategoryStory category : originalList){

                ArrayList<ModelStory> countryList = category.getTitleList();
                ArrayList<ModelStory> newList = new ArrayList<ModelStory>();
                for(ModelStory modelStory: countryList){
                    if(modelStory.title.toLowerCase().contains(query) ||
                            modelStory.title.toLowerCase().contains(query)){
                        newList.add(modelStory);
                    }
                }
                if(newList.size() > 0){
                    CategoryStory nCategory = new CategoryStory(category.getName(),newList);
                    categoryList.add(nCategory);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(categoryList.size()));
        notifyDataSetChanged();

    }

    public void setHighLightedText(TextView tv, String textToHighlight) {
        String tvt = tv.getText().toString().toLowerCase();
        int ofe = tvt.indexOf(textToHighlight, 0);
        Spannable wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToHighlight, ofs);
            if (ofe == -1)
                break;
            else {
                // set color here
                wordToSpan.setSpan(new BackgroundColorSpan(0xFF47C2C4), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }

}
