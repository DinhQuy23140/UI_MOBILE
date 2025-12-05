package com.example.testui.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ResetPasswordService {
    @POST("auth/forgot-password")
    Call<ResponseBody> sendResetPassword(@Body Map<String, String> body);

    @POST("auth/reset-password")
    Call<ResponseBody> resetPassword(@Body Map<String, String> body);

    @POST("auth/change-password")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<ResponseBody> changePassword(
            @Header("Authorization") String token,
            @Body Map<String, String> body
    );
}
