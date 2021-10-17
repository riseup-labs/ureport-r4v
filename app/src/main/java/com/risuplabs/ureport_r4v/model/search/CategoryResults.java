package com.risuplabs.ureport_r4v.model.search;

import com.risuplabs.ureport_r4v.model.results.ModelPolls;
import com.risuplabs.ureport_r4v.model.story.ModelStory;

import java.util.ArrayList;

public class CategoryResults {

    private String name;
    private ArrayList<ModelPolls> titleList = new ArrayList<ModelPolls>();

    public CategoryResults(String name, ArrayList<ModelPolls> titleList) {
        this.name = name;
        this.titleList = titleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ModelPolls> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<ModelPolls> titleList) {
        this.titleList = titleList;
    }

}
