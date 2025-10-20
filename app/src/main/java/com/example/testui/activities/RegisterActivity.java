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
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.R;
import com.example.testui.ViewModel.RegisterViewModel;
import com.example.testui.ViewModelFactory.RegisterViewModelFactory;
import com.example.testui.databinding.ActivityRegisterBinding;
import com.example.testui.model.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        observer();
    }

    void init() {
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory(this)).get(RegisterViewModel.class);
    }

    void register() {
        String fullname = Objects.requireNonNull(binding.registerEtFullname.getText()).toString();
        String email = binding.registerEtEmail.getText().toString();
        String password = binding.registerEtPassword.getText().toString();
        registerViewModel.register(new User(email, password, fullname));
    }

    void observer() {
        registerViewModel.getRegisterResult().observe(this, result -> {
            if (result) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.d("Reegister", result.toString());
            } else {
                Log.d("Reegister", "Có lỗi xảy ra");
            }
        });
    }

    void setupClick() {
        binding.registerTvLoginLink.setOnClickListener(login -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.registerBtnRegister.setOnClickListener(register -> {
            register();
        });
    }
}