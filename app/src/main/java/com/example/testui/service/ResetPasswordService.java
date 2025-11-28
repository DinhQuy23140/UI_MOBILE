package com.example.testui.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ResetPasswordService {
    @POST("auth/forgot-password")
    Call<ResponseBody> sendResetPassword(@Body Map<String, String> body);

    @POST("auth/reset-password")
    Call<ResponseBody> resetPassword(@Body Map<String, String> body);
}
