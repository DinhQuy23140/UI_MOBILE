package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.DangKiDeTaiViewModel;
import com.example.testui.ViewModelFactory.DangKiDeTaiViewModelFactory;
import com.example.testui.adapter.ProposedTopicAdapter;
import com.example.testui.databinding.ActivityDangKiDetaiBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.AcademyYear;
import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Status;
import com.example.testui.model.Student;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AcademyYearFormatter;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterTopicActivity extends AppCompatActivity {

    ActivityDangKiDetaiBinding binding;
    DangKiDeTaiViewModel dangKiDeTaiViewModel;
    Assignment assignment;
    String strAssignment;
    Intent intent;
    Student student;
    ProposedTopicAdapter proposedTopicAdapter;

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
        observerData();
        setupClick();
        loadData();
        message();
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
            dangKiDeTaiViewModel.createProject(assignment.getId(), projectBody);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void loadData() {
        ProjectTerm projectTerm = ProjectTermFormatter.format(assignment.getProject_term());
        binding.tvDotDoAn.setText(projectTerm.getStage());
        binding.tvStartDate.setText(projectTerm.getStart_date());
        binding.tvEndDate.setText(projectTerm.getEnd_date());
        AcademyYear academyYear = AcademyYearFormatter.format(projectTerm.getAcademy_year());
        binding.tvNamHoc.setText(academyYear.getYear_name());
        binding.tvMoTa.setText(projectTerm.getDescription());
        Status status = ProjectTermFormatter.getStatus(projectTerm);
        binding.tvTrangThai.setText(status.getStrStatus());
        binding.tvTrangThai.setBackground(getDrawable(status.getBackgroundColor()));
        Project project = ProjectFormatter.format(assignment.getProject());
        binding.edtTenDeTai.setText(project.getName());
        binding.edtDescription.setText(project.getDescription());

//        dangKiDeTaiViewModel.getResponseCreateProject().observe(this, result -> {
//            dangKiDeTaiViewModel.updateProjectIdAssignmentByAssIdAndProId(assignment.getId(), result.getId());
//        });
        dangKiDeTaiViewModel.loadProposedTopicByAssignmentId(assignment.getId());

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

        dangKiDeTaiViewModel.getIsSuccessCreate().observe(this, success -> {
            if (success == null) return;
            if (success) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        binding.rvProposedTopic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        proposedTopicAdapter = new ProposedTopicAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvProposedTopic.setAdapter(proposedTopicAdapter);
    }

    void message() {
    }

    void observerData() {
        dangKiDeTaiViewModel.getListProposedTopic().observe(this, result -> {
            if (result == null) return;
            proposedTopicAdapter.updateData(result);
            Log.d("ProposedTopic", new Gson().toJson(result));
        });
    }
}