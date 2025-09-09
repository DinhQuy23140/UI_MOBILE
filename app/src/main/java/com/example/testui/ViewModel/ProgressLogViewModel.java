package com.example.testui.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.ProgressLog;
import com.example.testui.repository.ProgressLogRepository;

import java.util.List;

public class ProgressLogViewModel extends ViewModel {
    ProgressLogRepository progressLogRepository;
    MutableLiveData<List<ProgressLog>> progressLog = new MutableLiveData<>();
    MutableLiveData<List<ProgressLog>> progressLogByProjectId = new MutableLiveData<>();

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
}
