package com.example.testui.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Supervisor {
    //supervisor
    @GET("supervisors")
    Call<List<com.example.testui.model.Supervisor>> getAllSuperVisor();

    @POST("supervisors")
    Call<com.example.testui.model.Supervisor> createSuperVisor(@Body com.example.testui.model.Supervisor supervisor);

    @GET("supervisors/{supervisorId}")
    Call<com.example.testui.model.Supervisor> getSuperVisorById(@Path("supervisorId") String supervisorId);

    @PUT("supervisors/{supervisorId}")
    Call<com.example.testui.model.Supervisor> updateSuperVisor(@Path("supervisorId") String supervisorId, @Body com.example.testui.model.Supervisor supervisor);

    @DELETE("supervisors/{supervisorId}")
    Call<com.example.testui.model.Supervisor> deleteSuperVisor(@Path("supervisorId") String supervisorId);
}
