package com.example.testui.service;

import com.example.testui.model.AssignmentSupervisor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AssignmentSupervisorService {
    @POST("assignment-supervisors")
    Call<AssignmentSupervisor> createAssignmentSupervisor(@Body AssignmentSupervisor assignmentSupervisor);
}
