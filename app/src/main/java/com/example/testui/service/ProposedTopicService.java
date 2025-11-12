package com.example.testui.service;

import com.example.testui.model.ProposedTopic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProposedTopicService {
    @GET("proposed-topics/assignment/{assignmentId}")
    Call<List<ProposedTopic>> getProposedTopicByAssignmentId(@Path("assignmentId") String assignmentId);
}
