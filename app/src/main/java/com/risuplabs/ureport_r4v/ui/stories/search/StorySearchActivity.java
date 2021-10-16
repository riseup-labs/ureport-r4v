package com.risuplabs.ureport_r4v.ui.stories.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityStorySearchBinding;

import java.util.ArrayList;

public class StorySearchActivity extends BaseActivity<ActivityStorySearchBinding> implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_story_search;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        binding.search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.search.setIconifiedByDefault(false);
        binding.search.setOnQueryTextListener(this);
        binding.search.setOnCloseListener(this);

        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        //display the list
        displayList();
        //expand all Groups
//        expandAll();
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.expandGroup(i);
        }
    }

    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.collapseGroup(i);
        }
    }

    //method to expand all groups
    private void displayList() {

        //display the list
        loadSomeData();

        //get reference to the ExpandableListView
        myList = (ExpandableListView) findViewById(R.id.expandableList);
        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(StorySearchActivity.this, continentList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);

    }

    private void loadSomeData() {

        ArrayList<Country> countryList = new ArrayList<Country>();
        Country country = new Country("BMU","Bermuda",10000000);
        countryList.add(country);
        country = new Country("CAN","Canada",20000000);
        countryList.add(country);
        country = new Country("USA","United States",50000000);
        countryList.add(country);

        Continent continent = new Continent("North America",countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("CHN","China",10000100);
        countryList.add(country);
        country = new Country("JPN","Japan",20000200);
        countryList.add(country);
        country = new Country("THA","Thailand",50000500);
        countryList.add(country);

        continent = new Continent("Asia",countryList);
        continentList.add(continent);

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