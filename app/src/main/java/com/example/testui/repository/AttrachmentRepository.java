package com.example.testui.repository;

import android.content.Context;
import android.util.Log;

import com.example.testui.client.Client;
import com.example.testui.model.Attachment;
import com.example.testui.service.AttachmentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttrachmentRepository {
    Context context;
    AttachmentService attachmentService;

    public AttrachmentRepository(Context context) {
        this.context = context;
        attachmentService = Client.getInstance().create(AttachmentService.class);
    }

    public void uploadAttachment(Attachment attachment) {
        attachmentService.createAttachmentGeneral(attachment).enqueue(new Callback<Attachment>() {
            @Override
            public void onResponse(Call<Attachment> call, Response<Attachment> response) {
                if (response.isSuccessful()) {

                }else {
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
            public void onFailure(Call<Attachment> call, Throwable throwable) {
                Log.e("Failure", "onFailure: " + throwable.toString());
            }
        });
    }
}
