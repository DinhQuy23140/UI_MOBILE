package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        Intent intent = getIntent();
        String studentId = intent.getStringExtra(Constants.KEY_ID_STUDENT);
        projectTermAdapter = new ProjectTermAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {
                Intent intent1 = new Intent(DanhSachDotDoAnActivity.this, TimeLineActivity.class);
                intent1.putExtra(Constants.KEY_PROJECT_TERM, new Gson().toJson(listProjectTerm.get(position)));
                //Toast.makeText(DanhSachDotDoAnActivity.this, new Gson().toJson(listProjectTerm.get(position)), Toast.LENGTH_SHORT).show();
                Log.d("ProjectTerm", new Gson().toJson(listProjectTerm.get(position)));
                startActivity(intent1);
            }
        });
        binding.rcvDotDoAn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvDotDoAn.setAdapter(projectTermAdapter);

        if (studentId != null) {
            dotDoAnViewModel = new DotDoAnViewModel(this);
            dotDoAnViewModel.getListDoAnByStudentId(studentId);
            Toast.makeText(this, studentId, Toast.LENGTH_SHORT).show();
            dotDoAnViewModel.getListDotDoAn().observe(this, result -> {
                if (result != null) {
                    listProjectTerm = result;
                    projectTermAdapter.updateData(listProjectTerm);
                }
            });
        }
    }
}