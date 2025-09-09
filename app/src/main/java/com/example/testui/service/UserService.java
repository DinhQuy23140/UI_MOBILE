package com.example.testui.service;

import com.example.testui.model.LoginResponse;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    //user
    @GET("users")
    Call<List<User>> getAllUser();

    @POST("users")
    Call<User> createUser(@Body User user);

    @GET("users/{userId}")
    Call<User> getUserById(@Path("userId") String userId);

    @PUT("users/{userId}")
    Call<User> updateUser(@Path("userId") String userId, @Body User user);

    @DELETE("users/{userId}")
    Call<User> deleteUser(@Path("userId") String userId);

}
