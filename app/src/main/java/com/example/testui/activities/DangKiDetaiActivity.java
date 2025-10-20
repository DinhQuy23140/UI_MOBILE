package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.R;
import com.example.testui.ViewModel.DangKiDeTaiViewModel;
import com.example.testui.ViewModelFactory.DangKiDeTaiViewModelFactory;
import com.example.testui.databinding.ActivityDangKiDetaiBinding;
import com.example.testui.model.AcademyYear;
import com.example.testui.model.Assignment;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Status;
import com.example.testui.model.Student;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AcademyYearFormatter;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DangKiDetaiActivity extends AppCompatActivity {

    ActivityDangKiDetaiBinding binding;
    DangKiDeTaiViewModel dangKiDeTaiViewModel;
    Assignment assignment;
    String strAssignment;
    Intent intent;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDangKiDetaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        loadData();
    }

    void init() {
        intent = getIntent();
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(new Gson().fromJson(strAssignment, Assignment.class));
        dangKiDeTaiViewModel = new ViewModelProvider(this, new DangKiDeTaiViewModelFactory(this)).get(DangKiDeTaiViewModel.class);
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });


        binding.btnDangKyDeTai.setOnClickListener(dangki -> {
            String tendetai = binding.edtTenDeTai.getText().toString();
            String mota = binding.edtDescription.getText().toString();
            Map<String, String> projectBody = new HashMap<>();
            projectBody.put("name", tendetai);
            projectBody.put("description", mota);
            dangKiDeTaiViewModel.createProject(projectBody);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void loadData() {
        ProjectTerm projectTerm = assignment.getProject_term();
        binding.tvDotDoAn.setText(projectTerm.getStage());
        binding.tvStartDate.setText(projectTerm.getStart_date());
        binding.tvEndDate.setText(projectTerm.getEnd_date());
        AcademyYear academyYear = AcademyYearFormatter.format(projectTerm.getAcademy_year());
        binding.tvNamHoc.setText(academyYear.getYear_name());
        binding.tvMoTa.setText(projectTerm.getDescription());
        Status status = ProjectTermFormatter.getStatus(projectTerm);
        binding.tvTrangThai.setText(status.getStrStatus());
        binding.tvTrangThai.setBackground(getDrawable(status.getBackgroundColor()));

        dangKiDeTaiViewModel.getResponseCreateProject().observe(this, result -> {
            dangKiDeTaiViewModel.updateProjectIdAssignmentByAssIdAndProId(assignment.getId(), result.getId());
        });

        dangKiDeTaiViewModel.loadStudentByStudentId();
        dangKiDeTaiViewModel.getStudent().observe(this, result -> {
            student = StudentFormatter.format(result);
            User user = UserFormatter.format(student.getUser());
            binding.tvTenSinhVien.setText(user.getFullname());
            binding.tvMaSinhVien.setText(student.getStudent_code());
            binding.tvLop.setText(student.getClass_code());
            binding.tvSoDienThoai.setText(user.getPhone());
            binding.tvEmail.setText(user.getEmail());
        });
    }
}