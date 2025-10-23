package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.DangKiDoAnViewModel;
import com.example.testui.ViewModelFactory.DangKiDoAnViewModelFactory;
import com.example.testui.adapter.GVHDAdapter;
import com.example.testui.databinding.ActivityDangKiDoAnBinding;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Faculties;
import com.example.testui.model.Marjor;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.FacultiesFormatter;
import com.example.testui.untilities.formatter.MarjorFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DangKiDoAnActivity extends AppCompatActivity {

    ActivityDangKiDoAnBinding binding;
    List<Supervisor> listSupervisor = new ArrayList<>();
    DangKiDoAnViewModel dangKiDoAnViewModel;
    String studentId;
    GVHDAdapter gvhdAdapter;
    Assignment assignmentResult;
    String strProjectTerm = "";
    Intent projectTermIntent;
    ProjectTerm projectTerm;
    Gson gson;
    Student student;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDangKiDoAnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        setupRecyclerView();
        fetchData();
        loadInfStudent();
    }

    void init() {
        gson = new Gson();
        dangKiDoAnViewModel = new DangKiDoAnViewModelFactory(this).create(DangKiDoAnViewModel.class);
        projectTermIntent = getIntent();
        strProjectTerm = projectTermIntent.getStringExtra(Constants.KEY_PROJECT_TERM);
        projectTerm = ProjectTermFormatter.format(gson.fromJson(strProjectTerm, ProjectTerm.class));
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> finish());

        binding.btnDangKyGVHD.setOnClickListener(gvhd -> {
            Intent intent = new Intent(this, DangKiGVHDActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.btnDangKyDeTai.setOnClickListener(dkdt -> {
            Intent intent = new Intent(this, DangKiDetaiActivity.class);
            intent.putExtra(Constants.KEY_ASSIGNMENT, new Gson().toJson(assignmentResult));
            startActivity(intent);
        });
    }

    void setupRecyclerView() {
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        gvhdAdapter = new GVHDAdapter(this, new ArrayList<>(), position -> {

        });
        binding.rvSupervisor.setAdapter(gvhdAdapter);
    }

    void fetchData() {
        studentId = dangKiDoAnViewModel.getStudentId();
        dangKiDoAnViewModel.getAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());

        dangKiDoAnViewModel.getAssignmentMutableLiveData().observe(this, assignment-> {
            assignmentResult = AssignmentFormatter.format(assignment);
            Project project = ProjectFormatter.format(assignmentResult.getProject());
            binding.tvTenDeTai.setText(project.getName());
            binding.tvDescription.setText(project.getDescription());
            if (assignment.getAssignment_supervisors().isEmpty()) {
                binding.tvEmptySupervisor.setVisibility(View.VISIBLE);
            } else {
                Log.d("Assignment", new Gson().toJson(assignment.getAssignment_supervisors()));
                for (AssignmentSupervisor assignmentSupervisor : assignment.getAssignment_supervisors()) {
                    listSupervisor.add(assignmentSupervisor.getSupervisor());
                }
                gvhdAdapter.updateData(listSupervisor);
                Log.d("Assignment", new Gson().toJson(listSupervisor));
            }
        });
    }

    void loadInfStudent() {
        dangKiDoAnViewModel.loadInfStudent();
        dangKiDoAnViewModel.getStudentById().observe(this, result -> {
            student = StudentFormatter.format(result);
            binding.tvMaSinhVien.setText(student.getStudent_code());
            user = UserFormatter.format(student.getUser());
            binding.tvTenSinhVien.setText(user.getFullname());
            binding.tvLop.setText(student.getClass_code());
            Marjor marjor = MarjorFormatter.format(student.getMarjor());
            Faculties faculties = FacultiesFormatter.format(marjor.getFaculties());
            binding.tvKhoa.setText(faculties.getName());
            binding.tvSoDienThoai.setText(user.getPhone());
            binding.tvEmail.setText(user.getEmail());
        });
    }
}