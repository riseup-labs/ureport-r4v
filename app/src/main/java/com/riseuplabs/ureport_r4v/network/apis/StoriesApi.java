package com.riseuplabs.ureport_r4v.network.apis;

import com.riseuplabs.ureport_r4v.model.story.ModelStories;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface StoriesApi {

    @GET
    Observable<ModelStories> getStories(@Url String url);

}
