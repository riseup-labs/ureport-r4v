package com.riseuplabs.ureport_r4v.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.riseuplabs.ureport_r4v.model.results.ModelPolls;
import com.riseuplabs.ureport_r4v.model.story.ModelStory;
import com.riseuplabs.ureport_r4v.model.surveyor.ModelSurvey;
import com.riseuplabs.ureport_r4v.room.dao.ResultsDao;
import com.riseuplabs.ureport_r4v.room.dao.StoriesDao;
import com.riseuplabs.ureport_r4v.room.dao.SurveyDao;
import com.riseuplabs.ureport_r4v.room.typeconverters.ResultsTypConverter;
import com.riseuplabs.ureport_r4v.room.typeconverters.StoryTypeConverter;

@Database(entities = {ModelStory.class, ModelPolls.class, ModelSurvey.class}, version = 1)
@TypeConverters({StoryTypeConverter.class, ResultsTypConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract StoriesDao storyDao();
    public abstract ResultsDao resultsDao();
    public abstract SurveyDao surveyDao();

}
