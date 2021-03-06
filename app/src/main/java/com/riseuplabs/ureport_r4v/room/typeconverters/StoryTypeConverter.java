package com.riseuplabs.ureport_r4v.room.typeconverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riseuplabs.ureport_r4v.model.story.ModelStoryCategory;

import java.lang.reflect.Type;
import java.util.List;

public class StoryTypeConverter {

    @TypeConverter
    public String ImageListToJson(List<String> value){
      return new Gson().toJson(value) ;
    }

    @TypeConverter
    public List<String> ImageJsonToList(String value){
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public String CategoryToJson(ModelStoryCategory value){
      return new Gson().toJson(value) ;
    }

    @TypeConverter
    public ModelStoryCategory CategoryJsonToModel(String value){
        Type listType = new TypeToken<ModelStoryCategory>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

}
