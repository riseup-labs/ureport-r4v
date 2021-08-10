package com.risuplabs.ureport_r4v.surveyor.net;

import com.google.gson.JsonObject;
import com.risuplabs.ureport_r4v.surveyor.net.requests.SubmissionPayload;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Boundary;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Definitions;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Field;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Flow;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Group;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Org;
import com.risuplabs.ureport_r4v.surveyor.net.responses.PaginatedResults;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface TembaAPI {

    //1
    @GET("/api/v2/org.json")
    Call<Org> getOrg(@Header("Authorization") String token);

    //2
    @GET("/api/v2/fields.json")
    Call<PaginatedResults<Field>> getFields(
            @Header("Authorization") String token,
            @Query("cursor") String cursor
    );

    //3
    @GET("/api/v2/groups.json")
    Call<PaginatedResults<Group>> getGroups(
            @Header("Authorization") String token,
            @Query("cursor") String cursor
    );

    //4
    @GET("/api/v2/flows.json")
    Call<PaginatedResults<Flow>> getFlows(
            @Header("Authorization") String token,
            @Query("type") String type,
            @Query("archived") Boolean archived,
            @Query("cursor") String cursor
    );

    //5
    @GET("/api/v2/definitions.json")
    Call<Definitions> getDefinitions(
            @Header("Authorization") String token,
            @Query("flow") List<String> flowUUIDs,
            @Query("dependencies") String dependencies
    );

    //6
    @GET("/api/v2/boundaries.json")
    Call<PaginatedResults<Boundary>> getBoundaries(
            @Header("Authorization") String token,
            @Query("cursor") String cursor
    );


    //Uploading media
    @Multipart
    @POST("/api/v2/media.json")
    Call<JsonObject> uploadMedia(
            @Header("Authorization") String token,
            @PartMap Map<String, RequestBody> params
    );

    //Submitting survey
    @POST("/mr/surveyor/submit")
    Call<JsonObject> submit(
            @Header("Authorization") String token,
            @Body SubmissionPayload submission
    );

}
