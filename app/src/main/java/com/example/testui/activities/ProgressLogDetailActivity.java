package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ProgressLogDetailViewModel;
import com.example.testui.adapter.AttachmentAdapter;
import com.example.testui.adapter.UploadAttachmentAdapter;
import com.example.testui.databinding.ActivityProgressLogDetailBinding;
import com.example.testui.databinding.LayoutDialogUploadBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Assignment;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.ReportFile;
import com.example.testui.model.Status;
import com.example.testui.model.UploadFile;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProgressLogFormatter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProgressLogDetailActivity extends AppCompatActivity {
    ActivityProgressLogDetailBinding binding;
    AttachmentAdapter adapter;
    Intent intent;
    String progressLogJson = "";
    ProgressLog progressLog;
    Gson gson;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    ProgressLogDetailViewModel progressLogDetailViewModel;
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ArrayList<UploadFile> listUploadFile;
    Uri fileUri;
    Context context;
    UploadAttachmentAdapter uploadAttachmentAdapter;
    Assignment assignment;
    String strAssignment = "";
    LayoutDialogUploadBinding dialogUploadBinding;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProgressLogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        setupRecyclerView();
        loadData();
    }

    void init() {
        context = this;
        listUploadFile = new ArrayList<>();
        intent = getIntent();
        gson = new Gson();
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignment, Assignment.class));
        progressLogJson = intent.getStringExtra(Constants.KEY_PROGRESS_LOG);
        progressLog = ProgressLogFormatter.format(gson.fromJson(progressLogJson, ProgressLog.class));
        progressLogDetailViewModel = new ProgressLogDetailViewModel(this);
    }

    void setupRecyclerView() {
        binding.rvAttachments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AttachmentAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvAttachments.setAdapter(adapter);
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });

        binding.btnUploadAttachment.setOnClickListener(upload -> {
            createDialog();
            builder.show();
        });
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    void loadData() {
        binding.tvTitle.setText(progressLog.getTitle());
        binding.tvDescription.setText("Mô tả: " + progressLog.getDescription());
        binding.tvStartTime.setText("Ngày bắt đầu: " + DateFormatter.formatDate(progressLog.getStart_date_time()));
        binding.tvEndTime.setText("Ngày kết thúc: " + DateFormatter.formatDate(progressLog.getEnd_date_time()));

        Status status = progressLogDetailViewModel.loadStatusProgress(progressLog.getStudent_status());
        binding.tvStudentStatus.setText("Trạng thái: " + status.getStrStatus());
        binding.tvStudentStatus.setBackground(getDrawable(status.getBackgroundColor()));

        Status instructorStatus = progressLogDetailViewModel.loadInstructorStatus(progressLog.getInstructor_status());
        binding.tvInstructorStatus.setText("Trạng thái: " + instructorStatus.getStrStatus());
        binding.tvInstructorStatus.setBackground(getDrawable(instructorStatus.getBackgroundColor()));
        String comment = (progressLog.getInstructor_comment() != null && !progressLog.getInstructor_comment().isEmpty()) ? progressLog.getInstructor_comment() : "Chưa có nhận xét";
        binding.tvInstructorComment.setText(comment);
        if (!progressLog.getAttachments().isEmpty()) {
            binding.tvAttachmentCount.setText(progressLog.getAttachments().size() + " file");
            adapter.updateData(progressLog.getAttachments());
        } else {
            binding.tvEmptyAttachment.setVisibility(View.VISIBLE);
        }
    }

    void createDialog() {
        builder = new AlertDialog.Builder(ProgressLogDetailActivity.this, R.style.FullScreenDialogTheme);
        dialogUploadBinding = LayoutDialogUploadBinding.inflate(getLayoutInflater());
        builder.setView(dialogUploadBinding.getRoot());
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialogUploadBinding.rvFileList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        uploadAttachmentAdapter = new UploadAttachmentAdapter(this, new ArrayList<>(), new UploadDocumentClick() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onDelete(int position) {

            }

            @Override
            public void onDownload(int position) {

            }
        });
        dialogUploadBinding.rvFileList.setAdapter(uploadAttachmentAdapter);

        dialogUploadBinding.btnChooseFile.setOnClickListener(choose -> {
            openFilePicker();
        });

        dialogUploadBinding.btnCancel.setOnClickListener(cancel -> {
            dialog.dismiss();
        });

        dialogUploadBinding.btnSubmit.setOnClickListener(submit -> {
            dialog.dismiss();
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
            String fileName = progressLogDetailViewModel.safeFileName(progressLogDetailViewModel.getFileName(fileUri));
            String fileType = context.getContentResolver().getType(fileUri);
            File file = null;
            try {
                file = progressLogDetailViewModel.getFileFromUri(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ReportFile reportFile = new ReportFile(fileName, fileType, "", assignment.getProject_id(), "outline", Constants.KEY_STATUS_REPORT_SUBMITTED);
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
}