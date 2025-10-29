package com.example.testui.ViewModel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.Supabase.UploadManage;
import com.example.testui.model.Attachment;
import com.example.testui.model.Status;
import com.example.testui.model.UploadAttachment;
import com.example.testui.repository.AttrachmentRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProgressLogDetailViewModel extends ViewModel {
    Context context;
    AttrachmentRepository attrachmentRepository;

    public ProgressLogDetailViewModel(Context context) {
        this.context = context;
        attrachmentRepository = new AttrachmentRepository(context);
    }

    public Status loadStatusProgress(String status) {
        switch (status) {
            case "pending":
                return new Status(R.drawable.bg_task_icon, "Chưa bắt đầu");
            case "in_progress":
                return new Status(R.drawable.bg_primary_button, "Đang tiến hành");
            case "not_completed":
                return new Status(R.drawable.bg_icon_success, "Chưa hoàn thành");
            case "completed":
                return new Status(R.drawable.bg_icon_accent, "Hoàn thành");
            default:
                return new Status(R.drawable.bg_task_icon, "Chưa bắt đầu");
        }
    }

    public Status loadInstructorStatus(String status) {
        switch(status) {
            case "pending":
                return new Status(R.drawable.bg_icon_primary, "Chưa duyệt");
            case "approved":
                return new Status(R.drawable.bg_icon_accent, "Đạt");
            case "need_editing":
                return new Status(R.drawable.bg_icon_success, "Cần chỉnh sửa");
            case "not_achieved":
                return new Status(R.drawable.bg_status_pending, "Chưa đạt");
            default:
                return new Status(R.drawable.bg_icon_primary, "Chưa duyệt");
        }
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".tmp", context.getCacheDir());
        FileOutputStream out = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
        inputStream.close();
        return tempFile;
    }

    public String safeFileName(String fileName) {
        return fileName.replaceAll("\\s+", "_").toLowerCase().replaceAll("[^a-zA-Z0-9._-]", "");
    }

    public void uploadProgressLogAttachment(Attachment attachment) {
        attrachmentRepository.uploadAttachment(attachment);
    }
}
