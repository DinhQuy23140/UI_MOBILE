package com.example.testui.activities;

import android.app.ComponentCaller;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.Supabase.UploadManage;
import com.example.testui.adapter.AttachmentAdapter;
import com.example.testui.adapter.UploadAttachmentAdapter;
import com.example.testui.databinding.ActivityNopBaoCaoBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Attachment;
import com.example.testui.model.Document;
import com.example.testui.model.TypeDocument;
import com.example.testui.model.UploadFile;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NopBaoCaoActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    Context context;
    Uri fileUri;
    Gson gson = new Gson();
    ArrayList<Document> listDocument;
    ArrayList<Attachment> listAttachment;
    ArrayList<UploadFile> listUploadFile;
    AttachmentAdapter attachmentAdapter;
    UploadAttachmentAdapter uploadAttachmentAdapter;
    ActivityNopBaoCaoBinding binding;
    UploadManage uploadManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNopBaoCaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = this;
        listDocument = new ArrayList<>();
        listAttachment = new ArrayList<>();
        listUploadFile = new ArrayList<>();
        uploadManage = new UploadManage();
        binding.btnChonFile.setOnClickListener(selectFile -> {
            openFilePicker();
        });

        uploadAttachmentAdapter = new UploadAttachmentAdapter(this, listDocument, new UploadDocumentClick() {
            @Override
            public void onClick(int position) {
                
            }

            @Override
            public void onDelete(int position) {
                listDocument.remove(position);
                uploadAttachmentAdapter.updateData(listDocument);
            }

            @Override
            public void onDownload(int position) {

            }
        });

        attachmentAdapter = new AttachmentAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvDocument.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvDocument.setAdapter(uploadAttachmentAdapter);

        binding.btnNopBaoCao.setOnClickListener(nopbaocao -> {
            uploadManage.uploadDocuments(listUploadFile, new UploadManage.UploadCallback() {
                @Override
                public void onUploadSuccess(UploadFile uploadFile) {

                }

                @Override
                public void onUploadError(UploadFile uploadFile, Throwable t) {

                }
            });
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            String fileName = getFileName(fileUri);
            String fileType = context.getContentResolver().getType(fileUri);
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = formatter.format(now);
            Document document = new Document(fileName, "", "", formattedDateTime,
                    TypeDocument.REPORT, fileType, "");
            File file = null;
            try {
                file = getFileFromUri(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            UploadFile uploadFile = new UploadFile(fileName, "", "", formattedDateTime, TypeDocument.REPORT, fileType, "", file, "");
            listUploadFile.add(uploadFile);
            Log.d("Document", gson.toJson(document));
            listDocument.add(document);
            uploadAttachmentAdapter.updateData(listDocument);
            Log.d("SizeAdapter", String.valueOf(uploadAttachmentAdapter.getListDocument().size()));
        }
    }

    private File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".tmp", getCacheDir());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                // All permissions granted
                openFilePicker();
            } else {
                // Permission denied
                Toast.makeText(this, "Quyền truy cập bị từ chối.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileName(Uri uri) {
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
}