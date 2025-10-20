package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.DotDoAnViewModel;
import com.example.testui.adapter.ProjectTermAdapter;
import com.example.testui.databinding.ActivityDanhSachDotDoAnBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProjectTerm;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDotDoAnActivity extends AppCompatActivity {

    ActivityDanhSachDotDoAnBinding binding;
    DotDoAnViewModel dotDoAnViewModel;
    ProjectTermAdapter projectTermAdapter;
    List<ProjectTerm> listProjectTerm;
    Intent intent;
    String studentId = "";
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDanhSachDotDoAnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        studentId = intent.getStringExtra(Constants.KEY_ID_STUDENT);
        setupClick();
        createRecyclerView();
        fetchDataRecyclerView();
    }

    void init(){
        dotDoAnViewModel = new DotDoAnViewModel(this);
        intent = getIntent();
        gson = new Gson();
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });
    }

    void createRecyclerView() {
        projectTermAdapter = new ProjectTermAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {
                Intent intent1 = new Intent(DanhSachDotDoAnActivity.this, TimeLineActivity.class);
                intent1.putExtra(Constants.KEY_PROJECT_TERM, gson.toJson(listProjectTerm.get(position)));
                Log.d("ProjectTerm", new Gson().toJson(listProjectTerm.get(position)));
                startActivity(intent1);
            }
        });
        binding.rcvDotDoAn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvDotDoAn.setAdapter(projectTermAdapter);
    }

    void fetchDataRecyclerView() {
        if (studentId != null) {
            dotDoAnViewModel.getListDoAnByStudentId(studentId);
            Toast.makeText(this, studentId, Toast.LENGTH_SHORT).show();
            dotDoAnViewModel.getListDotDoAn().observe(this, result -> {
                Log.d("Danhsachdotdoan", gson.toJson(result));
                if (result != null && !result.isEmpty()) {
                    listProjectTerm = result;
                    projectTermAdapter.updateData(listProjectTerm);
                    binding.tvCount.setText(String.valueOf(listProjectTerm.size()));
                    binding.layoutDotDoAn.setVisibility(View.VISIBLE);
                    binding.progressBarIc.setVisibility(View.GONE);
                    binding.rcvDotDoAn.setVisibility(View.VISIBLE);
                } else {
                    binding.rcvDotDoAn.setVisibility(View.GONE);
                    binding.emptyStateView.setVisibility(View.VISIBLE);
                    binding.progressBarIc.setVisibility(View.GONE);
                }
            });
        } else {
            binding.rcvDotDoAn.setVisibility(View.GONE);
            binding.emptyStateView.setVisibility(View.VISIBLE);
            binding.progressBarIc.setVisibility(View.GONE);
        }
    }
}