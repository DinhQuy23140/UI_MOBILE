package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ViewAllAssignmentViewModel;
import com.example.testui.ViewModelFactory.ViewAllAssignmentViewModelFactory;
import com.example.testui.adapter.AssignmentAdapter;
import com.example.testui.databinding.ActivityViewAllAssignmentBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewAllAssignmentActivity extends AppCompatActivity {
    Intent intent;
    String strSupervisor = "";
    Supervisor supervisor;
    String teacherId = "";
    Gson gson;
    AssignmentAdapter assignmentAdapter;
    ActivityViewAllAssignmentBinding binding;
    ViewAllAssignmentViewModel viewAllAssignmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityViewAllAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupRecyclerView();
        loadData();
    }

    void init(){
        viewAllAssignmentViewModel = new ViewModelProvider(this, new ViewAllAssignmentViewModelFactory(this)).get(ViewAllAssignmentViewModel.class);
        gson = new Gson();
        intent = getIntent();
        strSupervisor = intent.getStringExtra(Constants.KEY_SUPERVISOR);
        supervisor = gson.fromJson(strSupervisor, Supervisor.class);
        teacherId = supervisor.getTeacher().getId();
    }

    void setupRecyclerView() {
        binding.rvSinhVienList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        assignmentAdapter = new AssignmentAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvSinhVienList.setAdapter(assignmentAdapter);
    }

    void loadData() {
        binding.tvGiangVienName.setText(supervisor.getTeacher().getDegree() + "." + supervisor.getTeacher().getUser().getFullname());
        binding.tvChucVu.setText("Chức vụ: " + supervisor.getTeacher().getPosition());
        binding.tvGiangVienNameMain.setText(supervisor.getTeacher().getDegree() + "." + supervisor.getTeacher().getUser().getFullname());
        viewAllAssignmentViewModel.loadAssignments(teacherId);
        viewAllAssignmentViewModel.getListAssignment().observe(this, result ->{
            binding.tvSoLuongSinhVien.setText(result.size() + " sinh viên");
            assignmentAdapter.updateDate(result);
        });
    }
}