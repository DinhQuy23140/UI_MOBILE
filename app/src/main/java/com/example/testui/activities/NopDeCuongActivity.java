package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.Supabase.UploadManage;
import com.example.testui.ViewModel.NopBaoCaoViewModel;
import com.example.testui.ViewModel.NopDeCuongViewModel;
import com.example.testui.ViewModelFactory.NopBaoCaoViewModelFactory;
import com.example.testui.ViewModelFactory.NopDeCuongViewModelFactory;
import com.example.testui.adapter.ReportFileAdapter;
import com.example.testui.adapter.UploadAttachmentAdapter;
import com.example.testui.databinding.ActivityNopBaoCaoBinding;
import com.example.testui.databinding.ActivityNopDeCuongBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Assignment;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.ReportFile;
import com.example.testui.model.UploadFile;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NopDeCuongActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    Context context;
    Uri fileUri;
    Gson gson = new Gson();
    ArrayList<UploadFile> listUploadFile;
    List<ReportFile> listReportFile;
    UploadAttachmentAdapter uploadAttachmentAdapter;
    ReportFileAdapter reportFileAdapter;
    UploadManage uploadManage;
    String strProjectTerm;
    ProjectTerm projectTerm;
    Assignment assignment;
    NopDeCuongViewModel nopDeCuongViewModel;
    String studentId;
    ActivityNopDeCuongBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNopDeCuongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        Intent intent = getIntent();
        strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        projectTerm = gson.fromJson(strProjectTerm, ProjectTerm.class);

        studentId = nopDeCuongViewModel.getStudentId();

        nopDeCuongViewModel.getAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());
        nopDeCuongViewModel.getAssignmentMutableLiveData().observe(this, result -> {
            assignment = result;
            nopDeCuongViewModel.getListReportFileByProjectId(assignment.getProject_id(), Constants.KEY_TYPE_REPORT_OUTLINE);
            Log.d("Assignment", gson.toJson(assignment));
        });

        binding.btnChonFile.setOnClickListener(selectFile -> {
            openFilePicker();
        });

        reportFileAdapter = new ReportFileAdapter(this, listReportFile, new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });

        binding.rvDocumentUpload.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvDocumentUpload.setAdapter(reportFileAdapter);


        nopDeCuongViewModel.getListReportFileByProjectIdMutableLiveData().observe(this, result -> {
            listReportFile = result;
            Log.d("ReportFileSize", String.valueOf(listReportFile.size()));
            reportFileAdapter.updateData(listReportFile);
        });

        uploadAttachmentAdapter = new UploadAttachmentAdapter(this, listUploadFile, new UploadDocumentClick() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onDelete(int position) {
                listUploadFile.remove(position);
                uploadAttachmentAdapter.updateData(listUploadFile);
            }

            @Override
            public void onDownload(int position) {

            }
        });

        binding.rvDocument.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvDocument.setAdapter(uploadAttachmentAdapter);

        binding.btnNopBaoCao.setOnClickListener(nopbaocao -> {
            if (!listUploadFile.isEmpty()) {
                uploadManage.uploadDocuments(listUploadFile, new UploadManage.UploadCallback() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onUploadSuccess(UploadFile uploadFile) {
                        Log.d("Response_upload", new Gson().toJson(uploadFile));
                        Log.d("ReportFile", gson.toJson(uploadFile.getReportFile()));
                        ReportFile reportFile = uploadFile.getReportFile();
                        nopDeCuongViewModel.uploadReportFile(reportFile);
                        listUploadFile.remove(0);
                        uploadAttachmentAdapter.updateData(listUploadFile);
                        nopDeCuongViewModel.getListReportFileByProjectId(assignment.getProject_id(), Constants.KEY_TYPE_REPORT_OUTLINE);
                    }

                    @Override
                    public void onUploadError(UploadFile uploadFile, Throwable t) {
                        Log.e("Response_Error", t.toString());
                    }
                });
            } else {
                Toast.makeText(context, "Vui lòng chọn file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void init() {
        context = this;
        listUploadFile = new ArrayList<>();
        uploadManage = new UploadManage();
        listReportFile = new ArrayList<>();
        nopDeCuongViewModel = new ViewModelProvider(this, new NopDeCuongViewModelFactory(this)).get(NopDeCuongViewModel.class);
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
            String fileName = safeFileName(getFileName(fileUri));
            String fileType = context.getContentResolver().getType(fileUri);
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = formatter.format(now);
//            Document document = new Document(fileName, "", "", formattedDateTime,
//                    TypeDocument.REPORT, fileType, "");
            File file = null;
            try {
                file = getFileFromUri(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String create_at = formatter.format(now);
            ReportFile reportFile = new ReportFile(fileName, fileType, "", assignment.getProject_id(), "outline", Constants.KEY_STATUS_REPORT_SUBMITTED);
            UploadFile uploadFile = new UploadFile(file, reportFile);
            listUploadFile.add(uploadFile);
            Log.d("UploadFile", gson.toJson(uploadFile));
            uploadAttachmentAdapter.updateData(listUploadFile);
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

    private String safeFileName(String fileName) {
        return fileName.replaceAll("\\s+", "_").toLowerCase().replaceAll("[^a-zA-Z0-9._-]", "");
    }
}