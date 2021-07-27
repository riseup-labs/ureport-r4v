package com.risuplabs.ureport.di;

import android.app.Application;
import android.content.Context;

import com.risuplabs.ureport.network.apis.StoriesApi;
import com.risuplabs.ureport.rx.BaseScheduler;
import com.risuplabs.ureport.rx.DataManager;
import com.risuplabs.ureport.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Singleton
    @Provides
    Context getContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    BaseScheduler providesScheduler() {
        return new SchedulerProvider();
    }

    @Singleton
    @Provides
    DataManager providesDataManager(BaseScheduler scheduler) {
        return new DataManager(scheduler);
    }
}

