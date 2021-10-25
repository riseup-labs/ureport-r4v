package com.riseuplabs.ureport_r4v.ui.stories.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.annotation.Nullable;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.StorySearchListAdapter;
import com.riseuplabs.ureport_r4v.base.BaseActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityStorySearchBinding;
import com.riseuplabs.ureport_r4v.model.search.CategoryStory;
import com.riseuplabs.ureport_r4v.model.story.ModelStory;
import com.riseuplabs.ureport_r4v.ui.stories.StoryViewModel;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StorySearchActivity extends BaseActivity<ActivityStorySearchBinding> implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private StorySearchListAdapter listAdapter;
    private ArrayList<CategoryStory> categoryList = new ArrayList<CategoryStory>();

    int org_id;
    List<ModelStory> storyLlist = new ArrayList();

    @Inject
    StoryViewModel storyViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_story_search;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        org_id = prefManager.getInt(PrefKeys.ORG_ID);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        binding.search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.search.setIconifiedByDefault(false);
        binding.search.setOnQueryTextListener(this);
        binding.search.setOnCloseListener(this);

        storyViewModel.getAllStoriesFromLocal(org_id).observe(StorySearchActivity.this, response -> {
            storyLlist.addAll(response);
            if(storyLlist.size() > 0){
                displayList();
            }
        });

        binding.backButton.setOnClickListener(v -> {
            finish();
        });
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            binding.expandableList.expandGroup(i);
        }
    }

    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            binding.expandableList.collapseGroup(i);
        }
    }

    //method to expand all groups
    private void displayList() {

        ArrayList<String> cList = new ArrayList<>();
        ArrayList<String> cListDistinct = new ArrayList<>();


        for(int i = 0 ; i < storyLlist.size(); i++){
            cList.add(storyLlist.get(i).category.name);
        }

        for(int i = 0 ; i < cList.size() ; i++){
            if(!cListDistinct.contains(cList.get(i))){
                cListDistinct.add(cList.get(i));
            }
        }




        for(int i = 0; i < cListDistinct.size(); i++){

            ArrayList<ModelStory> titleList = new ArrayList<>();
            for(int j = 0; j < storyLlist.size(); j++){
                if(cListDistinct.get(i).equals(storyLlist.get(j).category.name)){
                    titleList.add(storyLlist.get(j));
                }
            }
            CategoryStory category = new CategoryStory(cListDistinct.get(i),titleList);
            categoryList.add(category);
        }


        //create the adapter by passing your ArrayList data
        listAdapter = new StorySearchListAdapter(StorySearchActivity.this, categoryList,org_id);
        //attach the adapter to the list
        binding.expandableList.setAdapter(listAdapter);

    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
//        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        listAdapter.filterData(query);
        Log.d(TAG, "onQueryTextChange: "+query);
        if(query.equals("")){
            collapseAll();
        }else{
            expandAll();
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextChange: "+query);
        listAdapter.filterData(query);
        if(query.equals("")){
            collapseAll();
        }else{
            expandAll();
        }
        return false;
    }
}