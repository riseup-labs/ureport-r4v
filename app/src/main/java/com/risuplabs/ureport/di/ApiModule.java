package com.risuplabs.ureport.di;

import com.risuplabs.ureport.network.apis.ResultsApi;
import com.risuplabs.ureport.network.apis.StoriesApi;
import com.risuplabs.ureport.network.apis.SurveyorApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Singleton
    @Provides
    StoriesApi providesStroyApi(@Named("story") Retrofit retrofit) {
        return retrofit.create(StoriesApi.class);
    }

    @Singleton
    @Provides
    ResultsApi providesResultApi(@Named("story") Retrofit retrofit) {
        return retrofit.create(ResultsApi.class);
    }

    @Singleton
    @Provides
    SurveyorApi providesSurveyorApi(@Named("surveyor") Retrofit retrofit) {
        return retrofit.create(SurveyorApi.class);
    }


}
