package com.riseuplabs.ureport_r4v.model.story;

import androidx.room.Entity;

import java.util.List;

@Entity(tableName = "table_story",primaryKeys = {"id","org_id"})
public class ModelStory {
    public int id;

    public String title;

    public int org_id;

    public String summary;

    public String content;

    public String video_id;

    public String audio_link;

    public String tags;

    public List<String> images;

    public ModelStoryCategory category;

    public String created_on;


}

