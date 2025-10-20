package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ProgressLogDetailViewModel;
import com.example.testui.adapter.AttachmentAdapter;
import com.example.testui.databinding.ActivityProgressLogDetailBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.Status;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProgressLogFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ProgressLogDetailActivity extends AppCompatActivity {
    ActivityProgressLogDetailBinding binding;
    AttachmentAdapter adapter;
    Intent intent;
    String progressLogJson = "";
    ProgressLog progressLog;
    Gson gson;
    ProgressLogDetailViewModel progressLogDetailViewModel;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProgressLogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        setupRecyclerView();
        loadData();
    }

    void init() {
        intent = getIntent();
        gson = new Gson();
        progressLogJson = intent.getStringExtra(Constants.KEY_PROGRESS_LOG);
        progressLog = ProgressLogFormatter.format(gson.fromJson(progressLogJson, ProgressLog.class));
        progressLogDetailViewModel = new ProgressLogDetailViewModel(this);
    }

    void setupRecyclerView() {
        binding.rvAttachments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AttachmentAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvAttachments.setAdapter(adapter);
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    void loadData() {
        binding.tvTitle.setText(progressLog.getTitle());
        binding.tvDescription.setText("Mô tả: " + progressLog.getDescription());
        binding.tvStartTime.setText("Ngày bắt đầu: " + DateFormatter.formatDate(progressLog.getStart_date_time()));
        binding.tvEndTime.setText("Ngày kết thúc: " + DateFormatter.formatDate(progressLog.getEnd_date_time()));

        Status status = progressLogDetailViewModel.loadStatusProgress(progressLog.getStudent_status());
        binding.tvStudentStatus.setText("Trạng thái: " + status.getStrStatus());
        binding.tvStudentStatus.setBackground(getDrawable(status.getBackgroundColor()));

        Status instructorStatus = progressLogDetailViewModel.loadInstructorStatus(progressLog.getInstructor_status());
        binding.tvInstructorStatus.setText("Trạng thái: " + instructorStatus.getStrStatus());
        binding.tvInstructorStatus.setBackground(getDrawable(instructorStatus.getBackgroundColor()));
        String comment = (progressLog.getInstructor_comment() != null && !progressLog.getInstructor_comment().isEmpty()) ? progressLog.getInstructor_comment() : "Chưa có nhận xét";
        binding.tvInstructorComment.setText(comment);
        if (!progressLog.getAttachments().isEmpty()) {
            binding.tvAttachmentCount.setText(progressLog.getAttachments().size() + " file");
            adapter.updateData(progressLog.getAttachments());
        } else {
            binding.tvEmptyAttachment.setVisibility(View.VISIBLE);
        }
    }
}