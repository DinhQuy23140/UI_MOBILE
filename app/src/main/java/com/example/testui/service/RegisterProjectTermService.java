package com.example.testui.service;

import com.example.testui.model.RegisterProjectTerm;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegisterProjectTermService {
    @GET("project-term-registrations")
    Observable<List<RegisterProjectTerm>> getAllRegisterProjectTerm();

    @GET("project-term-registrations/student/{studentId}")
    Observable<List<RegisterProjectTerm>> getRegisterProjectTermByStudentId(@Path("studentId") String studentId);

    @POST("project-term-registrations")
    Call <RegisterProjectTerm> registerProjectTerm(@Body RegisterProjectTerm registerProjectTerm);
}
