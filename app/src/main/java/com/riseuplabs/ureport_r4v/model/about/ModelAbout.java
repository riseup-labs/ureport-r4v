package com.riseuplabs.ureport_r4v.model.about;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelAbout {

    public List<ModelAboutContent> results;


    public class ModelAboutContent{
        @SerializedName("title")
        public String title;

        @SerializedName("summary")
        public String summary;

        @SerializedName("content")
        public String content;
    }

}
