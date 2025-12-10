package com.example.testui.service;

import com.example.testui.model.ProgressLog;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProgressLogService {
    @POST("progress-logs")
    Call<ProgressLog> createProgressLog(@Body Map<String, String> progressLog);

    @GET("progress-logs/log/{logId}")
    Call<ProgressLog> getProgressLogById(@Path("logId") String logId);
}
