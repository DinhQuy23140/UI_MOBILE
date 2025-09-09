package com.example.testui.service;

import com.example.testui.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeacherService {
    //teacher
    @GET("teachers")
    Call<List<Teacher>> getAllTeacher();

    @POST("teachers")
    Call<Teacher> createTeacher(@Body Teacher teacher);

    @GET("teachers/{teacherId}")
    Call<Teacher> getTeacherById(@Path("teacherId") String teacherId);

    @PUT("teachers/{teacherId}")
    Call<Teacher> updateTeacher(@Path("teacherId") String teacherId, @Body Teacher teacher);

    @DELETE("teachers/{teacherId}")
    Call<Teacher> deleteTeacher(@Path("teacherId") String teacherId);
}
