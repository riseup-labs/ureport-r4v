package com.risuplabs.ureport.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.risuplabs.ureport.model.results.ModelPolls;
import com.risuplabs.ureport.model.story.ModelStory;
import com.risuplabs.ureport.room.dao.ResultsDao;
import com.risuplabs.ureport.room.dao.StoriesDao;
import com.risuplabs.ureport.room.typeconverters.ResultsTypConverter;
import com.risuplabs.ureport.room.typeconverters.StoryTypeConverter;

@Database(entities = {ModelStory.class, ModelPolls.class}, version = 1)
@TypeConverters({StoryTypeConverter.class, ResultsTypConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract StoriesDao storyDao();
    public abstract ResultsDao resultsDao();

}
