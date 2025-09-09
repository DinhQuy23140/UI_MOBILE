package com.example.testui.service;

import com.example.testui.model.Assignment;
import com.example.testui.model.Attachment;
import com.example.testui.model.LoginResponse;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("all-attachments")
    Call<List<Attachment>> getAttachment();

    @GET("attachments/{idProgressLog}")
    Call<List<Attachment>> getAttachMentByIdProgressLog(@Path("idProgressLog") String processId);

    @PUT("attachments/{idProgressLog}")
    Call<Attachment> updateAttachment(@Path("idProgressLog") String processId, @Query("fileName") String fileName,
                                      @Query("fileUrl") String fileUrl, @Query("fileType") String fileType);

    @DELETE("attachments/{idProgressLog}")
    Call<Attachment> deleteAttachment(@Path("idProgressLog") String processId);

    @GET("progress-logs")
    Call<List<ProgressLog>> getProgressLog();

    @POST("progress-logs")
    Call<ProgressLog> createProgressLog();

    @GET("progress-logs/{idProgressLog}")
    Call<ProgressLog> getProgressLogById(@Path("idProgressLog") String processId);

    @PUT("progress-logs/{idProgressLog}")
    Call<ProgressLog> updateProgressLog(@Path("idProgressLog") String processId, @Query("title") String title,
                                        @Query("description") String description, @Query("startDateTime") String startDateTime,
                                        @Query("endDateTime") String endDateTime);

    @DELETE("progress-logs/{idProgressLog}")
    Call<ProgressLog> deleteProgressLog(@Path("idProgressLog") String processId);

    @GET("progress-logs/{idProgressLog}/attachments")
    Call<List<Attachment>> getAttachmentByProgressLogId(@Path("idProgressLog") String processId);

    @POST("progress-logs/{idProgressLog}/attachments")
    Call<Attachment> createAttachment(@Path("idProgressLog") String processId, @Query("fileName") String fileName,
                                      @Query("fileUrl") String fileUrl, @Query("fileType") String fileType);

    @POST("auth/login")
    Call<LoginResponse> login(@Body Map<String, String> body);

    @GET("students/{studentId}")
    Call<Student> getStudentById(@Path("studentId") String studentId);

    @GET("supervisors")
    Call<List<Supervisor>> getAllSuperVisor();

    @GET("assignments/student/{studentID}")
    Call<Assignment> getAssignmentByStudentId(@Path("studentID") String studentId);

    @GET("progress-logs/project/{projectId}")
    Call<List<ProgressLog>> getProgressLogByProjectId(@Path("projectId") String projectId);
}
