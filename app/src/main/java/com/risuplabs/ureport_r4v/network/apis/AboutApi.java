package com.risuplabs.ureport_r4v.network.apis;

import com.risuplabs.ureport_r4v.model.about.ModelAbout;
import com.risuplabs.ureport_r4v.model.story.ModelStories;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AboutApi {

    @GET
    Observable<ModelAbout> getAbout(@Url String url);

}
