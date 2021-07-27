package com.risuplabs.ureport.model.results;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "table_polls",primaryKeys = {"id","org"})
public class ModelPolls {
    public int id;
    public String flow_uuid;
    public String title;
    public int org;
    public String category_tag;
    public ModelPollCategory category;
    public String poll_date;
    public String modified_on;
    public String created_on;
    public List<ModelQuestion> questions;

}
