package com.example.testui.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.ProposedTopic;
import com.example.testui.service.ProposedTopicService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProposedTopicRepository {
    ProposedTopicService proposedTopicService;
    MutableLiveData<List<ProposedTopic>> listProposedTopic = new MutableLiveData<>();

    public ProposedTopicRepository() {
        proposedTopicService = Client.getInstance().create(ProposedTopicService.class);
    }

    public void getProposedTopicByAssignmentId(String assignmentId) {
        proposedTopicService.getProposedTopicByAssignmentId(assignmentId)
                .enqueue(new Callback<List<ProposedTopic>>() {
                    @Override
                    public void onResponse(Call<List<ProposedTopic>> call, Response<List<ProposedTopic>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listProposedTopic.postValue(response.body());
                            Log.d("ProposedTopicRepository", "Success: " + new Gson().toJson(response.body()));
                        } else {
                            listProposedTopic.postValue(null);
                            Log.e("ProposedTopicRepository", "Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProposedTopic>> call, Throwable t) {
                        listProposedTopic.postValue(null);
                        Log.e("ProposedTopicRepository", "Failure: " + t.getMessage(), t);
                    }
                });
    }

    public MutableLiveData<List<ProposedTopic>> getListProposedTopic() {
        return listProposedTopic;
    }
}
