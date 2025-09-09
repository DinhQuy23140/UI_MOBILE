package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.testui.model.Student;
import com.example.testui.untilities.Constants;

public class CapNhapThongTinActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    ActivityCapNhapThongTinBinding binding;
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

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(this)).get(HomeViewModel.class);

        Intent intent = getIntent();
        String studentId = intent.getStringExtra(Constants.KEY_ID_STUDENT);
        homeViewModel.getStudentById();
        homeViewModel.getGetStudent().observe(this, result -> {
            if (result != null) {
                Student student = result;
                Toast.makeText(this, student.getUser().getFullname(), Toast.LENGTH_SHORT).show();
                binding.tvSvMsv.setText(student.getStudent_code());
                binding.tvSvFullname.setText(student.getUser().getFullname());
                binding.tvSvDob.setText(student.getUser().getDob());
                binding.tvSvEmail.setText(student.getUser().getEmail());
                binding.tvSvPhone.setText(student.getUser().getPhone());
                String address = student.getUser().getAddress();
                binding.tvSvAddress.setText(address);
                binding.tvSvCourse.setText(student.getCourse_year());
            }
        });
    }
}