package com.riseuplabs.ureport_r4v.room.typeconverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riseuplabs.ureport_r4v.model.results.ModelPollCategory;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestion;
import com.riseuplabs.ureport_r4v.model.results.ModelQuestionResult;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByAge;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByGender;
import com.riseuplabs.ureport_r4v.model.results.ModelResultsByLocation;

import java.lang.reflect.Type;
import java.util.List;

public class ResultsTypConverter {

    @TypeConverter
    public String CategoryToJson(ModelPollCategory value){
        return new Gson().toJson(value) ;
    }

    @TypeConverter
    public ModelPollCategory CategoryJsonToModel(String value){
        Type type = new TypeToken<ModelPollCategory>(){}.getType();
        return new Gson().fromJson(value,type);
    }

    @TypeConverter
    public String QuestionListToJson(List<ModelQuestion> value){
        return new Gson().toJson(value) ;
    }

    @TypeConverter
    public List<ModelQuestion> QuestionJsonToList(String value){
        Type listType = new TypeToken<List<ModelQuestion>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public String QuestionResultListToJson(ModelQuestionResult value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public ModelQuestionResult QuestionResultJsonToList(String value) {
        Type listType = new TypeToken<ModelQuestionResult>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String ModelResultsByAgeToJson(List<ModelResultsByAge> value){
        return new Gson().toJson(value) ;
    }

    @TypeConverter
    public List<ModelResultsByAge> ModelResultsByAgeJsonToList(String value){
        Type listType = new TypeToken<List<ModelResultsByAge>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public String ModelResultsByGenderToJson(List<ModelResultsByGender> value){
        return new Gson().toJson(value) ;
    }

    @TypeConverter
    public List<ModelResultsByGender>ModelResultsByGenderJsonToList(String value){
        Type listType = new TypeToken<List<ModelResultsByGender>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public String ModelResultsByLocationToJson(List<ModelResultsByLocation> value){
        return new Gson().toJson(value) ;
    }

    @TypeConverter
    public List<ModelResultsByLocation>ModelResultsByLocationJsonToList(String value){
        Type listType = new TypeToken<List<ModelResultsByLocation>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }



}
