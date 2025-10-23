package com.example.testui.service;

import com.example.testui.model.Project;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProjectService {
    @POST("assignments/{assignmentId}/project")
    Call<Project> createProject(
            @Path("assignmentId") String assignmentId,
            @Body Map<String, String> projectBody
    );
}
