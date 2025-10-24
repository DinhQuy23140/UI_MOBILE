package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ProgressLogViewModel;
import com.example.testui.ViewModelFactory.ProgressLogViewModelFactory;
import com.example.testui.adapter.ProcessLogAdapter;
import com.example.testui.databinding.ActivityProgressLogBinding;
import com.example.testui.databinding.AddItemLogBinding;
import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProgressLogActivity extends AppCompatActivity {
    ActivityProgressLogBinding binding;
    ProgressLogViewModel progressLogViewModel;
    ProcessLogAdapter processLogAdapter;
    Gson gson;
    Intent getIntent;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    String projectId = "", strAssignment = "";
    String startDate = "", endDate = "";
    Assignment assignment;
    AddItemLogBinding addItemLogBinding;
    MaterialDatePicker<Long> startDatePicker, endDatePicker;
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
        datePicket();
        setupClick();
        loadData();
        setupRecyclerView();
        loadProgressLog();
    }

    void serialize() {
        progressLogViewModel = new ViewModelProvider(this, new ProgressLogViewModelFactory(this)).get(ProgressLogViewModel.class);
        gson = new Gson();
        getIntent = getIntent();
        projectId = getIntent.getStringExtra(Constants.KEY_PROJECT_ID);
        strAssignment = getIntent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignment, Assignment.class));
    }

    void createDialog() {
        builder = new AlertDialog.Builder(ProgressLogActivity.this, R.style.FullScreenDialogTheme);
        addItemLogBinding = AddItemLogBinding.inflate(getLayoutInflater());
        builder.setView(addItemLogBinding.getRoot());
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
    }

    void setupClick() {
        binding.btnAddEntry.setOnClickListener(addEntry -> {
            dialog.show();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.9)
                );
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

        });

        addItemLogBinding.btnSave.setOnClickListener(save -> {
            Map<String, String> progressLogBody = new HashMap<>();
            progressLogBody.put("title", addItemLogBinding.etTaskName.getText().toString());
            progressLogBody.put("description", addItemLogBinding.etDescription.getText().toString());
            progressLogBody.put("start_date_time", startDate);
            progressLogBody.put("end_date_time", endDate);
            progressLogBody.put("student_status", "in_progress");
            progressLogBody.put("instructor_status", "pending");
            progressLogBody.put("project_id", projectId);

            progressLogViewModel.createProgressLog(progressLogBody);
            dialog.dismiss();
        });

        addItemLogBinding.lnStartDate.setOnClickListener(startDate -> {
            startDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        addItemLogBinding.lnEndDate.setOnClickListener(endDate -> {
            endDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        startDatePicker.addOnPositiveButtonClickListener(selection -> {
            Date date = new Date(selection);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            startDate = sdf.format(date);
            addItemLogBinding.etStartDate.setText(startDate);
        });

        endDatePicker.addOnPositiveButtonClickListener(selection -> {
            Date date = new Date(selection);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            endDate = sdf.format(date);
            addItemLogBinding.etEndDate.setText(endDate);
        });

    }

    @SuppressLint("SetTextI18n")
    void loadProgressLog() {
        if ( projectId != null && !projectId.isEmpty()) {
            progressLogViewModel.getProgressLogByProjectId(projectId);
            progressLogViewModel.getProgressLogByProjectId().observe(this, result -> {
                if (result != null && !result.isEmpty()) {
                    Log.d("Progress", gson.toJson(result));
                    binding.tvLogCount.setText(Integer.toString(result.size()));
                    processLogAdapter.updateData(result); // thêm hàm update trong adapter
                    binding.rvProgressLogs.setVisibility(View.VISIBLE);
                    binding.emptyState.setVisibility(View.GONE);
                } else {
                    binding.rvProgressLogs.setVisibility(View.GONE);
                    binding.emptyState.setVisibility(View.VISIBLE);
                }
                int countProcess = progressLogViewModel.countProgressProcess(result);
                int countComplete = progressLogViewModel.countProgressComplete(result);
                binding.tvInProgressCount.setText(Integer.toString(countProcess));
                binding.tvCompletedCount.setText(Integer.toString(countComplete));
            });
        } else {
            binding.emptyState.setVisibility(View.VISIBLE);
            binding.rvProgressLogs.setVisibility(View.GONE);
        }
    }

    void setupRecyclerView() {
        processLogAdapter = new ProcessLogAdapter(this, new ArrayList<>(), position -> {
            Intent intent = new Intent(this, ProgressLogDetailActivity.class);
            intent.putExtra(Constants.KEY_PROGRESS_LOG, gson.toJson(processLogAdapter.getItem(position)));
            intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
            startActivity(intent);
        });
        binding.rvProgressLogs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvProgressLogs.setAdapter(processLogAdapter);
    }

    void loadData() {
        Project project = ProjectFormatter.format(assignment.getProject());
        binding.tvHint.setText("Đề tài: " + project.getName());
        progressLogViewModel.getProgressLogMutableLiveData().observe(this, result -> {
            Log.d("Result create", gson.toJson(result));
        });
    }

    void datePicket() {
        startDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("").build();
        endDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("").build();
    }
}