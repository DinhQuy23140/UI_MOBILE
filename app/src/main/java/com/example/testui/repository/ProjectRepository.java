package com.example.testui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.Project;
import com.example.testui.service.ProjectService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    ProjectService projectService;
    MutableLiveData<Project> projectMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isSuccessCreate = new MutableLiveData<>();

    public ProjectRepository() {
        projectService = Client.getInstance().create(ProjectService.class);
    }

    public void createProject(String assignmentId, Map<String, String> projectBody) {
        Call<Project> call = projectService.createProject(assignmentId, projectBody);
        call.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    projectMutableLiveData.setValue(response.body());
                    isSuccessCreate.setValue(true);
                    Log.d("Success", new Gson().toJson(response.body()));
                } else {
                    isSuccessCreate.setValue(false);
                    try {
                        if (response.errorBody() != null) {
                            Log.e("Failure", "onFailure: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable throwable) {
                isSuccessCreate.setValue(false);
                Log.e("Error", throwable.toString());
            }
        });
    }

    public MutableLiveData<Project> getProjectMutableLiveData() {
        return projectMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsSuccessCreate() {
        return isSuccessCreate;
    }
}
