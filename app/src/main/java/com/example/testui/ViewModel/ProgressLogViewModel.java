package com.example.testui.ViewModel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.ProgressLog;
import com.example.testui.repository.ProgressLogRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ProgressLogViewModel extends ViewModel {
    ProgressLogRepository progressLogRepository;
    MutableLiveData<List<ProgressLog>> progressLog = new MutableLiveData<>();
    MutableLiveData<List<ProgressLog>> progressLogByProjectId = new MutableLiveData<>();
    MutableLiveData<Boolean> isCreateSuccess = new MutableLiveData<>();
    MutableLiveData<ProgressLog> progressLogMutableLiveData = new MutableLiveData<>();

    public ProgressLogViewModel() {
        this.progressLogRepository = new ProgressLogRepository();
    }

    public void getAllProgressLog() {
        progressLogRepository.getAllProgressLog();
        progressLog = progressLogRepository.getProgressLogList();
    }

    public MutableLiveData<List<ProgressLog>> getProgressLog() {
        return progressLog;
    }

    public void getProgressLogByProjectId(String projectId) {
        progressLogRepository.getProgressLogByProjectId(projectId);
        progressLogByProjectId = progressLogRepository.getProgressLogByProjectId();
    }

    public MutableLiveData<List<ProgressLog>> getProgressLogByProjectId() {
        return progressLogByProjectId;
    }

    public int countProgressProcess(List<ProgressLog> listProgress) {
        int count = 0;
        for (ProgressLog progressLog : listProgress) {
            if (progressLog.getStudent_status().equals("in_progress")) count ++;
        }
        return  count;
    }

    public int countProgressComplete(List<ProgressLog> listProgress) {
        int count = 0;
        for (ProgressLog progressLog : listProgress) {
            if (progressLog.getStudent_status().equals("completed")) count ++;
        }
        return  count;
    }

    public void createProgressLog(Map<String, String> progressLog) {
        progressLogRepository.createProgressLog(progressLog);
        progressLogMutableLiveData = progressLogRepository.getResponseProgressLog();
    }

    public MutableLiveData<ProgressLog> getProgressLogMutableLiveData() {
        return progressLogMutableLiveData;
    }
}
