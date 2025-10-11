package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.testui.R;
import com.example.testui.databinding.ActivityTimeLineBinding;
import com.example.testui.model.ProjectTerm;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

public class TimeLineActivity extends AppCompatActivity {
    ActivityTimeLineBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTimeLineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent itent = getIntent();
        String strProjectTerm = itent.getStringExtra(Constants.KEY_PROJECT_TERM);
        Log.d("project_term", strProjectTerm);
        ProjectTerm projectTerm = new Gson().fromJson(strProjectTerm, ProjectTerm.class);
        binding.tvProjectName.setText("Đợt đồ án: Kỳ " + projectTerm.getStage() + " năm " + projectTerm.getAcademy_year().getYear_name());
        binding.tvStartDate.setText("Bắt đầu: " + projectTerm.getStart_date());
        binding.tvEndDate.setText("Kết thúc: " + projectTerm.getEnd_date());
        binding.tvStatus.setText("Trạng thái: " + projectTerm.getStatus());
        binding.tvDescription.setText(projectTerm.getDescription());

        binding.btnViewTopicDetail.setOnClickListener(viewTopic -> {
            Intent intent = new Intent(this, ChiTietDoAnActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timeLineDangKi.setOnClickListener(v -> {
            Intent intent = new Intent(this, DangKiDoAnActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timeLineNopDeCuong.setOnClickListener(v -> {
            Intent intent = new Intent(this, NopDeCuongActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timeLineThucHienHP.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProgressLogActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timeLineNopBaoCao.setOnClickListener(v -> {
            Intent intent = new Intent(this, NopBaoCaoActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timelineTraCuuHoiDong.setOnClickListener(timeline5 -> {
            Intent intent = new Intent(this, TraCuuHoiDongActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timelinePhanBien.setOnClickListener(timeline6 -> {
            Intent intent = new Intent(this, TraCuuDiemActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timelineTraCuuPhanBien.setOnClickListener(timeline7 -> {
            Intent intent = new Intent(this, TraCuuDiemActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        binding.timelineBaoVe.setOnClickListener(timeline8 -> {
            Intent intent = new Intent(this, TraCuuDiemActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });
    }
}