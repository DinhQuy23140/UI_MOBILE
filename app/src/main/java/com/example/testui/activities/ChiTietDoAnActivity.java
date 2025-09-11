package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ChiTietDoAnViewModel;
import com.example.testui.ViewModelFactory.ChiTietDoAnViewModelFactory;
import com.example.testui.adapter.GVHDAdapter;
import com.example.testui.adapter.ProcessLogAdapter;
import com.example.testui.databinding.ActivityChiTietDoAnBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDoAnActivity extends AppCompatActivity {
    ProcessLogAdapter processLogAdapter;
    Assignment assignment;
    ChiTietDoAnViewModel chiTietDoAnViewModel;
    ActivityChiTietDoAnBinding binding;
    List<Supervisor> listSuperVisor;
    GVHDAdapter gvhdAdapter;
    Gson gson;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gson = new Gson();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChiTietDoAnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chiTietDoAnViewModel = new ViewModelProvider(this, new ChiTietDoAnViewModelFactory(this)).get(ChiTietDoAnViewModel.class);
        processLogAdapter = new ProcessLogAdapter(this, new ArrayList<>(), position -> {
            Intent intent = new Intent(ChiTietDoAnActivity.this, ProgressLogDetailActivity.class);
            intent.putExtra(Constants.KEY_PROGRESS_LOG, gson.toJson(processLogAdapter.getItem(position)));
            startActivity(intent);
        });

        binding.rvProgressLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvProgressLog.setAdapter(processLogAdapter);

        gvhdAdapter = new GVHDAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(ChiTietDoAnActivity.this, GVHDActivity.class);
                intent.putExtra(Constants.KEY_SUPERVISOR, new Gson().toJson(assignment.getAssignment_supervisors().get(position).getSupervisor()));
                startActivity(intent);
            }
        });
        binding.rvGvhd.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvGvhd.setAdapter(gvhdAdapter);

        String studentId = chiTietDoAnViewModel.getIdStudent();
        chiTietDoAnViewModel.getChiTietDoAn(studentId);
        chiTietDoAnViewModel.getAssignmentMutableLiveData().observe(this, result -> {
            if (result != null) {
                assignment = result;
                binding.tvMaDeTai.setText("Mã đề tài: " + assignment.getId());
                binding.tvTenDeTai.setText("Tên đề tài: " + assignment.getProject().getName());
                binding.tvMoTa.setText("Mô tả: " + assignment.getProject().getDescription());
                if (!assignment.getProject().getProgress_logs().isEmpty()) {
                    processLogAdapter.updateData(assignment.getProject().getProgress_logs());
                }
                Log.d("Supervisor", String.valueOf(assignment.getAssignment_supervisors().size()));
                if (!assignment.getAssignment_supervisors().isEmpty()) {
                    List<Supervisor> convertSupervisor = new ArrayList<>();
                    for (AssignmentSupervisor assignmentSupervisor : assignment.getAssignment_supervisors()) {
                        convertSupervisor.add(assignmentSupervisor.getSupervisor());
                    }
                    gvhdAdapter.updateData(convertSupervisor);
                }

            }
        });

        binding.btnDetailProgress.setOnClickListener(detailProgress -> {
            Intent intent = new Intent(this, ProgressLogActivity.class);
            intent.putExtra(Constants.KEY_PROGRESS_LOG, new Gson().toJson(assignment.getProject().getProgress_logs()));
            startActivity(intent);
        });

        binding.btnNopDeCuong.setOnClickListener(nopedcuong -> {
            Intent intent = new Intent(ChiTietDoAnActivity.this, NopDeCuongActivity.class);
            startActivity(intent);
        });

        binding.btnNopBaoCao.setOnClickListener(nopbaocao -> {
            Intent intent = new Intent(ChiTietDoAnActivity.this, NopBaoCaoActivity.class);
            startActivity(intent);
        });

        binding.btnTraCuuDiem.setOnClickListener(tracuudiem -> {
            Intent intent = new Intent(ChiTietDoAnActivity.this, TraCuuDiemActivity.class);
            startActivity(intent);
        });
    }
}