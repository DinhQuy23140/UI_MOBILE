package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.R;
import com.example.testui.ViewModel.DangKiGVHDViewModel;
import com.example.testui.ViewModelFactory.DangKiGVHDViewModelFactory;
import com.example.testui.adapter.SupervisorAutocomplete;
import com.example.testui.databinding.ActivityDangKiGvhdactivityBinding;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.List;

public class DangKiGVHDActivity extends AppCompatActivity {
    ActivityDangKiGvhdactivityBinding binding;
    SupervisorAutocomplete supervisorAutocomplete;
    List<Supervisor> supervisors;
    Supervisor supervisor;
    String assignmentID, role = "main";
    DangKiGVHDViewModel dangKiGVHDViewModel;

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

        Intent intent = getIntent();
        String strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        ProjectTerm projectTerm = new Gson().fromJson(strProjectTerm, ProjectTerm.class);
        binding.btnBack.setOnClickListener(back-> {
            finish();
        });

        binding.btnDangKy.setOnClickListener(doan -> {

        });

        dangKiGVHDViewModel.getAllSupervisorByProjectTermId(projectTerm.getId());
        dangKiGVHDViewModel.getListSupervisor().observe(this, listSupervisor -> {
           supervisorAutocomplete = new SupervisorAutocomplete(this, listSupervisor);
            Log.d("SetAdapter", new Gson().toJson(listSupervisor));
            supervisors = listSupervisor;
           binding.autoCompleteGiangVien.setAdapter(supervisorAutocomplete);
           binding.autoCompleteGiangVien.setThreshold(1);
        });

        binding.autoCompleteGiangVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                supervisor = supervisors.get(position);
            }
        });

        binding.autoCompleteGiangVien.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && supervisorAutocomplete != null) {
                    binding.autoCompleteGiangVien.showDropDown();
                }
            }
        });

        String studentId = dangKiGVHDViewModel.getStudentId();
        dangKiGVHDViewModel.getAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());
        dangKiGVHDViewModel.getAssignmentMutableLiveData().observe(this, assignment -> {
            if (assignment != null) {
                Log.d("Assignment_id", String.valueOf(assignment.getId()));
                assignmentID = assignment.getId();
            }
        });

        binding.btnDangKy.setOnClickListener(dangki -> {
            if (assignmentID != null && supervisor != null) {
                AssignmentSupervisor assignmentSupervisor = new AssignmentSupervisor(assignmentID, supervisor.getId(), role, "pending");
                dangKiGVHDViewModel.createAssignmentSupervisor(assignmentSupervisor);
            }
        });
    }

    void init() {
        dangKiGVHDViewModel = new DangKiGVHDViewModelFactory(this).create(DangKiGVHDViewModel.class);
    }
}