package com.example.testui.service;

import com.example.testui.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {
    //student
    @GET("students")
    Call<List<Student>> getAllStudent();

    @POST("students")
    Call<Student> createStudent(@Body Student student);

    @GET("students/{studentId}")
    Call<Student> getStudentById(@Path("studentId") String studentId);

    @PUT("students/{studentId}")
    Call<Student> updateStudent(@Path("studentId") String studentId, @Body Student student);

    @DELETE("students/{studentId}")
    Call<Student> deleteStudent(@Path("studentId") String studentId);
}
