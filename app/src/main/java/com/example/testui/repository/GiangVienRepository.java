package com.example.testui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.Supervisor;
import com.example.testui.service.SupervisorService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiangVienRepository {
    SupervisorService supervisorService;
    MutableLiveData<List<Supervisor>> listSupervisor = new MutableLiveData<>();

    public GiangVienRepository() {
        supervisorService = Client.getInstance().create(SupervisorService.class);
    }

    public void getAllSupervisorByProjectTermId(String projectTermId){
        Call<List<Supervisor>> call = supervisorService.getSuperVisorByProjectTermId(projectTermId);
        call.enqueue(new Callback<List<Supervisor>>() {
            @Override
            public void onResponse(Call<List<Supervisor>> call, Response<List<Supervisor>> response) {
                if (response.isSuccessful()) {
                    listSupervisor.setValue(response.body());
                    Log.e("API_SUCCESS", "Response successful: " + new Gson().toJson(response.body()));
                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Supervisor>> call, Throwable throwable) {
                Log.e("API_ERROR", "Failed to fetch supervisors", throwable);
            }
        });
    }

    public MutableLiveData<List<Supervisor>> getListSupervisor() {
        return listSupervisor;
    }
}
