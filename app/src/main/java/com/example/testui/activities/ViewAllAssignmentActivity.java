package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.ViewAllAssignmentViewModel;
import com.example.testui.ViewModelFactory.ViewAllAssignmentViewModelFactory;
import com.example.testui.adapter.AssignmentAdapter;
import com.example.testui.databinding.ActivityViewAllAssignmentBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.TeacherFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewAllAssignmentActivity extends AppCompatActivity {
    Intent intent;
    String strTeacher = "";
    Teacher teacher;
    String teacherId = "";
    Gson gson;
    List<Assignment> listAssignment = new ArrayList<>();
    List<Assignment> listAssignmentDisplay = new ArrayList<>();
    AssignmentAdapter assignmentAdapter;
    ActivityViewAllAssignmentBinding binding;
    ViewAllAssignmentViewModel viewAllAssignmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityViewAllAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupRecyclerView();
        loadData();
        event();
    }

    void init(){
        viewAllAssignmentViewModel = new ViewModelProvider(this, new ViewAllAssignmentViewModelFactory(this)).get(ViewAllAssignmentViewModel.class);
        gson = new Gson();
        intent = getIntent();
        strTeacher = intent.getStringExtra(Constants.KEY_TEACHER);
        teacher = TeacherFormatter.format(gson.fromJson(strTeacher, Teacher.class));
        teacherId = teacher.getId();
    }

    void setupRecyclerView() {
        binding.rvSinhVienList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        assignmentAdapter = new AssignmentAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvSinhVienList.setAdapter(assignmentAdapter);
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        binding.tvGiangVienName.setText(teacher.getDegree() + "." + teacher.getUser().getFullname());
        binding.tvChucVu.setText("Chức vụ: " + teacher.getPosition());
        binding.tvGiangVienNameMain.setText(teacher.getDegree() + "." + teacher.getUser().getFullname());
        viewAllAssignmentViewModel.loadAssignments(teacherId);
        viewAllAssignmentViewModel.getListAssignment().observe(this, result ->{
            listAssignment = result;
            binding.tvSoLuongSinhVien.setText(listAssignment.size() + " sinh viên");
            assignmentAdapter.updateDate(listAssignment);
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

    @SuppressLint("SetTextI18n")
    void filter(String keyWord) {
        listAssignmentDisplay.clear();
        if (keyWord.isEmpty()) {
            listAssignmentDisplay.addAll(listAssignment);
        } else {
            for (Assignment assignment : listAssignment) {
                Student student = StudentFormatter.format(assignment.getStudent());
                User user = UserFormatter.format(student.getUser());
                if (user.getFullname().toLowerCase().contains(keyWord.toLowerCase())) {
                    listAssignmentDisplay.add(assignment);
                }
            }
        }
        binding.tvSoLuongSinhVien.setText(listAssignmentDisplay.size() + " sinh viên");
        assignmentAdapter.updateDate(listAssignmentDisplay);
    }
}