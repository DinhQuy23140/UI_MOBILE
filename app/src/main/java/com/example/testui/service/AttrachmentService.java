package com.example.testui.service;

import com.example.testui.model.Attachment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AttrachmentService {
    @GET("attachment")
    Call<List<Attachment>> getAllAttachment();

    @GET("attachment/progress/{id}")
    Call<List<Attachment>> getAttachmentByProgressId(@Query("progressId") String progressId);
}
