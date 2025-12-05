package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.testui.Supabase.UploadManage;
import com.example.testui.ViewModel.TraCuuBaoVeViewModel;
import com.example.testui.ViewModelFactory.TraCuuBaoVeViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.adapter.CouncilMemberScoreAdapter;
import com.example.testui.adapter.CouncilsMemberAdapter;
import com.example.testui.adapter.UploadAttachmentAdapter;
import com.example.testui.databinding.ActivityTraCuuBaoVeBinding;
import com.example.testui.databinding.DialogNopBaoCaoBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Assignment;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilProjectDefence;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Department;
import com.example.testui.model.Project;
import com.example.testui.model.ReportFile;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.model.UploadFile;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.CouncilFormatter;
import com.example.testui.untilities.formatter.CouncilProjectFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.DepartmentFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TraCuuBaoVeActivity extends AppCompatActivity {
    ActivityTraCuuBaoVeBinding binding;
    Gson gson;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    Assignment assignment;
    String strAssignment;
    Intent intent;
    DialogNopBaoCaoBinding dialogNopBaoCaoBinding;
    CouncilMemberScoreAdapter councilMemberScoreAdapter;
    CouncilsMemberAdapter councilsMemberAdapter;
    BaseGVHDAdapter baseGVHDAdapter;
    TraCuuBaoVeViewModel traCuuBaoVeViewModel;
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    Context context;
    Uri fileUri;
    ArrayList<UploadFile> listUploadFile;
    UploadManage uploadManage;
    List<ReportFile> listReportFile = new ArrayList<>();
    UploadAttachmentAdapter uploadAttachmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuBaoVeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        observerData();
        createDialog();
        setupRecyclerView();
        loadData();
        setupClick();
    }

    void init() {
        gson = new Gson();
        context = this;
        intent = getIntent();
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignment, Assignment.class));
        traCuuBaoVeViewModel = new TraCuuBaoVeViewModelFactory(this).create(TraCuuBaoVeViewModel.class);
        listUploadFile = new ArrayList<>();
        uploadManage = new UploadManage(this);
    }

    void setupRecyclerView() {
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, new ArrayList<>(), position -> {

        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);

        binding.rvCouncilMemberScore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        councilMemberScoreAdapter = new CouncilMemberScoreAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvCouncilMemberScore.setAdapter(councilMemberScoreAdapter);

        binding.rvCouncilMember.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        councilsMemberAdapter = new CouncilsMemberAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvCouncilMember.setAdapter(councilsMemberAdapter);
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        Project project = ProjectFormatter.format(assignment.getProject());
        binding.tvProjectName.setText(project.getName());
        binding.txtProjectDescription.setText(project.getDescription());
        CouncilProject councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());
        Council council = CouncilFormatter.format(councilProject.getCouncil());
        binding.txtCouncilName.setText(council.getName());
        binding.txtCouncilId.setText(council.getId());
        Department department = DepartmentFormatter.format(council.getDepartment());
        binding.txtMajor.setText(department.getName());
        binding.txtCouncilDescription.setText(council.getDescription());
        binding.tvBvRoom.setText(council.getAddress());
        binding.tvBvTime.setText(DateFormatter.formatDate(council.getDate()));
        traCuuBaoVeViewModel.councilDisplay.observe(this, model -> {
            Council resultCouncil = CouncilFormatter.format(model);
            binding.txtCouncilName.setText(resultCouncil.getName());
            binding.txtCouncilId.setText(resultCouncil.getId());
            Department departmentResult = DepartmentFormatter.format(resultCouncil.getDepartment());
            binding.txtMajor.setText(departmentResult.getName());
            binding.txtCouncilDescription.setText(resultCouncil.getDescription());
            binding.tvBvRoom.setText(resultCouncil.getAddress());
            binding.tvBvTime.setText(resultCouncil.getDate());
        });

        listReportFile = project.getReport_files();

        List<Supervisor> listBaseSupervisor = traCuuBaoVeViewModel.convertListBaseSupervisor(assignment.getAssignment_supervisors());
        baseGVHDAdapter.updateData(listBaseSupervisor);

        List<CouncilProjectDefence> listCouncilProjectDefence = councilProject.getCouncil_project_defences() != null ?
                councilProject.getCouncil_project_defences() : new ArrayList<>();
        if (listCouncilProjectDefence.isEmpty()) {
            binding.tvEmptyCouncilProjectDefences.setVisibility(View.VISIBLE);
        } else {
            councilMemberScoreAdapter.updateData(listCouncilProjectDefence);
        }

        List<CouncilsMember> listCouncilMember = council.getCouncil_members();
        int count = listCouncilMember != null ? listCouncilMember.size() : 0;
        binding.tvMemberCount.setText(count + " thành viên");
        if (count == 0) {
            binding.tvEmptyCouncilMember.setVisibility(View.VISIBLE);
        } else {
            councilsMemberAdapter.updateData(listCouncilMember);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void createDialog() {
        builder = new AlertDialog.Builder(TraCuuBaoVeActivity.this, R.style.FullScreenDialogTheme);
        dialogNopBaoCaoBinding = DialogNopBaoCaoBinding.inflate(getLayoutInflater());
        builder.setView(dialogNopBaoCaoBinding.getRoot());
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        boolean isUpload = traCuuBaoVeViewModel.isUploadReport(assignment);
        Status status = traCuuBaoVeViewModel.loadStatus(isUpload);
        dialogNopBaoCaoBinding.tvTrangThaiBaoCao.setText(status.getStrStatus());
        dialogNopBaoCaoBinding.tvTrangThaiBaoCao.setBackground(getDrawable(status.getBackgroundColor()));

        dialogNopBaoCaoBinding.rvReport.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
        dialogNopBaoCaoBinding.rvReport.setAdapter(uploadAttachmentAdapter);

        dialogNopBaoCaoBinding.llDropZone.setOnClickListener(v -> {
            openFilePicker();
        });
    }

    void setupClick() {
        binding.btnSubmitReportCard.setOnClickListener(v -> {
            dialog.show();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.9)
                );
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        dialogNopBaoCaoBinding.btnSubmit.setOnClickListener(v -> {
            if (!listUploadFile.isEmpty()) {
                uploadManage.uploadDocuments(listUploadFile, new UploadManage.UploadCallback() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onUploadSuccess(UploadFile uploadFile) {
                        Log.d("Response_upload", new Gson().toJson(uploadFile));
                        Log.d("ReportFile", gson.toJson(uploadFile.getReportFile()));
                        ReportFile reportFile = uploadFile.getReportFile();
                        traCuuBaoVeViewModel.uploadReportFile(reportFile);
                        listUploadFile.remove(0);
                        uploadAttachmentAdapter.updateData(listUploadFile);
//                        traCuuBaoVeViewModel.getListReportFileByProjectId(assignment.getProject_id(), Constants.KEY_TYPE_REPORT_REPORT_COUNCIL);
                    }

                    @Override
                    public void onUploadError(UploadFile uploadFile, Throwable t) {
                        Log.e("Response_Error", t.toString());
                    }
                });
            } else {
                Toast.makeText(context, "Vui lòng chọn file", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialogNopBaoCaoBinding.btnCancel.setOnClickListener(v -> {
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
            String fileName = traCuuBaoVeViewModel.safeFileName(traCuuBaoVeViewModel.getFileName(fileUri));
            String fileType = context.getContentResolver().getType(fileUri);
            File file = null;
            try {
                file = traCuuBaoVeViewModel.getFileFromUri(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ReportFile reportFile = new ReportFile(fileName, fileType, "", assignment.getProject_id(), Constants.KEY_TYPE_REPORT_REPORT_COUNCIL, Constants.KEY_STATUS_REPORT_SUBMITTED);
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

    void observerData() {
        traCuuBaoVeViewModel.getIsCreateSuccess().observe(this, result -> {
            if (result) {
                Toast.makeText(context, "Upload thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Upload cáo thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}