package com.example.testui.service;

import com.example.testui.model.Student;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
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
    Call<ResponseBody> updateStudent(@Path("studentId") String studentId, @Body Map<String, String> body);

    @DELETE("students/{studentId}")
    Call<Student> deleteStudent(@Path("studentId") String studentId);

}
