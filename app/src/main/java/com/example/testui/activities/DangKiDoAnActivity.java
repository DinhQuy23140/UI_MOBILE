package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testui.R;
import com.example.testui.databinding.ActivityDangKiDoAnBinding;
import com.example.testui.model.ProjectTerm;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

public class DangKiDoAnActivity extends AppCompatActivity {
    ActivityDangKiDoAnBinding binding;
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
    }
}