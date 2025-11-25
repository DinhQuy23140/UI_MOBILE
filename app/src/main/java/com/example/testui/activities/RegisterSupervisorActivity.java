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

import com.example.testui.R;
import com.example.testui.ViewModel.DangKiGVHDViewModel;
import com.example.testui.ViewModelFactory.DangKiGVHDViewModelFactory;
import com.example.testui.adapter.SupervisorAutoAdapter;
import com.example.testui.databinding.ActivityDangKiGvhdactivityBinding;
import com.example.testui.model.AcademyYear;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AcademyYearFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.TeacherFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.List;

public class RegisterSupervisorActivity extends AppCompatActivity {
    ActivityDangKiGvhdactivityBinding binding;
    SupervisorAutoAdapter supervisorAutoAdapter;
    List<Supervisor> supervisors;
    Supervisor supervisor;
    String assignmentID, role = "main";
    DangKiGVHDViewModel dangKiGVHDViewModel;
    ProjectTerm projectTerm;
    String studentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDangKiGvhdactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        loadData();
        setupClick();

    }

    void init() {
        dangKiGVHDViewModel = new DangKiGVHDViewModelFactory(this).create(DangKiGVHDViewModel.class);
        Intent intent = getIntent();
        String strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        projectTerm = ProjectTermFormatter.format(new Gson().fromJson(strProjectTerm, ProjectTerm.class));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void loadData() {
        binding.tvDotDoAn.setText(projectTerm.getStage());
        binding.tvStartDate.setText(projectTerm.getStart_date());
        binding.tvEndDate.setText(projectTerm.getEnd_date());
        AcademyYear academyYear = AcademyYearFormatter.format(projectTerm.getAcademy_year());
        binding.tvNamHoc.setText(academyYear.getYear_name());
        binding.tvMoTa.setText(projectTerm.getDescription());
        Status status = ProjectTermFormatter.getStatus(projectTerm);
        binding.tvTrangThai.setText(status.getStrStatus());
        binding.tvTrangThai.setBackground(getDrawable(status.getBackgroundColor()));


        dangKiGVHDViewModel.getAllSupervisorByProjectTermId(projectTerm.getId());
        dangKiGVHDViewModel.getListSupervisor().observe(this, listSupervisor -> {
            supervisorAutoAdapter = new SupervisorAutoAdapter(this, listSupervisor);
            Log.d("SetAdapter", new Gson().toJson(listSupervisor));
            supervisors = listSupervisor;
            binding.autoCompleteGiangVien.setAdapter(supervisorAutoAdapter);
            binding.autoCompleteGiangVien.setThreshold(1);
        });

        studentId = dangKiGVHDViewModel.getStudentId();
        dangKiGVHDViewModel.getAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());
        dangKiGVHDViewModel.getAssignmentMutableLiveData().observe(this, assignment -> {
            if (assignment != null) {
                Log.d("Assignment_id", String.valueOf(assignment.getId()));
                assignmentID = assignment.getId();
            }
        });

        dangKiGVHDViewModel.getIsCreateSuccess().observe(this, result -> {
            Log.d("isCreateSuccess", result.toString());
            if (result) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back-> {
            finish();
        });

        binding.btnDangKy.setOnClickListener(dangki -> {
            if (assignmentID != null && supervisor != null) {
                AssignmentSupervisor assignmentSupervisor = new AssignmentSupervisor(assignmentID, supervisor.getId(), role, "pending");
                dangKiGVHDViewModel.createAssignmentSupervisor(assignmentSupervisor);
            } else {
                Toast.makeText(this, "Supervisor null", Toast.LENGTH_SHORT).show();
            }
        });

        binding.autoCompleteGiangVien.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && supervisorAutoAdapter != null) {
                    binding.autoCompleteGiangVien.showDropDown();
                }
            }
        });

        binding.autoCompleteGiangVien.setOnItemClickListener((parent, view, position, id) -> {
            Supervisor selected = (Supervisor) parent.getItemAtPosition(position);
            if (selected != null &&
                    selected.getTeacher() != null &&
                    selected.getTeacher().getUser() != null) {
                supervisor = selected;
                Teacher teacher = TeacherFormatter.format(supervisor.getTeacher());
                User user = UserFormatter.format(teacher.getUser());
                String name = user.getFullname();
                binding.autoCompleteGiangVien.setText(name, false);  // hiển thị text mà không kích hoạt filter lại
                binding.autoCompleteGiangVien.dismissDropDown();     // đóng dropdown thủ công
            }
        });
    }
}