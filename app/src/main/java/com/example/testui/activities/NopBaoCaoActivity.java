package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.testui.ViewModelFactory.NopBaoCaoViewModelFactory;
import com.example.testui.adapter.ReportFileAdapter;
import com.example.testui.adapter.UploadAttachmentAdapter;
import com.example.testui.databinding.ActivityNopBaoCaoBinding;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.ReportFile;
import com.example.testui.model.StageTimeline;
import com.example.testui.model.UploadFile;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StageTimelineFormatter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NopBaoCaoActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    Context context;
    Uri fileUri;
    Gson gson = new Gson();
    ArrayList<UploadFile> listUploadFile;
    List<ReportFile> listReportFile;
    UploadAttachmentAdapter uploadAttachmentAdapter;
    ReportFileAdapter reportFileAdapter;
    ActivityNopBaoCaoBinding binding;
    UploadManage uploadManage;
    String strProjectTerm;
    ProjectTerm projectTerm;
    Assignment assignment;
    NopBaoCaoViewModel nopBaoCaoViewModel;
    String studentId;
    Intent intent;
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

        init();
        observerData();
        setupRecyclerViewReportFile();
        setupRecyclerViewAttachment();
        fetchDataRecyclerView();
        setupClick();
    }

    void init() {
        context = this;
        listUploadFile = new ArrayList<>();
        uploadManage = new UploadManage(this);
        listReportFile = new ArrayList<>();
        nopBaoCaoViewModel = new ViewModelProvider(this, new NopBaoCaoViewModelFactory(this)).get(NopBaoCaoViewModel.class);
        intent = getIntent();
        strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        projectTerm = ProjectTermFormatter.format(gson.fromJson(strProjectTerm, ProjectTerm.class));
        studentId = nopBaoCaoViewModel.getStudentId();
    }

    void setupClick() {
        binding.dragDropArea.setOnClickListener(selectFile -> openFilePicker());

        binding.btnNopBaoCao.setOnClickListener(nopbaocao -> {
            if (!listUploadFile.isEmpty()) {
                uploadManage.uploadDocuments(listUploadFile, new UploadManage.UploadCallback() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onUploadSuccess(UploadFile uploadFile) {
                        Log.d("Response_upload", new Gson().toJson(uploadFile));
                        Log.d("ReportFile", gson.toJson(uploadFile.getReportFile()));
                        ReportFile reportFile = uploadFile.getReportFile();
                        nopBaoCaoViewModel.uploadReportFile(reportFile);
                        listUploadFile.remove(0);
                        uploadAttachmentAdapter.updateData(listUploadFile);
                        nopBaoCaoViewModel.getListReportFileByProjectId(assignment.getProject_id(), Constants.KEY_TYPE_REPORT_REPORT);
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
            String fileName = nopBaoCaoViewModel.safeFileName(nopBaoCaoViewModel.getFileName(fileUri));
            String fileType = context.getContentResolver().getType(fileUri);
            File file = null;
            try {
                file = nopBaoCaoViewModel.getFileFromUri(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ReportFile reportFile = new ReportFile(fileName, fileType, "", assignment.getProject_id(), Constants.KEY_TYPE_REPORT_REPORT, Constants.KEY_STATUS_REPORT_SUBMITTED);
            UploadFile uploadFile = new UploadFile(file, reportFile);
            listUploadFile.add(uploadFile);
            Log.d("UploadFile", gson.toJson(uploadFile));
            uploadAttachmentAdapter.updateData(listUploadFile);
            Log.d("SizeAdapter", String.valueOf(uploadAttachmentAdapter.getListDocument().size()));
        }
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

    void setupRecyclerViewReportFile() {
        reportFileAdapter = new ReportFileAdapter(this, listReportFile, position -> {

        });
        binding.rvDocumentUpload.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvDocumentUpload.setAdapter(reportFileAdapter);
    }

    void setupRecyclerViewAttachment() {
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
    }

    void fetchDataRecyclerView() {
        nopBaoCaoViewModel.getAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());
        nopBaoCaoViewModel.getAssignmentMutableLiveData().observe(this, result -> {
            assignment = AssignmentFormatter.format(result);
            nopBaoCaoViewModel.getListReportFileByProjectId(assignment.getProject_id(), "report");
            Log.d("Assignment", gson.toJson(assignment));
            loadData();
        });

        nopBaoCaoViewModel.getListReportFileByProjectIdMutableLiveData().observe(this, result -> {
            listReportFile = result;
            Log.d("ReportFileSize", String.valueOf(listReportFile.size()));
            reportFileAdapter.updateData(listReportFile);
            binding.tvSubmissionCount.setText(String.valueOf(listReportFile.size()));
        });
    }

    void loadData() {
        Project project = ProjectFormatter.format(assignment.getProject());
        binding.tvTenDeTai.setText("Đề tài: " + project.getName());
        binding.tvMoTaDeTai.setText("Mô tả: " + project.getDescription());
        List<StageTimeline> listStage = projectTerm.getStage_timelines();
        StageTimeline stageTimeline = StageTimelineFormatter.format(listStage.get(1));
        String startDate = DateFormatter.formatDate(stageTimeline.getStart_date());
        String endDate = DateFormatter.formatDate(stageTimeline.getEnd_date());
        binding.tvThoiGianNop.setText(startDate + " - " + endDate);
    }

    void observerData() {
        nopBaoCaoViewModel.getIsCreateSuccess().observe(this, result -> {
            if (result) {
                Toast.makeText(context, "Upload thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Upload cáo thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}