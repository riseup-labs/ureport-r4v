package com.risuplabs.ureport.network.apis;

import com.risuplabs.ureport.model.story.ModelStories;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface StoriesApi {

    @GET
    Observable<ModelStories> getStories(@Url String url);

}
