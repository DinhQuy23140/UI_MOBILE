package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.testui.model.ProjectTerm;
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
    Intent getIntent;
    AlertDialog dialog;
    View view;
    AlertDialog.Builder builder;
    ProjectTerm projectTerm;
    String strProjectTerm = "", projectId = "";
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
        createDialog();
        setupClick();

        processLogAdapter = new ProcessLogAdapter(this, new ArrayList<>(), position -> {
            Intent intent = new Intent(this, ProgressLogDetailActivity.class);
            intent.putExtra(Constants.KEY_PROGRESS_LOG, gson.toJson(processLogAdapter.getItem(position)));
            startActivity(intent);
        });
        binding.rvProgressLogs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvProgressLogs.setAdapter(processLogAdapter);

        loadProgressLog(projectId, this);
    }

    void serialize() {
        progressLogViewModel = new ProgressLogViewModel();
        gson = new Gson();
        getIntent = getIntent();
        strProjectTerm = getIntent.getStringExtra(Constants.KEY_PROJECT_TERM);
        projectTerm = gson.fromJson(strProjectTerm, ProjectTerm.class);
        projectId = getIntent.getStringExtra(Constants.KEY_PROJECT_ID);
    }

    void createDialog() {
        builder = new AlertDialog.Builder(ProgressLogActivity.this, R.style.FullScreenDialogTheme);
        // Inflate layout
        view = LayoutInflater.from(this).inflate(R.layout.add_item_log, null);
        builder.setView(view);
        // Tạo dialog
        dialog = builder.create();
        // Cho phép tắt khi bấm ngoài (tuỳ chọn)
        dialog.setCanceledOnTouchOutside(true);
    }

    void setupClick() {
        binding.btnAddEntry.setOnClickListener(addEntry -> {
            dialog.show();
        });

        view.findViewById(R.id.btnSave).setOnClickListener(cancel -> {
            dialog.dismiss();
        });
    }

    @SuppressLint("SetTextI18n")
    void loadProgressLog(String projectId, Context context) {
        progressLogViewModel.getProgressLogByProjectId(projectId);
        progressLogViewModel.getProgressLogByProjectId().observe(this, result -> {
            if (result != null && !result.isEmpty()) {
                Log.d("Progress", gson.toJson(result));
                binding.tvLogCount.setText(Integer.toString(result.size()));
                processLogAdapter.updateData(result); // thêm hàm update trong adapter
            }
            int countProcess = progressLogViewModel.countProgressProcess(result);
            int countComplete = progressLogViewModel.countProgressComplete(result);
            binding.tvInProgressCount.setText(Integer.toString(countProcess));
            binding.tvCompletedCount.setText(Integer.toString(countComplete));
        });
    }
}