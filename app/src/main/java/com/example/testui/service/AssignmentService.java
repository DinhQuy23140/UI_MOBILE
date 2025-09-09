package com.example.testui.service;

import com.example.testui.model.Assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AssignmentService {
    @GET("assignments")
    Call<List<Assignment>> getAllAssignment();

    @POST("assignments")
    Call<Assignment> createAssignment(@Body Assignment assignment);

    @GET("assignments/{assignmentId}")
    Call<Assignment> getAssignmentById(@Path("assignmentId") String assignmentId);

    @PUT("assignments/{assignmentId}")
    Call<Assignment> updateAssignment(@Path("assignmentId") String assignmentId, @Body Assignment assignment);

    @DELETE("assignments/{assignmentId}")
    Call<Assignment> deleteAssignment(@Path("assignmentId") String assignmentId);
}
