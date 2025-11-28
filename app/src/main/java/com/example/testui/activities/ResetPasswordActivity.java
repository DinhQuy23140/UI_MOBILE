package com.example.testui.activities;

import android.content.Intent;
import android.net.Uri;
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
import com.example.testui.ViewModel.ForgotPasswordViewModel;
import com.example.testui.ViewModelFactory.ForgotPasswordViewModelFactory;
import com.example.testui.databinding.ActivityResetPasswordBinding;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    Intent intent;
    Uri uri;
    String token, email;
    ForgotPasswordViewModel forgotPasswordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        observer();
        onClick();
        loadData();
    }

    void onClick() {
        binding.resetBtnSend.setOnClickListener(v -> {
            String newPassword = binding.resetEtNewPassword.getText().toString();
            String confirmPassword = binding.resetEtConfirmPassword.getText().toString();
            if (validate()) {
                Map<String, String> body = new HashMap<>();
                body.put(Constants.KEY_EMAIL, email);
                body.put(Constants.KEY_PASSWORD, newPassword);
                body.put(Constants.KEY_CONFIRM_PASSWORD, confirmPassword);
                body.put(Constants.KEY_TOKEN, token);
                Log.d("Body", new Gson().toJson(body));
                forgotPasswordViewModel.resetPassword(body);
            }
        });
    }

    void init() {
        intent = getIntent();
        uri = intent.getData();
        forgotPasswordViewModel = new ViewModelProvider(this, new ForgotPasswordViewModelFactory(this)).get(ForgotPasswordViewModel.class);
    }

    void loadData() {
        if (uri != null) {
            token = uri.getLastPathSegment();
            email = uri.getQueryParameter("email");
            binding.resetEtEmail.setText(email);
            Log.d("Result token and email", token + " " + email);
        }
    }

    boolean validate() {
        String newPassword = binding.resetEtNewPassword.getText().toString();
        String confirmPassword = binding.resetEtConfirmPassword.getText().toString();
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void observer() {
        forgotPasswordViewModel.getIsResetPasswordSuccess().observe(this, isResetPasswordSuccess -> {
            if (isResetPasswordSuccess) {
                Toast.makeText(this, "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}