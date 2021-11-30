package com.riseuplabs.ureport_r4v.model.results;

import java.util.List;

public class ModelQuestion {

    public int id;
    public String ruleset_uuid;
    public String title;
    public ModelQuestionResult results;
    public List<ModelResultsByAge> results_by_age;
    public List<ModelResultsByGender> results_by_gender;
    public List<ModelResultsByLocation> results_by_location;

    @Override
    public String toString() {
        return "ModelQuestion{" +
                "id=" + id +
                ", ruleset_uuid='" + ruleset_uuid + '\'' +
                ", title='" + title + '\'' +
                ", results=" + results +
                ", results_by_age=" + results_by_age +
                ", results_by_gender=" + results_by_gender +
                ", results_by_location=" + results_by_location +
                '}';
    }
}
