package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testui.R;
import com.example.testui.databinding.ActivityGvhdactivityBinding;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

public class GVHDActivity extends AppCompatActivity {

    ActivityGvhdactivityBinding binding;

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

        Intent itent = getIntent();
        String strSupervisor = itent.getStringExtra(Constants.KEY_SUPERVISOR);
        Gson gson = new Gson();
        Supervisor supervisor = gson.fromJson(strSupervisor, Supervisor.class);
        binding.tvGiangVienName.setText(supervisor.getTeacher().getDegree() + " " + supervisor.getTeacher().getUser().getFullname());
        binding.tvHocVi.setText(supervisor.getTeacher().getDegree());
        binding.tvChucVu.setText("Chức vụ: " + supervisor.getTeacher().getPosition());
        binding.tvEmail.setText("Email: " + supervisor.getTeacher().getUser().getEmail());
        binding.tvPhone.setText("Số điện thoại: " + supervisor.getTeacher().getUser().getPhone());
    }
}