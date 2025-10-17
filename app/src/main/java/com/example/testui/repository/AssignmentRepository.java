package com.example.testui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.Assignment;
import com.example.testui.service.AssignmentService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentRepository {
    AssignmentService assignmentService;
    MutableLiveData<Assignment> assignmenUpdatetMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Assignment> assignmentByStudentIdAndTermIdMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Assignment>> listAssignment = new MutableLiveData<>();

    public AssignmentRepository() {
        assignmentService = Client.getInstance().create(AssignmentService.class);
    }

    public void loadAssignmentByStudentIdAndTermId(String studentId, String termId){
        Call<Assignment> call = assignmentService.getAssignmentByStudentIdAndTermId(studentId, termId);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()) {
                    assignmentByStudentIdAndTermIdMutableLiveData.setValue(response.body());
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

    public MutableLiveData<Assignment> getAssignmentByStudentIdAndTermId() {
        return assignmentByStudentIdAndTermIdMutableLiveData;
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

    public void getAssignmentsByTeacherId(String teacherId) {
        Call<List<Assignment>> call = assignmentService.getAssignmentsByTeacherId(teacherId);

        Log.d("API_URL", call.request().url().toString()); // 👈 In URL thật để kiểm tra

        call.enqueue(new Callback<List<Assignment>>() {
            @Override
            public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
                if (response.isSuccessful()) {
                    listAssignment.setValue(response.body());
                } else {
                    try {
                        String error = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e("API_ERROR", error);
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error reading errorBody()", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Assignment>> call, Throwable t) {
                Log.e("API_FAIL", "onFailure: " + t.getMessage(), t);
            }
        });
    }


    public MutableLiveData<List<Assignment>> getListAssignment() {
        return listAssignment;
    }
}
