package com.example.testui.repository;



import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.ProgressLog;
import com.example.testui.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressLogRepository {
    ApiService apiService;
    MutableLiveData<List<ProgressLog>> progressLogList = new MutableLiveData<>();
    MutableLiveData<List<ProgressLog>> progressLogByProjectId = new MutableLiveData<>();

    public ProgressLogRepository() {
        this.apiService = Client.getInstance().create(ApiService.class);
    }

    public void getAllProgressLog() {
        Call<List<ProgressLog>> call = apiService.getProgressLog();
        call.enqueue(new Callback<List<ProgressLog>>() {
            @Override
            public void onResponse(Call<List<ProgressLog>> call, Response<List<ProgressLog>> response) {
                progressLogList.setValue(response.body());
                Log.d("API", "Data size: " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<ProgressLog>> call, Throwable throwable) {
                progressLogList.setValue(new ArrayList<>());
                Log.e("API", "Failure: " + throwable.getMessage());
            }
        });
    }

    public MutableLiveData<List<ProgressLog>> getProgressLogList() {
        return progressLogList;
    }

    public void getProgressLogByProjectId(String projectID) {
        Call<List<ProgressLog>> call = apiService.getProgressLogByProjectId(projectID);
        call.enqueue(new Callback<List<ProgressLog>>() {
            @Override
            public void onResponse(Call<List<ProgressLog>> call, Response<List<ProgressLog>> response) {
                if (response.isSuccessful()) {
                    progressLogByProjectId.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Not response");
                }
            }

            @Override
            public void onFailure(Call<List<ProgressLog>> call, Throwable throwable) {
                Log.e("API_FAILURE", "Failure call api", throwable);
            }
        });
    }

    public MutableLiveData<List<ProgressLog>> getProgressLogByProjectId() {
        return progressLogByProjectId;
    }
}
