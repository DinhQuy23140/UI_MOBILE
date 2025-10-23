package com.example.testui.service;

import com.example.testui.model.ProgressLog;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProgressLogService {
    @POST("progress-logs")
    Call<ProgressLog> createProgressLog(@Body Map<String, String> progressLog);
}
