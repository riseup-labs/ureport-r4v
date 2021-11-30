package com.riseuplabs.ureport_r4v.model.story;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelStories
{
    public int count;

    public String error_code;

    public String next;

    public String previous;

    @SerializedName("results")
    public List<ModelStory> stories;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setNext(String next){
        this.next = next;
    }
    public String getNext(){
        return this.next;
    }
    public void setPrevious(String previous){
        this.previous = previous;
    }
    public String getPrevious(){
        return this.previous;
    }
    public void setStories(List<ModelStory> stories){
        this.stories = stories;
    }
    public List<ModelStory> getStories(){
        return this.stories;
    }
}

