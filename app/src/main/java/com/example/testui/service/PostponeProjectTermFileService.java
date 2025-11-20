package com.example.testui.service;

import com.example.testui.model.PostponeProjectTermFile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostponeProjectTermFileService {
    @POST("postpone-project-term-files")
    Call<PostponeProjectTermFile> createPostponeProjectTermFile(@Body PostponeProjectTermFile postponeProjectTermFile);
}
