package com.example.testui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.R;
import com.example.testui.ViewModel.ForgotPasswordViewModel;
import com.example.testui.ViewModelFactory.ForgotPasswordViewModelFactory;
import com.example.testui.databinding.ActivityForgotPassBinding;
import com.example.testui.untilities.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ForgotPassActivity extends AppCompatActivity {

    ActivityForgotPassBinding binding;
    ForgotPasswordViewModel forgotPasswordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityForgotPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        observer();
        setClick();
    }

    void setClick() {
        binding.resetTvBackToLogin.setOnClickListener(login -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.resetBtnSend.setOnClickListener(v -> {
            String email = binding.resetEtEmail.getText().toString();
            if (validate(email)) {
                Map<String, String> body = new HashMap<>();
                body.put(Constants.KEY_EMAIL, email);
                forgotPasswordViewModel.sendResetPassword(body);
            }
        });
    }

    void init() {
        forgotPasswordViewModel = new ViewModelProvider(this, new ForgotPasswordViewModelFactory(this)).get(ForgotPasswordViewModel.class);
    }

    boolean validate(String email) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void observer(){
        forgotPasswordViewModel.getIsSendSuccess().observe(this, result -> {
            if (result) {
                Toast.makeText(this, "Vui lòng kiểm tra email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Gửi thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}