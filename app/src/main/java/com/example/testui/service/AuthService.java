package com.example.testui.service;

import com.example.testui.model.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body Map<String, String> body);

    @POST("auth/logout")
    Call<LoginResponse> logout(@Body Map<String, String> body);
}
