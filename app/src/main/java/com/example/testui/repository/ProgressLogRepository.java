package com.example.testui.repository;



import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.ProgressLog;
import com.example.testui.service.ApiService;
import com.example.testui.service.ProgressLogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressLogRepository {
    ApiService apiService;
    ProgressLogService progressLogService;
    MutableLiveData<List<ProgressLog>> progressLogList = new MutableLiveData<>();
    MutableLiveData<List<ProgressLog>> progressLogByProjectId = new MutableLiveData<>();
    MutableLiveData<Boolean> isCreateSuccess = new MutableLiveData<>();
    MutableLiveData<ProgressLog> responseProgressLog = new MutableLiveData<>();

    public ProgressLogRepository() {
        this.apiService = Client.getInstance().create(ApiService.class);
        progressLogService = Client.getInstance().create(ProgressLogService.class);
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

    public void createProgressLog(Map<String, String> progressLog) {
        Call<ProgressLog> call = progressLogService.createProgressLog(progressLog);

        call.enqueue(new Callback<ProgressLog>() {
            @Override
            public void onResponse(Call<ProgressLog> call, Response<ProgressLog> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseProgressLog.setValue(response.body());
                    isCreateSuccess.setValue(true);
                    Log.d("API_SUCCESS", "Tạo tiến độ thành công: " + response.body().toString());
                } else {
                    isCreateSuccess.setValue(false);

                    // 🔥 Log chi tiết lỗi từ response
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        errorBody = "Không thể đọc errorBody: " + e.getMessage();
                    }

                    Log.e("API_ERROR", "Lỗi khi tạo ProgressLog:"
                            + "\nCode: " + response.code()
                            + "\nMessage: " + response.message()
                            + "\nError body: " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<ProgressLog> call, Throwable t) {
                isCreateSuccess.setValue(false);

                // 🔥 Log chi tiết nguyên nhân thất bại (thường do mạng, hoặc cấu hình Retrofit sai)
                Log.e("API_FAILURE", "Không thể gọi API createProgressLog()", t);
            }
        });
    }

    public MutableLiveData<ProgressLog> getResponseProgressLog() {
        return responseProgressLog;
    }

    public MutableLiveData<Boolean> getIsCreateSuccess() {
        return isCreateSuccess;
    }
}
