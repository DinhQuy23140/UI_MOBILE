package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testui.R;
import com.example.testui.databinding.ActivityDangKiGvhdactivityBinding;
import com.example.testui.model.ProjectTerm;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

public class DangKiGVHDActivity extends AppCompatActivity {
    ActivityDangKiGvhdactivityBinding binding;
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
        Intent intent = getIntent();
        String strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        ProjectTerm projectTerm = new Gson().fromJson(strProjectTerm, ProjectTerm.class);
        binding.btnBack.setOnClickListener(back-> {
            finish();
        });

        binding.btnDangKy.setOnClickListener(doan -> {

        });
    }
}