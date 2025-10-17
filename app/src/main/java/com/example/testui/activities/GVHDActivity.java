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
import com.example.testui.untilities.Constants;
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
    }

    void init() {
        intent = getIntent();
        strSupervisor = intent.getStringExtra(Constants.KEY_SUPERVISOR);
        gson = new Gson();
        supervisor = gson.fromJson(strSupervisor, Supervisor.class);
        gvhdViewModel = new ViewModelProvider(this, new GVHDViewModelFactory(this)).get(GVHDViewModel.class);
        teacherId = supervisor.getTeacher().getId();
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        binding.tvGiangVienName.setText(supervisor.getTeacher().getDegree() + " " + supervisor.getTeacher().getUser().getFullname());
        binding.tvHocVi.setText(supervisor.getTeacher().getDegree());
        binding.tvChucVu.setText("Chức vụ: " + supervisor.getTeacher().getPosition());
        binding.tvEmail.setText("Email: " + supervisor.getTeacher().getUser().getEmail());
        binding.tvPhone.setText("Số điện thoại: " + supervisor.getTeacher().getUser().getPhone());

        binding.tvGiangVienNameMain.setText(supervisor.getTeacher().getDegree() + " " + supervisor.getTeacher().getUser().getFullname());
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
}