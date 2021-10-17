package com.risuplabs.ureport_r4v.model.search;

import com.risuplabs.ureport_r4v.model.story.ModelStory;

import java.util.ArrayList;

public class CategoryStory {

    private String name;
    private ArrayList<ModelStory> titleList = new ArrayList<ModelStory>();

    public CategoryStory(String name, ArrayList<ModelStory> titleList) {
        this.name = name;
        this.titleList = titleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ModelStory> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<ModelStory> titleList) {
        this.titleList = titleList;
    }

}
