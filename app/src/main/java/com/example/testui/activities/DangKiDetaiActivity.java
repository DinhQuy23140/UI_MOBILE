package com.example.testui.activities;

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
import com.example.testui.model.Assignment;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DangKiDetaiActivity extends AppCompatActivity {

    ActivityDangKiDetaiBinding binding;
    DangKiDeTaiViewModel dangKiDeTaiViewModel;
    Assignment assignment;

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
        Intent intent = getIntent();
        String strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = new Gson().fromJson(strAssignment, Assignment.class);

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

        dangKiDeTaiViewModel.getResponseCreateProject().observe(this, result -> {
            dangKiDeTaiViewModel.updateProjectIdAssignmentByAssIdAndProId(assignment.getId(), result.getId());
        });
    }

    void init() {
        dangKiDeTaiViewModel = new ViewModelProvider(this, new DangKiDeTaiViewModelFactory(this)).get(DangKiDeTaiViewModel.class);
    }
}