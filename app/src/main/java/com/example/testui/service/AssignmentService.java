package com.example.testui.service;

import com.example.testui.model.Assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AssignmentService {
    @GET("assignments")
    Call<List<Assignment>> getAllAssignment();

    @POST("assignments")
    Call<Assignment> createAssignment(@Body Assignment assignment);

    @GET("assignment/{assignmentId}")
    Call<Assignment> getAssignmentById(@Path("assignmentId") String assignmentId);

    @PUT("assignments/{assignmentId}")
    Call<Assignment> updateAssignment(@Path("assignmentId") String assignmentId, @Body Assignment assignment);

    @DELETE("assignments/{assignmentId}")
    Call<Assignment> deleteAssignment(@Path("assignmentId") String assignmentId);

    @GET("assignments/student/{studentId}/project-term/{projectTermId}")
    Call<Assignment> getAssignmentByStudentIdAndTermId(@Path("studentId") String studentId, @Path("projectTermId") String projectTermId);

    @PATCH("assignments/{assignmentId}/project/{projectId}")
    Call<Assignment> updateProjectIdAssignmentByAssIdAndProId(@Path("assignmentId") String assignmentId, @Path("projectId") String projectId);

    @GET("assignment/recent/student/{studentId}")
    Call<Assignment> getRecentAssignmentByStudentId(@Path("studentId") String studentId);

    @GET("assignments/teacher/{teacherId}")
    Call<List<Assignment>> getAssignmentsByTeacherId(@Path("teacherId") String teacherId);
}
