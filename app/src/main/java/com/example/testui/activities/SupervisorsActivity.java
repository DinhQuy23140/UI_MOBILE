package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.GVHDViewModel;
import com.example.testui.ViewModelFactory.GVHDViewModelFactory;
import com.example.testui.adapter.GVHDAdapter;
import com.example.testui.adapter.TeacherAdapter;
import com.example.testui.databinding.ActivitySupervisorsBinding;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.List;

public class SupervisorsActivity extends AppCompatActivity {
    ActivitySupervisorsBinding binding;
    GVHDViewModel gvhdViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySupervisorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gvhdViewModel = new ViewModelProvider(this, new GVHDViewModelFactory(this)).get(GVHDViewModel.class);
//        gvhdViewModel.getGVHD();
//        gvhdViewModel.getListSupervisor().observe(this, result -> {
//            List<Supervisor> listSupervisor = result;
//            binding.tvCountSupervisor.setText(String.valueOf(listSupervisor.size()));
//            Log.d("Supervisor", result.size() + "");
//            GVHDAdapter gvhdAdapter = new GVHDAdapter(this, listSupervisor, position -> {
//                Intent intent = new Intent(this, SupervisorActivity.class);
//                Gson gson = new Gson();
//                Supervisor supervisor = listSupervisor.get(position);
//                String strGVHD = gson.toJson(supervisor);
//                intent.putExtra(Constants.KEY_SUPERVISOR, strGVHD);
//                startActivity(intent);
//            });
//            binding.rvGiangVien.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//            binding.rvGiangVien.setAdapter(gvhdAdapter);
//        });
        gvhdViewModel.loadTeacher();
        gvhdViewModel.getTeachers().observe(this, result -> {
            List<Teacher> listTeacher = result;
            binding.tvCountSupervisor.setText(String.valueOf(listTeacher.size()));
            Log.d("Teacher", result.size() + "");
            TeacherAdapter teacherAdapter = new TeacherAdapter(this, listTeacher, position -> {
                Intent intent = new Intent(this, SupervisorActivity.class);
                Gson gson = new Gson();
                Teacher teacher = listTeacher.get(position);
                String strTeacher = gson.toJson(teacher);
                intent.putExtra(Constants.KEY_TEACHER, strTeacher);
                startActivity(intent);
            });
            binding.rvGiangVien.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.rvGiangVien.setAdapter(teacherAdapter);
        });
    }
}