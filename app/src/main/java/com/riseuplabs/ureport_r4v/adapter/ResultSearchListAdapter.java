package com.riseuplabs.ureport_r4v.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.model.results.ModelPolls;
import com.riseuplabs.ureport_r4v.model.search.CategoryResults;
import com.riseuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.riseuplabs.ureport_r4v.utils.DateFormatUtils;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;

import java.text.ParseException;
import java.util.ArrayList;

public class ResultSearchListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<CategoryResults> categoryList;
    private ArrayList<CategoryResults> originalList;
    int org_id;
    String queryText = "";

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
        setHighLightedText(name, queryText);
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
        queryText = query;
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
