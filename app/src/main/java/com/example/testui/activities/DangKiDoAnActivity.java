package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DangKiDoAnActivity extends AppCompatActivity {

    ActivityDangKiDoAnBinding binding;
    List<Supervisor> listSupervisor = new ArrayList<>();
    DangKiDoAnViewModel dangKiDoAnViewModel;
    String studentId;
    GVHDAdapter gvhdAdapter;
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
        Intent projectTermIntent = getIntent();
        String strProjectTerm = projectTermIntent.getStringExtra(Constants.KEY_PROJECT_TERM);
        ProjectTerm projectTerm = new Gson().fromJson(strProjectTerm, ProjectTerm.class);
        binding.btnDangKyGVHD.setOnClickListener(gvhd -> {
            Intent intent = new Intent(this, DangKiGVHDActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.btnBack.setOnClickListener(back -> {
            finish();
        });

        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        gvhdAdapter = new GVHDAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvSupervisor.setAdapter(gvhdAdapter);

        studentId = dangKiDoAnViewModel.getStudentId();
        dangKiDoAnViewModel.getAssignmentByStudentIdAndTermId(studentId, projectTerm.getId());

        dangKiDoAnViewModel.getAssignmentMutableLiveData().observe(this, assignment-> {
            Log.d("Assignment", new Gson().toJson(assignment.getAssignment_supervisors()));
            for (AssignmentSupervisor assignmentSupervisor : assignment.getAssignment_supervisors()) {
                listSupervisor.add(assignmentSupervisor.getSupervisor());
            }
            gvhdAdapter.updateData(listSupervisor);
            Log.d("Assignment", new Gson().toJson(listSupervisor));
        });
    }

    void init() {
        dangKiDoAnViewModel = new DangKiDoAnViewModelFactory(this).create(DangKiDoAnViewModel.class);
    }
}