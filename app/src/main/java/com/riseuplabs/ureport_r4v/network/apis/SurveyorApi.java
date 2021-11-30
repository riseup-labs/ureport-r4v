package com.riseuplabs.ureport_r4v.network.apis;

import com.riseuplabs.ureport_r4v.surveyor.net.responses.FlowResponse;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.TokenResults;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SurveyorApi {

    @FormUrlEncoded
    @POST("/api/v2/authenticate")
    Observable<TokenResults> authenticate(
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role
    );

    @GET("/api/v2/flows.json?type=survey&archived=false")
    Observable<FlowResponse> getFlows();
}
