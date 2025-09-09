package com.example.testui.service;

import com.example.testui.model.BatchStudent;
import com.example.testui.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BatchStudentService {
    @GET("batch-students")
    Call<List<BatchStudent>> getAllBatchStudent();

    @POST("batch-students")
    Call<BatchStudent> createBatchStudent(@Body BatchStudent batchStudent);

    @GET("batch-students/{batchStudentId}")
    Call<BatchStudent> getBatchStudentById(@Path("batchStudentId") String batchStudentId);

    @PUT("batch-students/{batchStudentId}")
    Call<BatchStudent> updateBatchStudent(@Path("batchStudentId") String batchStudentId, @Body BatchStudent batchStudent);

    @DELETE("batch-students/{batchStudentId}")
    Call<BatchStudent> deleteBatchStudent(@Path("batchStudentId") String batchStudentId);

    @GET("batch-students/{batchStudentId}/students")
    Call<List<Student>> getStudentsByBatchStudentId(@Path("batchStudentId") String batchStudentId);

    @POST("batch-students/{batchStudentId}/students")
    Call<Student> createStudentForBatchStudent(@Path("batchStudentId") String batchStudentId, @Body Student student);

    @GET("batch-students/{batchStudentId}/students/{studentId}")
    Call<Student> getStudentByBatchStudentIdAndStudentId(@Path("batchStudentId") String batchStudentId, @Path("studentId") String studentId);

    @PUT("batch-students/{batchStudentId}/students/{studentId}")
    Call<Student> updateStudentForBatchStudent(@Path("batchStudentId") String batchStudentId, @Path("studentId") String studentId, @Body Student student);

    @DELETE("batch-students/{batchStudentId}/students/{studentId}")
    Call<Student> deleteStudentForBatchStudent(@Path("batchStudentId") String batchStudentId, @Path("studentId") String studentId);

}
