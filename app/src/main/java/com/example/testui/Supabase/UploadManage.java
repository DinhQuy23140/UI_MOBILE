package com.example.testui.Supabase;

import android.content.Context;
import android.util.Log;

import com.example.testui.client.SupabaseClient;
import com.example.testui.model.Document;
import com.example.testui.model.ReportFile;
import com.example.testui.model.UploadFile;
import com.example.testui.repository.SinhVienRepository;
import com.example.testui.service.SupabaseService;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadManage {
    private SupabaseService supabaseService;
    private String bucket = "document";
    private String url = "https://wxvjcevcyelpobqleeti.supabase.co";
    private String apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind4dmpjZXZjeWVscG9icWxlZXRpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQ2OTkyMzgsImV4cCI6MjA3MDI3NTIzOH0.T9pxOQqLz0LOBGXer1zT8nTAHRHzwRUTBwTh_N7KYN0";
    private String bearerToken = "Bearer " + apikey;
    private SinhVienRepository sinhVienRepository;

    public UploadManage(Context context) {
        this.supabaseService = SupabaseClient.getInstance(url).create(SupabaseService.class);
        sinhVienRepository = new SinhVienRepository(context);
    }

    public void uploadDocuments(List<UploadFile> listUploadFile, UploadCallback uploadCallback) {
        for (UploadFile uploadFile : listUploadFile) {
            uploadDocument(uploadFile, uploadCallback);
        }
    }

    private void uploadDocument(UploadFile uploadFile, UploadCallback uploadCallback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile.getFile());
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", uploadFile.getReportFile().getFile_name(), requestBody);
        String studentId = sinhVienRepository.getStudentId();
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
        String storagePath = studentId + "/" + uploadFile.getReportFile().getType_report() + "/" + timestamp + "/" + uploadFile.getReportFile().getFile_name();
        Call<ResponseBody> call = supabaseService.uploadDocument(
                bearerToken,
                apikey,
                bucket,
                storagePath, // 👈 thay vì chỉ là file_name
                multipartBody
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String publicUrl = url + "/storage/v1/object/public/" + bucket + "/" + storagePath;
                    ReportFile reportFile = uploadFile.getReportFile();
                    reportFile.setFile_url(publicUrl);
                    uploadFile.setReportFile(reportFile);

                    // Gọi callback
                    uploadCallback.onUploadSuccess(uploadFile);

                    Log.d("UPLOAD", "Uploaded: " + publicUrl);
                } else {
                    uploadCallback.onUploadError(uploadFile, new Exception("Upload failed: " + response.message()));
                    try {
                        Log.e("UPLOAD", "Failed to upload: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                uploadCallback.onUploadError(uploadFile, throwable);
            }
        });
    }

    public void uploadProgressLogAttachments(List<UploadFile> listUploadFile, UploadCallback uploadCallback) {

    }

    private void uploadProgressLogAttachment(UploadFile uploadFile, UploadCallback uploadCallback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile.getFile());
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", uploadFile.getReportFile().getFile_name(), requestBody);
        String studentId = sinhVienRepository.getStudentId();
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss"));
        String storagePath = studentId + "/" + uploadFile.getReportFile().getType_report() + "/" + timestamp + "/" + uploadFile.getReportFile().getFile_name();
        Call<ResponseBody> call = supabaseService.uploadDocument(
                bearerToken,
                apikey,
                bucket,
                storagePath, // 👈 thay vì chỉ là file_name
                multipartBody
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String publicUrl = url + "/storage/v1/object/public/" + bucket + "/" + storagePath;
                    ReportFile reportFile = uploadFile.getReportFile();
                    reportFile.setFile_url(publicUrl);
                    uploadFile.setReportFile(reportFile);

                    // Gọi callback
                    uploadCallback.onUploadSuccess(uploadFile);

                    Log.d("UPLOAD", "Uploaded: " + publicUrl);
                } else {
                    uploadCallback.onUploadError(uploadFile, new Exception("Upload failed: " + response.message()));
                    try {
                        Log.e("UPLOAD", "Failed to upload: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                uploadCallback.onUploadError(uploadFile, throwable);
            }
        });
    }

    public interface UploadCallback {
        void onUploadSuccess(UploadFile uploadFile);
        void onUploadError(UploadFile uploadFile, Throwable t);
    }
}
