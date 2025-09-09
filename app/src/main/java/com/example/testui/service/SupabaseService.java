package com.example.testui.service;

import com.example.testui.model.Document;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SupabaseService {
    void uploadDocument(List<Document> document);

    @Multipart
    @POST("storage/v1/object/{bucket}/{path}")
    Call<ResponseBody> uploadDocument(
            @Header("Authorization") String bearer,
            @Header("apikey") String apiKey,
            @Path("bucket") String bucket,
            @Path(value = "path", encoded = true) String path,
            @Part MultipartBody.Part file
    );

}
