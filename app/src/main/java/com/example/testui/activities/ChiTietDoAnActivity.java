package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ChiTietDoAnViewModel;
import com.example.testui.ViewModelFactory.ChiTietDoAnViewModelFactory;
import com.example.testui.adapter.GVHDAdapter;
import com.example.testui.adapter.ProcessLogAdapter;
import com.example.testui.databinding.ActivityChiTietDoAnBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.AcademyYear;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AcademyYearFormatter;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDoAnActivity extends AppCompatActivity {
    ProcessLogAdapter processLogAdapter;
    Assignment assignment;
    ProjectTerm projectTerm;
    ChiTietDoAnViewModel chiTietDoAnViewModel;
    ActivityChiTietDoAnBinding binding;
    List<Supervisor> listSuperVisor;
    GVHDAdapter gvhdAdapter;
    Gson gson;
    String studentId = "", strAssignment = "", strProjectTerm = "";
    Intent intent;
    Project project;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChiTietDoAnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        setupRecyclerSupervisor();
        setupRecyclerProgressLog();
        loadData();
    }

    void init() {
        gson = new Gson();
        chiTietDoAnViewModel = new ViewModelProvider(this, new ChiTietDoAnViewModelFactory(this)).get(ChiTietDoAnViewModel.class);
        intent = getIntent();
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignment, Assignment.class));
        projectTerm = ProjectTermFormatter.format(gson.fromJson(strProjectTerm, ProjectTerm.class));
    }

    void setupClick() {
        binding.btnDetailProgress.setOnClickListener(detailProgress -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, ProgressLogActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_PROJECT_ID, assignment.getProject_id());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chưa có đề tài, vui lòng đăng ký đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnNopDeCuong.setOnClickListener(nopedcuong -> {
            if(project.getId() != null) {
                Intent intent = new Intent(ChiTietDoAnActivity.this, NopDeCuongActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chưa có đề tài, vui lòng đăng ký đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("Chitietdoan", gson.toJson(gson.toJson(project)));

        binding.btnNopBaoCao.setOnClickListener(nopbaocao -> {
            if (project.getId() != null) {
                Intent intent = new Intent(ChiTietDoAnActivity.this, NopBaoCaoActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chưa có đề tài, vui lòng đăng ký đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnTraCuuDiem.setOnClickListener(tracuudiem -> {
            if (project.getId() != null) {
                Intent intent = new Intent(ChiTietDoAnActivity.this, TraCuuDiemActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chưa có đề tài, vui lòng đăng ký đề tài", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setupRecyclerProgressLog() {
        processLogAdapter = new ProcessLogAdapter(this, new ArrayList<>(), position -> {
            Intent intent = new Intent(ChiTietDoAnActivity.this, ProgressLogDetailActivity.class);
            intent.putExtra(Constants.KEY_PROGRESS_LOG, gson.toJson(processLogAdapter.getItem(position)));
            startActivity(intent);
        });

        binding.rvProgressLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvProgressLog.setAdapter(processLogAdapter);
    }

    void setupRecyclerSupervisor() {
        gvhdAdapter = new GVHDAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(ChiTietDoAnActivity.this, GVHDActivity.class);
                intent.putExtra(Constants.KEY_SUPERVISOR, new Gson().toJson(assignment.getAssignment_supervisors().get(position).getSupervisor()));
                startActivity(intent);
            }
        });
        binding.rvGvhd.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvGvhd.setAdapter(gvhdAdapter);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    void loadData() {
        studentId = chiTietDoAnViewModel.getIdStudent();
        chiTietDoAnViewModel.loadAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());
        chiTietDoAnViewModel.getAssignmentByStudentIdAndTermId().observe(this, result -> {
            if (result != null) {
                assignment = AssignmentFormatter.format(result);

                binding.tvMaDeTai.setText(assignment.getId());
                project = ProjectFormatter.format(assignment.getProject());
                binding.tvTenDeTai.setText("Tên đề tài: " + project.getName());
                binding.tvMoTa.setText("Mô tả: " + project.getDescription());
                ProjectTerm getProjectTerm = ProjectTermFormatter.format(assignment.getProject_term());
                AcademyYear academyYear = AcademyYearFormatter.format(projectTerm.getAcademy_year());
                binding.tvThongTinHocKy.setText("Đợt " + getProjectTerm.getStage() + " - " + academyYear.getYear_name());


                String createdAt = assignment.getCreated_at();
                String formattedDate = DateFormatter.formatDate(createdAt);
                binding.tvNgayDangKy.setText(formattedDate);

                Status status = AssignmentFormatter.statusFormat(assignment.getStatus());
                binding.tvTrangThai.setText(status.getStrStatus());
                binding.tvTrangThai.setBackground(getDrawable(status.getBackgroundColor()));

                if (project.getProgress_logs() != null && !project.getProgress_logs().isEmpty()) {
                    processLogAdapter.updateData(project.getProgress_logs());
                } else {
                    binding.tvEmptyProgress.setVisibility(View.VISIBLE);
                }
                Log.d("Supervisor", String.valueOf(assignment.getAssignment_supervisors().size()));
                binding.tvTeacherCount.setText(assignment.getAssignment_supervisors().size() + " GV");

                if (!assignment.getAssignment_supervisors().isEmpty()) {
                    List<Supervisor> convertSupervisor = new ArrayList<>();
                    for (AssignmentSupervisor assignmentSupervisor : assignment.getAssignment_supervisors()) {
                        convertSupervisor.add(assignmentSupervisor.getSupervisor());
                    }
                    gvhdAdapter.updateData(convertSupervisor);
                } else {
                    binding.tvEmptyNotify.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}