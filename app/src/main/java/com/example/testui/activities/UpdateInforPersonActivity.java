package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.DepartmentFormatter;
import com.example.testui.untilities.formatter.FacultiesFormatter;
import com.example.testui.untilities.formatter.MarjorFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
        observed();
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

        binding.btnChangePassword.setOnClickListener(change -> {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.tvSvDob.setOnClickListener(date -> {
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // Format dd/MM/yyyy
                        String selectedDate = String.format("%02d/%02d/%04d",
                                dayOfMonth, (monthOfYear + 1), year1);

                        binding.tvSvDob.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        binding.btnSave.setOnClickListener(save -> {
            String user_Id = homeViewModel.getUserId();
            String student_id = homeViewModel.getStudentId();
            String student_code = binding.tvSvMsv.getText().toString();
            String fullname = binding.tvSvFullname.getText().toString();
            String[] dob = binding.tvSvDob.getText().toString().split("/");
            String converDob = dob[2] + "-" + dob[1] + "-" + dob[0];
            String phone = binding.tvSvPhone.getText().toString();
            String address = binding.tvSvAddress.getText().toString();
            String class_code = binding.tvSvClass.getText().toString();
            String marjor_id = binding.tvSvMajor.getText().toString();
            Map<String, String> body = new HashMap<>();
            body.put(Constants.KEY_USER_ID, user_Id);
            body.put(Constants.KEY_STUDENT_ID, student_id);
            body.put(Constants.KEY_STUDENT_CODE, student_code);
            body.put(Constants.KEY_FULL_NAME, fullname);
            body.put(Constants.KEY_DOB, converDob);
            body.put(Constants.KEY_PHONE, phone);
            body.put(Constants.KEY_ADDRESS, address);
            body.put(Constants.KEY_CLASS_CODE, class_code);
            body.put(Constants.KEY_MAJOR_ID, marjor_id);
            homeViewModel.updateInfStudent(student_id, body);
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
                String[] dob = user.getDob().split("-");
                String displayDob = dob[2] + "/" + dob[1] + "/" + dob[0];
                binding.tvSvDob.setText(displayDob);
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
                binding.tvAccountEmail.setText(user.getEmail());
            }
        });
    }

    void observed() {
        homeViewModel.getIsUpdateSuccess().observe(this, result -> {
            if (result) {
                Toast.makeText(this, "Cập nhập thông tin thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}