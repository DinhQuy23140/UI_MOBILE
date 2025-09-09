package com.example.testui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.ProjectTerm;
import com.example.testui.service.ProjectTermService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DotDoAnRepository {
    ProjectTermService projectTermService;
    MutableLiveData<List<ProjectTerm>> listDotDoAn = new MutableLiveData<>();

    public DotDoAnRepository() {
        this.projectTermService = Client.getInstance().create(ProjectTermService.class);
    }

    public void getListDoAnByStudentId(String studentId) {
        Call<List<ProjectTerm>> call = projectTermService.getProjectTermsByStudentId(studentId);
        call.enqueue(new Callback<List<ProjectTerm>>() {
            @Override
            public void onResponse(Call<List<ProjectTerm>> call, Response<List<ProjectTerm>> response) {
                if (response.isSuccessful()) {
                    listDotDoAn.setValue(response.body());
                } else {
                    Log.d("API_ERROR", "Not response");
                }
            }

            @Override
            public void onFailure(Call<List<ProjectTerm>> call, Throwable throwable) {
                Log.e("API_ERROR", "error: ", throwable);
            }
        });
    }

    public MutableLiveData<List<ProjectTerm>> getListDotDoAn() {
        return listDotDoAn;
    }
}
