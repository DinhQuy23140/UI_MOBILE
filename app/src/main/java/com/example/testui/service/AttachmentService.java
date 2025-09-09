package com.example.testui.service;

import com.example.testui.model.Attachment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AttachmentService {
    @POST("attachments")
    Call<Attachment> createAttachment(@Body Attachment attachment);

    @GET("attachments/{attachmentId}")
    Call<Attachment> getAttachmentById(@Path("attachmentId") String attachmentId);

    @PUT("attachments/{attachmentId}")
    Call<Attachment> updateAttachment(@Path("attachmentId") String attachmentId, @Body Attachment attachment);

    @DELETE("attachments/{attachmentId}")
    Call<Attachment> deleteAttachment(@Path("attachmentId") String attachmentId);

    @GET("all-attachments")
    Call<List<Attachment>> getAllAttachment();

    @GET("progress-logs/{progress_log}/attachments")
    Call<List<Attachment>> getAttachmentsByProgressLog(@Path("progress_log") String progressLogId);

    @POST("progress-logs/{progress_log}/attachments")
    Call<Attachment> createAttachmentForProgressLog(@Path("progress_log") String progressLogId, @Body Attachment attachment);
}
