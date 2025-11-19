package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.R;
import com.example.testui.ViewModel.HomeViewModel;
import com.example.testui.ViewModelFactory.HomeViewModelFactory;
import com.example.testui.databinding.ActivityCapNhapThongTinBinding;
import com.example.testui.model.Department;
import com.example.testui.model.Faculties;
import com.example.testui.model.Marjor;
import com.example.testui.model.Student;
import com.example.testui.model.User;
import com.example.testui.untilities.formatter.DepartmentFormatter;
import com.example.testui.untilities.formatter.FacultiesFormatter;
import com.example.testui.untilities.formatter.MarjorFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

public class UpdateInforPersonActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    ActivityCapNhapThongTinBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCapNhapThongTinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        fetchData();
    }

    void init() {
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(this)).get(HomeViewModel.class);
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });
    }

    void fetchData(){
        homeViewModel.getStudentById();
        homeViewModel.getGetStudent().observe(this, result -> {
            Log.d("Student", new Gson().toJson(result));
            if (result != null) {
                Student student = StudentFormatter.format(result);
                Toast.makeText(this, student.getUser().getFullname(), Toast.LENGTH_SHORT).show();
                binding.tvSvMsv.setText(student.getStudent_code());
                User user = UserFormatter.format(student.getUser());
                binding.tvSvFullname.setText(user.getFullname());
                binding.tvSvDob.setText(user.getDob());
                binding.tvSvEmail.setText(user.getEmail());
                binding.tvSvPhone.setText(user.getPhone());
                String address = user.getAddress();
                binding.tvSvAddress.setText(address);
                binding.tvSvCourse.setText(student.getCourse_year());
                binding.tvSvClass.setText(student.getClass_code() );
                Marjor marjor = MarjorFormatter.format(student.getMarjor());
                binding.tvSvMajor.setText(marjor.getCode() + " - " + marjor.getName());
                Department department = DepartmentFormatter.format(marjor.getDepartment());
                Faculties faculties = FacultiesFormatter.format(department.getFaculties());
                binding.tvSvDepartment.setText(faculties.getCode() + " - " + faculties.getName());
            }
        });
    }
}