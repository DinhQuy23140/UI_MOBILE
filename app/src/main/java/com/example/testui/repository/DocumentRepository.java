package com.example.testui.repository;

import com.example.testui.client.Client;
import com.example.testui.service.ApiService;

public class DocumentRepository {
    ApiService apiService;
    public DocumentRepository() {
        apiService = Client.getInstance().create(ApiService.class);
    }


}
