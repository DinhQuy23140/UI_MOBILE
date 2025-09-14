package com.example.testui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.Assignment;
import com.example.testui.service.AssignmentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentRepository {
    AssignmentService assignmentService;
    MutableLiveData<Assignment> assignmentMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Assignment> assignmenUpdatetMutableLiveData = new MutableLiveData<>();

    public AssignmentRepository() {
        assignmentService = Client.getInstance().create(AssignmentService.class);
    }

    public void getAssignmentByStudentIdAndTermId(String studentId, String termId){
        Call<Assignment> call = assignmentService.getAssignmentByStudentIdAndTermId(studentId, termId);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()) {
                    assignmentMutableLiveData.setValue(response.body());
                } else {
                    Log.e("AssignmentRepository", "onResponse: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable throwable) {
                Log.e("AssignmentRepository", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentMutableLiveData;
    }

    public void updateProjectIdAssignmentByAssIdAndProId(String assignmentId, String projectId) {
        Call<Assignment> call = assignmentService.updateProjectIdAssignmentByAssIdAndProId(assignmentId, projectId);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()) {
                    assignmenUpdatetMutableLiveData.setValue(response.body());
                } else {
                    try{
                        if (response.errorBody() != null){
                            Log.e("Failure", "onFailure: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable throwable) {

            }
        });
    }

    public MutableLiveData<Assignment> getAssignmenUpdatetMutableLiveData() {
        return assignmenUpdatetMutableLiveData;
    }
}
