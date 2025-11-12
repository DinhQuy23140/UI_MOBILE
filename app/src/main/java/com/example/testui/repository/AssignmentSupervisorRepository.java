package com.example.testui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.service.AssignmentService;
import com.example.testui.service.AssignmentSupervisorService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentSupervisorRepository {

    MutableLiveData<Assignment> assignmentMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isCreateSuccess = new MutableLiveData<>();
    AssignmentService assignmentService;
    AssignmentSupervisorService assignmentSupervisorService;

    public AssignmentSupervisorRepository() {
        assignmentService = Client.getInstance().create(AssignmentService.class);
        assignmentSupervisorService = Client.getInstance().create(AssignmentSupervisorService.class);
    }

    public void getAssigmentByStudentIdAndTermId(String studentId, String termId) {
        Call<Assignment> call = assignmentService.getAssignmentByStudentIdAndTermId(studentId, termId);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()) {
                    assignmentMutableLiveData.setValue(response.body());
                    Log.d("API_SUCCESS", new Gson().toJson(response.body()));
                } else {
                    Log.e("API_ERROR", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable throwable) {
                Log.e("AssignmentSupervisor", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentMutableLiveData;
    }

    public void createAssignmentSupervisor(AssignmentSupervisor assignmentSupervisor) {
        Call<AssignmentSupervisor> call = assignmentSupervisorService.createAssignmentSupervisor(assignmentSupervisor);

        call.enqueue(new Callback<AssignmentSupervisor>() {
            @Override
            public void onResponse(Call<AssignmentSupervisor> call, Response<AssignmentSupervisor> response) {
                if (response.isSuccessful()) {
                    isCreateSuccess.postValue(true);
                    Log.i("AssignmentSupervisor", "Create success: " + new Gson().toJson(response.body()));
                } else {
                    isCreateSuccess.postValue(false);

                    String errorMsg = "Unknown error";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        Log.e("AssignmentSupervisor", "Error reading errorBody", e);
                    }

                    Log.e("AssignmentSupervisor",
                            "Create failed - Code: " + response.code()
                                    + " | Message: " + response.message()
                                    + " | Error: " + errorMsg);
                }
            }

            @Override
            public void onFailure(Call<AssignmentSupervisor> call, Throwable throwable) {
                isCreateSuccess.postValue(false);
                Log.e("AssignmentSupervisor", "Request failed: " + throwable.getClass().getSimpleName()
                        + " | Message: " + throwable.getMessage(), throwable);
            }
        });
    }

    public MutableLiveData<Boolean> getIsCreateSuccess() {
        return isCreateSuccess;
    }
}
