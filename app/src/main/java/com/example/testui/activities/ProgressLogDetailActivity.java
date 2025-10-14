package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.adapter.AttachmentAdapter;
import com.example.testui.databinding.ActivityProgressLogDetailBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProgressLog;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.DateFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ProgressLogDetailActivity extends AppCompatActivity {
    ActivityProgressLogDetailBinding binding;
    AttachmentAdapter adapter;
    @SuppressLint("SetTextI18n")
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
        Intent intent = getIntent();
        String progressLogJson = intent.getStringExtra(Constants.KEY_PROGRESS_LOG);
        ProgressLog progressLog = new Gson().fromJson(progressLogJson, ProgressLog.class);
        binding.tvTitle.setText(progressLog.getTitle());
        binding.tvDescription.setText("Mô tả: " + progressLog.getDescription());
        binding.tvStartTime.setText("Ngày bắt đầu: " + DateFormatter.formatDate(progressLog.getStart_date_time()));
        binding.tvEndTime.setText("Ngày kết thúc: " + DateFormatter.formatDate(progressLog.getEnd_date_time()));
        binding.tvStudentStatus.setText("Trạng thái: " + progressLog.getStudent_status());
        binding.rvAttachments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AttachmentAdapter(this, progressLog.getAttachments(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvAttachments.setAdapter(adapter);
    }
}