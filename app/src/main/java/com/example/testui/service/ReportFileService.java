package com.example.testui.service;

import com.example.testui.model.ReportFile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportFileService {
    @GET("reportFile")
    Call<List<ReportFile>> getAllReportFile();

    @POST("report-files")
    Call<ReportFile> createReportFile(@Body ReportFile reportFile);

    @GET("report-files/project/{projectId}/type/{typeReport}")
    Call<List<ReportFile>> getListReportFileByProjectId(@Path("projectId") String projectId, @Path("typeReport") String typeReport);
}
