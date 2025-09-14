package com.example.testui.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.ReportFile;
import com.example.testui.service.ReportFileService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFileRepository {
    private Context context;
    private ReportFileService reportFileService;
    private MutableLiveData<ReportFile> reportFileMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ReportFile>> listReportFileByProjectId = new MutableLiveData<>();

    public ReportFileRepository(Context context) {
        this.context = context;
        reportFileService = Client.getInstance().create(ReportFileService.class);
    }

    public void uploadReportFile(ReportFile reportFile) {
        Call<ReportFile> call = reportFileService.createReportFile(reportFile);
        call.enqueue(new Callback<ReportFile>() {
            @Override
            public void onResponse(Call<ReportFile> call, Response<ReportFile> response) {
                if (response.isSuccessful()) {

                }else {
                    try{
                        if (response.errorBody() != null) {
                            Log.e("Failure", "onFailure: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportFile> call, Throwable throwable) {
                Log.e("Error_reportfile", throwable.toString());
            }
        });
    }

    public MutableLiveData<ReportFile> getReportFileMutableLiveData() {
        return reportFileMutableLiveData;
    }

    public void getListReportFileByProjectId(String projectId, String typeReport) {
        Call<List<ReportFile>> call = reportFileService.getListReportFileByProjectId(projectId, typeReport);
        call.enqueue(new Callback<List<ReportFile>>() {
            @Override
            public void onResponse(Call<List<ReportFile>> call, Response<List<ReportFile>> response) {
                if (response.isSuccessful()) {
                    listReportFileByProjectId.setValue(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Log.e("Failure", "onFailure: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ReportFile>> call, Throwable throwable) {
                Log.e("Error_reportfile", throwable.toString());
            }
        });
    }

    public MutableLiveData<List<ReportFile>> getListReportFileByProjectIdMutableLiveData() {
        return listReportFileByProjectId;
    }
}
