package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.Supabase.UploadManage;
import com.example.testui.ViewModel.TimeLineViewModel;
import com.example.testui.ViewModelFactory.TimeLineViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.adapter.UploadPostponeFileAdapter;
import com.example.testui.databinding.ActivityTimeLineBinding;
import com.example.testui.databinding.DialogPostponeConfirmationBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.AcademyYear;
import com.example.testui.model.Assignment;
import com.example.testui.model.PostponeProjectTerm;
import com.example.testui.model.PostponeProjectTermFile;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.ReportFile;
import com.example.testui.model.StageTimeline;
import com.example.testui.model.Status;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.model.UploadFile;
import com.example.testui.model.UploadPostponeFile;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AcademyYearFormatter;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.PostponeProjectTermFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StageTimelineFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ActivityTimeLineBinding binding;
    AlertDialog.Builder alertDialog;
    Uri fileUri;
    Context context;
    AlertDialog confirmDialog;
    DialogPostponeConfirmationBinding dialogPostponeConfirmationBinding;
    String strProjectTerm = "", postponeProjectTermId = "", basePostponeProjectTermId = "";
    Intent intent;
    TimeLineViewModel timeLineViewModel;
    String studentId = "", projectTermId = "";
    BaseGVHDAdapter baseGVHDAdapter;
    List<Supervisor> listSupervisor;
    Assignment assignment;
    Gson gson;
    List<UploadPostponeFile> listUploadFile;
    Project project;
    UploadManage uploadManage;
    UploadPostponeFileAdapter uploadPostponeFileAdapter;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTimeLineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        observed();
        setUpRecyclerView();
        loadProjectTerm();
        loadAssignment();
        createDialogPostpone();
        setupClick();
    }

    void init() {
        intent = getIntent();
        timeLineViewModel = new ViewModelProvider(this, new TimeLineViewModelFactory(this)).get(TimeLineViewModel.class);
        listSupervisor = new ArrayList<>();
        gson = new Gson();
        context = this;
        listUploadFile = new ArrayList<>();
        uploadManage = new UploadManage(this);
    }

    void loadAssignment() {
        studentId = timeLineViewModel.getStudentId();
        timeLineViewModel.loadAssignmentByStudentIdAndTermId(studentId, projectTermId);
    }

    void setUpRecyclerView() {
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, new ArrayList<>(), position -> {
            Intent intent1 = new Intent(TimeLineActivity.this, SupervisorActivity.class);
            intent1.putExtra(Constants.KEY_SUPERVISOR, new Gson().toJson(listSupervisor.get(position)));
            startActivity(intent1);
        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);

        uploadPostponeFileAdapter = new UploadPostponeFileAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {
                listUploadFile.remove(position);
                uploadPostponeFileAdapter.updateData(listUploadFile);
            }
        });
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "ResourceAsColor"})
    void loadProjectTerm() {
        strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        Log.d("project_term", strProjectTerm);
        ProjectTerm projectTerm = ProjectTermFormatter.format(new Gson().fromJson(strProjectTerm, ProjectTerm.class));
        projectTermId = projectTerm.getId();
        binding.tvProjectName.setText("Đợt đồ án: Đợt " + projectTerm.getStage() + " năm " + projectTerm.getAcademy_year().getYear_name());
        binding.tvStartDate.setText("Bắt đầu: " + DateFormatter.formatDate(projectTerm.getStart_date()));
        binding.tvEndDate.setText("Kết thúc: " + DateFormatter.formatDate(projectTerm.getEnd_date()));
        binding.tvStatus.setText(projectTerm.toString());
        binding.bgStatus.setBackground(getDrawable(projectTerm.getBackgroundColor()));
        binding.tvDescription.setText(projectTerm.getDescription());

        List<StageTimeline> listStage = projectTerm.getStage_timelines();

        // Kiểm tra null và size để tránh IndexOutOfBoundsException
        if (listStage != null && !listStage.isEmpty()) {
            // Tạo danh sách TextView tương ứng
            TextView[] tvDates = {
                    binding.tvDates1, binding.tvDates2, binding.tvDates3,
                    binding.tvDates4, binding.tvDates5, binding.tvDates6,
                    binding.tvDates7, binding.tvDates8
            };

            TextView[] tvStageNumbers = {
                    binding.tvStepNumber1, binding.tvStepNumber2, binding.tvStepNumber3,
                    binding.tvStepNumber4, binding.tvStepNumber5, binding.tvStepNumber6,
                    binding.tvStepNumber7, binding.tvStepNumber8
            };

            TextView[] tvStageStatus = {
                    binding.tvStatus1, binding.tvStatus2, binding.tvStatus3,
                    binding.tvStatus4, binding.tvStatus5, binding.tvStatus6,
                    binding.tvStatus7, binding.tvStatus8
            };

            View[] viewStageRanges = {
                    binding.tvRangeStage1, binding.tvRangeStage2, binding.tvRangeStage3, binding.tvRangeStage4,
                    binding.tvRangeStage5, binding.tvRangeStage6, binding.tvRangeStage7, binding.tvRangeStage8
            };

            // Giới hạn theo số stage và số textview có
            int limit = Math.min(listStage.size(), tvDates.length);

            for (int i = 0; i < limit; i++) {
                StageTimeline stage = StageTimelineFormatter.format(listStage.get(i));

                String start = DateFormatter.formatDate(stage.getStart_date());
                String end = DateFormatter.formatDate(stage.getEnd_date());
                tvDates[i].setText(start + " - " + end);
                tvDates[i].setVisibility(View.VISIBLE);

                Status statusStage = timeLineViewModel.loadStatusStage(stage);
                tvStageStatus[i].setText(statusStage.getStrStatus());
                tvStageStatus[i].setBackground(getDrawable(statusStage.getBackgroundColor()));
                tvStageNumbers[i].setBackground(getDrawable(statusStage.getBackgroundColor()));
                viewStageRanges[i].setBackground(getDrawable(statusStage.getBackgroundColor()));
            }

            // Ẩn những TextView dư
            for (int i = limit; i < tvDates.length; i++) {
                tvDates[i].setVisibility(View.GONE);
            }
        }

    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    void setupClick() {
        binding.btnViewTopicDetail.setOnClickListener(viewTopic -> {
            Intent intent = new Intent(this, ChiTietDoAnActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timeLineDangKi.setOnClickListener(v -> {
            Intent intent = new Intent(this, DangKiDoAnActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timeLineNopDeCuong.setOnClickListener(v -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, NopDeCuongActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timeLineThucHienHP.setOnClickListener(v -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, ProgressLogActivity.class);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                intent.putExtra(Constants.KEY_PROJECT_ID, assignment.getProject_id());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timeLineNopBaoCao.setOnClickListener(v -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, NopBaoCaoActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelineTraCuuHoiDong.setOnClickListener(timeline5 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuHoiDongActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelinePhanBien.setOnClickListener(timeline6 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuPhanBienActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelineTraCuuPhanBien.setOnClickListener(timeline7 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuKetQuaPhanBienActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelineBaoVe.setOnClickListener(timeline8 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuBaoVeActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnViewTopicDetail.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChiTietDoAnActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
            startActivity(intent);
        });


        binding.btnPostpone.setOnClickListener(v -> {
            confirmDialog.show();
            loadDataDialogPostpone();
            if (confirmDialog.getWindow() != null) {
                confirmDialog.getWindow().setLayout(
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.9)
                );
                confirmDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        binding.btnPendingPostpone.setOnClickListener(v -> {
            if (basePostponeProjectTermId != null && !basePostponeProjectTermId.isEmpty()) {
                timeLineViewModel.cancelPostponeProjectTerm(basePostponeProjectTermId);
            } else {
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });

        dialogPostponeConfirmationBinding.btnCancel.setOnClickListener(v -> confirmDialog.dismiss());

        dialogPostponeConfirmationBinding.btnConfirm.setOnClickListener(v -> {
            confirmDialog.dismiss();
            PostponeProjectTerm postponeProjectTerm = new PostponeProjectTerm(projectTermId, assignment.getId(), "pending", dialogPostponeConfirmationBinding.etReason.getText().toString());
            timeLineViewModel.submitPostponeProjectTerm(postponeProjectTerm);
            loadAssignment();
        });

        dialogPostponeConfirmationBinding.llFileSelection.setOnClickListener(v -> {
            openFilePicker();
        });

        binding.btnAllResult.setOnClickListener(v -> {
            Intent intent = new Intent(this, TraCuuDiemActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
            startActivity(intent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            String fileName = timeLineViewModel.safeFileName(timeLineViewModel.getFileName(fileUri));
            String fileType = context.getContentResolver().getType(fileUri);
            File file = null;
            try {
                file = timeLineViewModel.getFileFromUri(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ReportFile reportFile = new ReportFile(fileName, fileType, "", assignment.getProject_id(), Constants.KEY_TYPE_REPORT_REPORT, Constants.KEY_STATUS_REPORT_SUBMITTED);
            UploadFile uploadFile = new UploadFile(file, reportFile);
            PostponeProjectTermFile postponeProjectTermFile = new PostponeProjectTermFile(fileName, "", fileType, postponeProjectTermId);
            UploadPostponeFile uploadPostponeFile = new UploadPostponeFile(file, postponeProjectTermFile);
            listUploadFile.add(uploadPostponeFile);
            Log.d("UploadFile", gson.toJson(uploadFile));
            uploadPostponeFileAdapter.updateData(listUploadFile);
            Log.d("SizeAdapter", String.valueOf(uploadPostponeFileAdapter.getListUpload().size()));
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

    void createDialogPostpone() {
        alertDialog = new AlertDialog.Builder(this, R.style.FullScreenDialogTheme);
        dialogPostponeConfirmationBinding = DialogPostponeConfirmationBinding.inflate(getLayoutInflater());
        alertDialog.setView(dialogPostponeConfirmationBinding.getRoot());
        confirmDialog = alertDialog.create();
        dialogPostponeConfirmationBinding.rvPostponeFile.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        dialogPostponeConfirmationBinding.rvPostponeFile.setAdapter(uploadPostponeFileAdapter);
        confirmDialog.setCanceledOnTouchOutside(true);
    }

    void loadDataDialogPostpone(){
        ProjectTerm projectTerm = ProjectTermFormatter.format(assignment.getProject_term());
        AcademyYear academyYear = AcademyYearFormatter.format(projectTerm.getAcademy_year());
        Student student = StudentFormatter.format(assignment.getStudent());
        User user = UserFormatter.format(student.getUser());
        dialogPostponeConfirmationBinding.tvProjectPeriod.setText(DateFormatter.formatDate(projectTerm.getStart_date()) + " - " + DateFormatter.formatDate(projectTerm.getEnd_date()));
        dialogPostponeConfirmationBinding.tvProjectName.setText("Đợt " + projectTerm.getStage() + " - Năm học " + academyYear.getYear_name());
        dialogPostponeConfirmationBinding.tvStudentName.setText(user.getFullname());
        dialogPostponeConfirmationBinding.tvStudentId.setText(student.getStudent_code());
    }

    void observed() {
        timeLineViewModel.getPostponeProjectTermMutableLiveData().observe(this, postponeProjectTerm -> {
            if (postponeProjectTerm != null) {
                postponeProjectTermId = postponeProjectTerm.getId();
                for (UploadPostponeFile uploadPostponeFile : listUploadFile) {
                    uploadPostponeFile.getPostponeProjectTermFile().setPostpone_project_term_id(postponeProjectTermId);
                }
                uploadManage.uploadPostponeFiles(listUploadFile, new UploadManage.UploadPostponeFileCallBack() {
                    @Override
                    public void onUploadSuccess(UploadPostponeFile uploadPostponeFile) {
                        timeLineViewModel.uploadPostponeFile(uploadPostponeFile.getPostponeProjectTermFile());
                    }

                    @Override
                    public void onUploadError(UploadPostponeFile uploadPostponeFile, Throwable t) {
                        Toast.makeText(context, "Gửi yêu cầu hoãn thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                loadAssignment();
                Toast.makeText(context, "Gửi yêu cầu hoãn thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gửi yêu cầu hoãn thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        timeLineViewModel.getAssignmentByStudentIdAndTermId().observe(this, result -> {
            if (result != null) {
                assignment = AssignmentFormatter.format(result);
                project = ProjectFormatter.format(assignment.getProject());
                Log.d("timeline", new Gson().toJson(result));
                Project project = ProjectFormatter.format(assignment.getProject());
                binding.tvCurrentTopic.setText(project.getName());
                if (result.getAssignment_supervisors().isEmpty()) {
                    binding.rvSupervisor.setVisibility(View.GONE);
                    binding.tvNotifyEmpty.setVisibility(View.VISIBLE);
                } else {
                    listSupervisor = timeLineViewModel.getSupervisor(result.getAssignment_supervisors());
                    baseGVHDAdapter.updateData(listSupervisor);
                    binding.rvSupervisor.setVisibility(View.VISIBLE);
                    binding.tvNotifyEmpty.setVisibility(View.GONE);
                }
                PostponeProjectTerm postponeProjectTerm = PostponeProjectTermFormatter.format(assignment.getPostpone_project_term());
                if (postponeProjectTerm.getStatus() != null) {
                    switch(postponeProjectTerm.getStatus()) {
                        case Constants.STATUS_PENDING:
                            binding.btnPendingPostpone.setVisibility(View.VISIBLE);
                            binding.btnPostpone.setVisibility(View.GONE);
                            binding.btnAcceptPostpone.setVisibility(View.GONE);
                            break;
                        case Constants.STATUS_APPROVED:
                            binding.btnAcceptPostpone.setVisibility(View.VISIBLE);
                            binding.btnPostpone.setVisibility(View.GONE);
                            binding.btnPendingPostpone.setVisibility(View.GONE);
                            break;
                        case Constants.STATUS_REJECTED:
                            binding.btnPostpone.setVisibility(View.VISIBLE);
                            binding.btnAcceptPostpone.setVisibility(View.GONE);
                            binding.btnPendingPostpone.setVisibility(View.GONE);
                            break;
                        default: {
                            binding.btnPostpone.setVisibility(View.VISIBLE);
                            binding.btnPendingPostpone.setVisibility(View.GONE);
                            binding.btnAcceptPostpone.setVisibility(View.GONE);
                        }
                    }
                }
                PostponeProjectTerm postponeProjectTerm1 = assignment.getPostpone_project_term();
                if (postponeProjectTerm1 != null) basePostponeProjectTermId = postponeProjectTerm1.getId();
            }
        });

        timeLineViewModel.getIsCancelPostponeProjectTerm().observe(this, result -> {
            if (result) {
                loadAssignment();
                Toast.makeText(this, "Hủy yêu cầu hoãn thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hủy yêu cầu hoãn thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}