package com.example.testui.service;

import com.example.testui.model.Assignment;
import com.example.testui.model.ProjectTerm;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProjectTermService {
    @GET("project-terms")
    Call<List<ProjectTerm>> getProjectTerms();

    @GET("project-terms/{id}")
    Call<ProjectTerm> getProjectTermById(@Path("id") String id);

    @POST("project-terms")
    Call<ProjectTerm> createProjectTerm(@Body ProjectTerm projectTerm);

    @PUT("project-terms/{id}")
    Call<ProjectTerm> updateProjectTerm(@Path("id") String id, @Body ProjectTerm projectTerm);

    @DELETE("project-terms/{id}")
    Call<ProjectTerm> deleteProjectTerm(@Path("id") String id);

    @GET("project-terms/student/{studentId}")
    Call<List<ProjectTerm>> getProjectTermsByStudentId(@Path("studentId") String studentId);

    @GET("assignments/student/{studentId}/project-term/{projectTermId}")
    Call<Assignment> getAssignmentByStudentIdAndTermId(@Path("studentId") String studentId, @Path("projectTermId") String projectTermId);

    @GET("project_terms/new_terms")
    Observable<List<ProjectTerm>> getNewTerms();
}
