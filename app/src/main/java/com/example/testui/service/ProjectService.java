package com.example.testui.service;

import com.example.testui.model.Project;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProjectService {
    @POST("create_project")
    Call<Project> createProject(@Body Map<String, String> projectBody);
}
