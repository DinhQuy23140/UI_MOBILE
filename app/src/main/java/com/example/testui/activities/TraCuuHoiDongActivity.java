package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testui.R;
import com.example.testui.databinding.ActivityTraCuuHoiDongBinding;
import com.example.testui.model.Assignment;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

public class TraCuuHoiDongActivity extends AppCompatActivity {
    Intent intent;
    String strAssignment = "";
    Assignment assignment;
    Gson gson;
    ActivityTraCuuHoiDongBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuHoiDongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    void init() {
        intent = getIntent();
        gson = new Gson();
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = gson.fromJson(strAssignment, Assignment.class);
        Log.d("Assignment_id", assignment.getId());
    }
}