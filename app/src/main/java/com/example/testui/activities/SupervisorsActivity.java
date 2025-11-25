package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SupervisorsActivity extends AppCompatActivity {
    ActivitySupervisorsBinding binding;
    GVHDViewModel gvhdViewModel;
    List<Teacher> listTeacher = new ArrayList<>();
    List<Teacher> listTeacherDisplay = new ArrayList<>();
    TeacherAdapter teacherAdapter;
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

        init();
        setupRecyclerView();
        loadData();
        observeData();
        event();
    }

    void init() {
        gvhdViewModel = new ViewModelProvider(this, new GVHDViewModelFactory(this)).get(GVHDViewModel.class);
    }

    void loadData() {
        gvhdViewModel.loadTeacher();
    }

    void setupRecyclerView() {
        binding.rvGiangVien.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
         teacherAdapter = new TeacherAdapter(this, new ArrayList<>(), position -> {
            Intent intent = new Intent(this, SupervisorActivity.class);
            Gson gson = new Gson();
            Teacher teacher = listTeacher.get(position);
            String strTeacher = gson.toJson(teacher);
            intent.putExtra(Constants.KEY_TEACHER, strTeacher);
            startActivity(intent);
        });
        binding.rvGiangVien.setAdapter(teacherAdapter);
    }

    void  observeData() {
        gvhdViewModel.getTeachers().observe(this, result -> {
            listTeacher = result;
            binding.tvCountSupervisor.setText(String.valueOf(listTeacher.size()));
            Log.d("Teacher", result.size() + "");
            teacherAdapter.updateData(listTeacher);
        });
    }

    void event() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void filter(String keyWord) {
        listTeacherDisplay.clear();
        if (keyWord.isEmpty()) {
            listTeacherDisplay.addAll(listTeacher);
        } else {
            for (Teacher teacher : listTeacher) {
                User user = UserFormatter.format(teacher.getUser());
                if (user.getFullname().toLowerCase().contains(keyWord.toLowerCase())) {
                    listTeacherDisplay.add(teacher);
                }
            }
        }
        binding.tvCountSupervisor.setText(String.valueOf(listTeacherDisplay.size()));
        teacherAdapter.updateData(listTeacherDisplay);
    }
}