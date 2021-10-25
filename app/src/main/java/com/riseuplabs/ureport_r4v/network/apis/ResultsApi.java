package com.riseuplabs.ureport_r4v.network.apis;

import com.riseuplabs.ureport_r4v.model.results.ModelResults;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ResultsApi {
    @GET
    Observable<ModelResults> getResults(@Url String url);
}
