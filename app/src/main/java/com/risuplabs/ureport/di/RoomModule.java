package com.risuplabs.ureport.di;

import android.app.Application;

import androidx.room.Room;

import com.risuplabs.ureport.room.AppDatabase;
import com.risuplabs.ureport.room.dao.ResultsDao;
import com.risuplabs.ureport.room.dao.StoriesDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Provides
    @Singleton
    AppDatabase providesDatabase(Application application){
        return Room.databaseBuilder(application,AppDatabase.class,"database").build();
    }

    @Provides
    @Singleton
    StoriesDao providesStoriesDao(AppDatabase appDatabase){
        return appDatabase.storyDao();
    }

    @Provides
    @Singleton
    ResultsDao providesResultsDao(AppDatabase appDatabase){
        return appDatabase.resultsDao();
    }



}
