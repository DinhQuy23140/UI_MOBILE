package com.example.testui.service;

import com.example.testui.model.AcademyYear;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AcademyYearService {
    @GET("academy-years")
    Call<List<AcademyYear>> getAllAcademyYear();

    @POST("academy-years")
    Call<AcademyYear> createAcademyYear(@Body AcademyYear academyYear);

    @GET("academy-years/{academyYearId}")
    Call<AcademyYear> getAcademyYearById(@Path("academyYearId") String academyYearId);

    @PUT("academy-years/{academyYearId}")
    Call<AcademyYear> updateAcademyYear(@Path("academyYearId") String academyYearId, @Body AcademyYear academyYear);

    @DELETE("academy-years/{academyYearId}")
    Call<AcademyYear> deleteAcademyYear(@Path("academyYearId") String academyYearId);
}
