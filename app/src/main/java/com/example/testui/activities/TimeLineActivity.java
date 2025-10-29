package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.TimeLineViewModel;
import com.example.testui.ViewModelFactory.TimeLineViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.databinding.ActivityTimeLineBinding;
import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.StageTimeline;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StageTimelineFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {
    ActivityTimeLineBinding binding;
    String strProjectTerm = "";
    Intent intent;
    TimeLineViewModel timeLineViewModel;
    String studentId = "", projectTermId = "";
    BaseGVHDAdapter baseGVHDAdapter;
    List<Supervisor> listSupervisor;
    Assignment assignment;
    Gson gson;
    Project project;
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

        init();
        setUpRecyclerView();
        loadProjectTerm();
        loadAssignment();
        setupClick();
    }

    void init() {
        intent = getIntent();
        timeLineViewModel = new ViewModelProvider(this, new TimeLineViewModelFactory(this)).get(TimeLineViewModel.class);
        listSupervisor = new ArrayList<>();
        gson = new Gson();
    }

    void loadAssignment() {
        studentId = timeLineViewModel.getStudentId();
        timeLineViewModel.loadAssignmentByStudentIdAndTermId(studentId, projectTermId);
        timeLineViewModel.getAssignmentByStudentIdAndTermId().observe(this, result -> {
            if (result != null) {
                assignment = AssignmentFormatter.format(result);
                project = ProjectFormatter.format(assignment.getProject());
                Log.d("timeline", new Gson().toJson(result));
                Project project = ProjectFormatter.format(assignment.getProject());
                binding.tvCurrentTopic.setText(project.getName());
                if (result.getAssignment_supervisors().isEmpty()) {
                    binding.rvSupervisor.setVisibility(View.GONE);
                    binding.tvNotifyEmpty.setVisibility(View.VISIBLE);
                } else {
                    listSupervisor = timeLineViewModel.getSupervisor(result.getAssignment_supervisors());
                    baseGVHDAdapter.updateData(listSupervisor);
                    binding.rvSupervisor.setVisibility(View.VISIBLE);
                    binding.tvNotifyEmpty.setVisibility(View.GONE);
                }
            }
        });
    }

    void setUpRecyclerView() {
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, new ArrayList<>(), position -> {
            Intent intent1 = new Intent(TimeLineActivity.this, GVHDActivity.class);
            intent1.putExtra(Constants.KEY_SUPERVISOR, new Gson().toJson(listSupervisor.get(position)));
            startActivity(intent1);
        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    void loadProjectTerm() {
        strProjectTerm = intent.getStringExtra(Constants.KEY_PROJECT_TERM);
        Log.d("project_term", strProjectTerm);
        ProjectTerm projectTerm = ProjectTermFormatter.format(new Gson().fromJson(strProjectTerm, ProjectTerm.class));
        projectTermId = projectTerm.getId();
        binding.tvProjectName.setText("Đợt đồ án: Đợt " + projectTerm.getStage() + " năm " + projectTerm.getAcademy_year().getYear_name());
        binding.tvStartDate.setText("Bắt đầu: " + DateFormatter.formatDate(projectTerm.getStart_date()));
        binding.tvEndDate.setText("Kết thúc: " + DateFormatter.formatDate(projectTerm.getEnd_date()));
        Status status = timeLineViewModel.loadStatusAssignment(projectTerm.getStatus());
        binding.tvStatus.setText("Trạng thái: " + status.getStrStatus());
        binding.tvStatus.setBackground(getDrawable(status.getBackgroundColor()));
        binding.tvStatus.setTextColor(getColor(status.getTextColor()));
        binding.tvDescription.setText(projectTerm.getDescription());

        List<StageTimeline> listStage = projectTerm.getStage_timelines();

        // Kiểm tra null và size để tránh IndexOutOfBoundsException
        if (listStage != null && !listStage.isEmpty()) {
            // Tạo danh sách TextView tương ứng
            TextView[] tvDates = {
                    binding.tvDates1, binding.tvDates2, binding.tvDates3,
                    binding.tvDates4, binding.tvDates5, binding.tvDates6,
                    binding.tvDates7, binding.tvDates8
            };

            TextView[] tvStageNumbers = {
                    binding.tvStepNumber1, binding.tvStepNumber2, binding.tvStepNumber3,
                    binding.tvStepNumber4, binding.tvStepNumber5, binding.tvStepNumber6,
                    binding.tvStepNumber7, binding.tvStepNumber8
            };

            TextView[] tvStageStatus = {
                    binding.tvStatus1, binding.tvStatus2, binding.tvStatus3,
                    binding.tvStatus4, binding.tvStatus5, binding.tvStatus6,
                    binding.tvStatus7, binding.tvStatus8
            };

            View[] viewStageRanges = {
                    binding.tvRangeStage1, binding.tvRangeStage2, binding.tvRangeStage3, binding.tvRangeStage4,
                    binding.tvRangeStage5, binding.tvRangeStage6, binding.tvRangeStage7, binding.tvRangeStage8
            };

            // Giới hạn theo số stage và số textview có
            int limit = Math.min(listStage.size(), tvDates.length);

            for (int i = 0; i < limit; i++) {
                StageTimeline stage = StageTimelineFormatter.format(listStage.get(i));

                String start = DateFormatter.formatDate(stage.getStart_date());
                String end = DateFormatter.formatDate(stage.getEnd_date());
                tvDates[i].setText(start + " - " + end);
                tvDates[i].setVisibility(View.VISIBLE);

                Status statusStage = timeLineViewModel.loadStatusStage(stage);
                tvStageStatus[i].setText(statusStage.getStrStatus());
                tvStageStatus[i].setBackground(getDrawable(statusStage.getBackgroundColor()));
                tvStageNumbers[i].setBackground(getDrawable(statusStage.getBackgroundColor()));
                viewStageRanges[i].setBackground(getDrawable(statusStage.getBackgroundColor()));
            }

            // Ẩn những TextView dư
            for (int i = limit; i < tvDates.length; i++) {
                tvDates[i].setVisibility(View.GONE);
            }
        }

    }

    void setupClick() {
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
            if (project.getId() != null) {
                Intent intent = new Intent(this, NopDeCuongActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timeLineThucHienHP.setOnClickListener(v -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, ProgressLogActivity.class);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                intent.putExtra(Constants.KEY_PROJECT_ID, assignment.getProject_id());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timeLineNopBaoCao.setOnClickListener(v -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, NopBaoCaoActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelineTraCuuHoiDong.setOnClickListener(timeline5 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuHoiDongActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelinePhanBien.setOnClickListener(timeline6 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuHoiDongActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelineTraCuuPhanBien.setOnClickListener(timeline7 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuPhanBienActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.timelineBaoVe.setOnClickListener(timeline8 -> {
            if (project.getId() != null) {
                Intent intent = new Intent(this, TraCuuBaoVeActivity.class);
                intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
                intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng kí đề tài", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnViewTopicDetail.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChiTietDoAnActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
            startActivity(intent);
        });
    }
}