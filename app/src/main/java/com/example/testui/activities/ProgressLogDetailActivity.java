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
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.Supabase.UploadManage;
import com.example.testui.ViewModel.ProgressLogDetailViewModel;
import com.example.testui.adapter.AttachmentAdapter;
import com.example.testui.adapter.UploadAttachmentAdapter;
import com.example.testui.adapter.UploadReportLogAdapter;
import com.example.testui.adapter.WorkLogAdapter;
import com.example.testui.databinding.ActivityProgressLogDetailBinding;
import com.example.testui.databinding.AddItemLogBinding;
import com.example.testui.databinding.LayoutDialogUploadBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Assignment;
import com.example.testui.model.Attachment;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.ReportFile;
import com.example.testui.model.Status;
import com.example.testui.model.UploadAttachment;
import com.example.testui.model.UploadFile;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProgressLogFormatter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressLogDetailActivity extends AppCompatActivity {
    ActivityProgressLogDetailBinding binding;
    AttachmentAdapter adapter;
    Intent intent;
    String progressLogJson = "";
    ProgressLog progressLog;
    Gson gson;
    AlertDialog dialog, dialogEdit;
    AlertDialog.Builder builder, alertBuilder;
    ProgressLogDetailViewModel progressLogDetailViewModel;
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ArrayList<UploadFile> listUploadFile;
    Uri fileUri;
    Context context;
    UploadReportLogAdapter uploadAttachmentAdapter;
    Assignment assignment;
    String strAssignment = "";
    LayoutDialogUploadBinding dialogUploadBinding;
    UploadManage uploadManage;
    List<UploadAttachment> uploadAttachmentList = new ArrayList<>();
    List<String> listWork = new ArrayList<>();
    WorkLogAdapter workLogAdapter;
    AddItemLogBinding addItemLogBinding;

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
        createDialogEdit();
        setupClick();
        setupRecyclerView();
        loadData();
    }

    void init() {
        context = this;
        listUploadFile = new ArrayList<>();
        intent = getIntent();
        gson = new Gson();
        uploadManage = new UploadManage(this);
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

        binding.rvWeeklyTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        workLogAdapter = new WorkLogAdapter(this, new ArrayList<>());
        binding.rvWeeklyTasks.setAdapter(workLogAdapter);
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });

        binding.btnUploadAttachment.setOnClickListener(upload -> {
            createDialog();
            dialog.show();
        });

        addItemLogBinding.btnSave.setOnClickListener(save -> {
            dialogEdit.dismiss();
        });

        binding.tvEditProgress.setOnClickListener(edit -> {
            dialogEdit.show();
            if (dialogEdit.getWindow() != null) {
                dialogEdit.getWindow().setLayout(
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.9)
                );
                dialogEdit.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            loadDataDialogEdit();
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

        if (progressLog.getContent() != null && !progressLog.getContent().isEmpty()) {
            listWork = Arrays.asList(progressLog.getContent().split("\n"));
            binding.tvTaskCount.setText(listWork.size() + " nhiệm vụ");
            Toast.makeText(this, String.valueOf(listWork.size()), Toast.LENGTH_SHORT).show();
            workLogAdapter.updateData(listWork);
        }

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

        adapter.updateData(progressLog.getAttachments());
    }

    void loadDataDialogEdit(){
        addItemLogBinding.etTaskName.setText(progressLog.getTitle());
        addItemLogBinding.etDescription.setText(progressLog.getDescription());
        addItemLogBinding.etContent.setText(progressLog.getContent());
        addItemLogBinding.etStartDate.setText(DateFormatter.formatDate(progressLog.getStart_date_time()));
        addItemLogBinding.etEndDate.setText(DateFormatter.formatDate(progressLog.getEnd_date_time()));
    }

    void createDialog() {
        builder = new AlertDialog.Builder(ProgressLogDetailActivity.this, R.style.FullScreenDialogTheme);
        dialogUploadBinding = LayoutDialogUploadBinding.inflate(getLayoutInflater());
        builder.setView(dialogUploadBinding.getRoot());
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialogUploadBinding.rvFileList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        uploadAttachmentAdapter = new UploadReportLogAdapter(this, new ArrayList<>(), new UploadDocumentClick() {
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
            if (!uploadAttachmentList.isEmpty()) {
                dialog.dismiss();
                uploadManage.uploadProgressLogAttachments(uploadAttachmentList, new UploadManage.UploadAttachmentCallback() {
                    @Override
                    public void onUploadSuccess(UploadAttachment uploadAttachment) {
                        Attachment attachment = uploadAttachment.getAttachment();
                        progressLogDetailViewModel.uploadProgressLogAttachment(attachment);
                        uploadAttachmentList.remove(0);
                    }

                    @Override
                    public void onUploadError(UploadAttachment uploadAttachment, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(this, "Vui lòng chọn file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void createDialogEdit() {
        alertBuilder = new AlertDialog.Builder(this, R.style.FullScreenDialogTheme);
        addItemLogBinding = AddItemLogBinding.inflate(getLayoutInflater());
        alertBuilder.setView(addItemLogBinding.getRoot());
        dialogEdit = alertBuilder.create();
        dialogEdit.setCanceledOnTouchOutside(true);
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
            Attachment attachment = new Attachment(fileName, fileType, "", progressLog.getId());
            UploadAttachment uploadAttachment = new UploadAttachment(file, attachment);
            uploadAttachmentList.add(uploadAttachment);
            Log.d("UploadFile", gson.toJson(uploadAttachment));
            uploadAttachmentAdapter.updateData(uploadAttachmentList);
            Log.d("SizeAdapter", String.valueOf(uploadAttachmentList.size()));
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