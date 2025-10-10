package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ProgressLogViewModel;
import com.example.testui.adapter.ProcessLogAdapter;
import com.example.testui.databinding.ActivityProgressLogBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProgressLog;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ProgressLogActivity extends AppCompatActivity {
    ActivityProgressLogBinding binding;
    ProgressLogViewModel progressLogViewModel;
    ProcessLogAdapter processLogAdapter;
    Gson gson;
    String projectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProgressLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        serialize();
        Intent getIntent = getIntent();
        projectId = getIntent.getStringExtra(Constants.KEY_PROJECT_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressLogActivity.this);
        View view = LayoutInflater.from(ProgressLogActivity.this).inflate(R.layout.add_item_log, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        binding.btnAddEntry.setOnClickListener(addEntry -> {
            dialog.show();
        });

        view.findViewById(R.id.btnSave).setOnClickListener(cancel -> {
            dialog.dismiss();
        });

        processLogAdapter = new ProcessLogAdapter(this, new ArrayList<>(), position -> {
            Intent intent = new Intent(this, ProgressLogDetailActivity.class);
            intent.putExtra(Constants.KEY_PROGRESS_LOG, gson.toJson(processLogAdapter.getItem(position)));
            startActivity(intent);
        });
        binding.rvProgressLogs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvProgressLogs.setAdapter(processLogAdapter);

        loadProgressLog("49", this);
    }

    void serialize() {
        progressLogViewModel = new ProgressLogViewModel();
        gson = new Gson();
    }

    @SuppressLint("SetTextI18n")
    void loadProgressLog(String projectId, Context context) {
        progressLogViewModel.getProgressLogByProjectId(projectId);
        progressLogViewModel.getProgressLogByProjectId().observe(this, result -> {
            if (result != null && !result.isEmpty()) {
                binding.tvLogCount.setText(Integer.toString(result.size()));
                processLogAdapter.updateData(result); // thêm hàm update trong adapter
            }
        });
    }
}