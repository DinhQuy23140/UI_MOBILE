package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.GVHDViewModel;
import com.example.testui.ViewModelFactory.GVHDViewModelFactory;
import com.example.testui.adapter.AssignmentAdapter;
import com.example.testui.databinding.ActivityGvhdactivityBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.SupervisorFormatter;
import com.example.testui.untilities.formatter.TeacherFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GVHDActivity extends AppCompatActivity {

    ActivityGvhdactivityBinding binding;
    Intent intent;
    String strSupervisor;
    Gson gson;
    Supervisor supervisor;
    AssignmentAdapter assignmentAdapter;
    GVHDViewModel gvhdViewModel;
    List<Assignment> listAssignment;
    String teacherId;
    Teacher teacher;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGvhdactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        loadData();
        setupRecycler();
        loadDataRecyclerView();
        setupClick();
    }

    void init() {
        intent = getIntent();
        strSupervisor = intent.getStringExtra(Constants.KEY_SUPERVISOR);
        gson = new Gson();
        supervisor = SupervisorFormatter.format(gson.fromJson(strSupervisor, Supervisor.class));
        gvhdViewModel = new ViewModelProvider(this, new GVHDViewModelFactory(this)).get(GVHDViewModel.class);
        teacher = TeacherFormatter.format(supervisor.getTeacher());
        teacherId = teacher.getId();
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        User user = UserFormatter.format(teacher.getUser());
        binding.tvGiangVienName.setText(teacher + " " + user.getFullname());
        binding.tvHocVi.setText(teacher.getDegree());
        binding.tvChucVu.setText("Chức vụ: " + teacher.getPosition());
        binding.tvEmail.setText("Email: " + user.getEmail());
        binding.tvPhone.setText("Số điện thoại: " + user.getPhone());

        binding.tvGiangVienNameMain.setText(teacher.getDegree() + " " + user.getFullname());
    }

    void setupRecycler() {
        binding.rvAssignments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        assignmentAdapter = new AssignmentAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvAssignments.setAdapter(assignmentAdapter);
    }

    @SuppressLint("SetTextI18n")
    void loadDataRecyclerView() {
        Log.d("TeacherId", teacherId);
        gvhdViewModel.loadAssignmentsByTeacherId(teacherId);
        gvhdViewModel.getListAssignment().observe(this, result -> {
            listAssignment = result;
            binding.tvSoLuongSV.setText(listAssignment.size() + " sinh viên");
            assignmentAdapter.updateDate(listAssignment);
        });
    }

    void setupClick() {
        binding.tvViewAllAsignment.setOnClickListener(assignments -> {
            Intent intent = new Intent(this, ViewAllAssignmentActivity.class);
            intent.putExtra(Constants.KEY_SUPERVISOR, strSupervisor);
            startActivity(intent);
        });
    }
}