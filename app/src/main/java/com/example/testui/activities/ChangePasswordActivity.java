package com.example.testui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.R;
import com.example.testui.ViewModel.ChangePasswordViewModel;
import com.example.testui.ViewModelFactory.ChangePasswordViewModelFactory;
import com.example.testui.databinding.ActivityChangePasswordBinding;
import com.example.testui.untilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    ChangePasswordViewModel changePasswordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        observer();
        setupClick();
    }

    void init() {
        changePasswordViewModel = new ViewModelProvider(this, new ChangePasswordViewModelFactory(this)).get(ChangePasswordViewModel.class);
    }

    void setupClick() {
        binding.btnBack.setOnClickListener(back -> {
            finish();
        });

        binding.changeBtnSubmit.setOnClickListener(submit -> {
            String currentPassword = binding.changeEtCurrentPassword.getText().toString();
            String newPassword = binding.changeEtNewPassword.getText().toString();
            String confirmPassword = binding.changeEtConfirmPassword.getText().toString();
            String token = "Bearer " + changePasswordViewModel.getAccessToken();
            if (validate()) {
                Map<String, String> body = new HashMap<>();
                body.put(Constants.KEY_CURRENT_PASSWORD, currentPassword);
                body.put(Constants.KEY_PASSWORD, newPassword);
                body.put(Constants.KEY_CONFIRM_PASSWORD, confirmPassword);
                changePasswordViewModel.changePassword(token, body);
            }
        });
    }

    boolean validate() {
        String currentPassword = binding.changeEtCurrentPassword.getText().toString();
        String newPassword = binding.changeEtNewPassword.getText().toString();
        String confirmPassword = binding.changeEtConfirmPassword.getText().toString();
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        if (currentPassword.length() < 8) {
            showMessage("Mật khẩu hiện tại phải có ít nhất 8 ký tự");
            return false;
        }

        if (newPassword.length() < 8) {
            showMessage("Mật khẩu mới phải có ít nhất 8 ký tự");
            return false;
        }

        if (confirmPassword.length() < 8) {
            showMessage("Xác nhận mật khẩu phải có ít nhất 8 ký tự");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            showMessage("Mật khẩu mới và xác nhận mật khẩu không khớp");
            return false;
        }
        return true;
    }

    void observer() {
        changePasswordViewModel.getIsChangePasswordSuccess().observe(this, result -> {
            if (result) {
                showMessage("Thay đổi mật khẩu thành công");
                finish();
            } else {
                showMessage("Có lỗi xảy ra");
            }
        });
    }

    void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}