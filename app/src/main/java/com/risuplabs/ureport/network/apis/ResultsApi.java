package com.risuplabs.ureport.network.apis;

import com.risuplabs.ureport.model.results.ModelResults;
import com.risuplabs.ureport.model.story.ModelStories;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ResultsApi {
    @GET
    Observable<ModelResults> getResults(@Url String url);
}
