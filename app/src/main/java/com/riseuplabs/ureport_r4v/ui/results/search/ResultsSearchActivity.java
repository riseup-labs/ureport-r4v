package com.riseuplabs.ureport_r4v.ui.results.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.annotation.Nullable;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.ResultSearchListAdapter;
import com.riseuplabs.ureport_r4v.base.BaseActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityResultsSearchBinding;
import com.riseuplabs.ureport_r4v.model.results.ModelPolls;
import com.riseuplabs.ureport_r4v.model.search.CategoryResults;
import com.riseuplabs.ureport_r4v.ui.results.ResultsViewModel;
import com.riseuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ResultsSearchActivity extends BaseActivity<ActivityResultsSearchBinding> implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private ResultSearchListAdapter listAdapter;
    private ArrayList<CategoryResults> categoryList = new ArrayList<CategoryResults>();
    List<ModelPolls> resultList = new ArrayList();

    int org_id;
    
    @Inject
    ResultsViewModel viewModel;
    

    @Override
    public int getLayoutId() {
        return R.layout.activity_results_search;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        org_id = prefManager.getInt(PrefKeys.ORG_ID);

        viewModel.getResultCategoriesFromLocal(org_id).observe(ResultsSearchActivity.this, response->{
            resultList.addAll(response);
            if(resultList.size() > 0){
                displayList();
            }
        });
        
        
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        binding.search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.search.setIconifiedByDefault(false);
        binding.search.setOnQueryTextListener(this);
        binding.search.setOnCloseListener(this);
        

        binding.backButton.setOnClickListener(v -> {
            Navigator.navigate(this, PollsActivity.class);
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


        for(int i = 0 ; i < resultList.size(); i++){
            cList.add(resultList.get(i).category_tag);
        }

        for(int i = 0 ; i < cList.size() ; i++){
            if(!cListDistinct.contains(cList.get(i))){
                cListDistinct.add(cList.get(i));
            }
        }




        for(int i = 0; i < cListDistinct.size(); i++){

            ArrayList<ModelPolls> titleList = new ArrayList<>();
            for(int j = 0; j < resultList.size(); j++){
                if(cListDistinct.get(i).equals(resultList.get(j).category_tag)){
                    titleList.add(resultList.get(j));
                }
            }
            CategoryResults category = new CategoryResults(cListDistinct.get(i),titleList);
            categoryList.add(category);
        }


        //create the adapter by passing your ArrayList data
        listAdapter = new ResultSearchListAdapter(ResultsSearchActivity.this,ResultsSearchActivity.this, categoryList,org_id);
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