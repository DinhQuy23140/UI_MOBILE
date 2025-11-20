package com.example.testui.service;

import com.example.testui.model.PostponeProjectTerm;
import com.example.testui.model.PostponeProjectTermFile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostponeProjectTermService {
    @POST("postpone-project-terms")
    Call<PostponeProjectTerm> createPostponeProjectTerm(@Body PostponeProjectTerm postponeProjectTerm);

    @DELETE("postpone-project-terms/{postpone_project_term}")
    Call<PostponeProjectTerm> cancelPostponeProjectTerm(@Path("postpone_project_term") String postpone_project_term);
}
