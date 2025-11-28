package com.example.testui.activities;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testui.ViewModel.LoginViewModel;
import com.example.testui.ViewModelFactory.LoginViewModelFactory;
import com.example.testui.databinding.ActivityLoginBinding;
import com.google.android.material.checkbox.MaterialCheckBox;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this))
                .get(LoginViewModel.class);
        final EditText emailEditText = binding.loginEtEmail;
        final EditText passwordEditText = binding.loginEtPassword;
        final Button loginButton = binding.loginBtnLogin;
        final ProgressBar loadingProgressBar = binding.loading;
        MaterialCheckBox saveInfCheckBox = binding.loginCbSaveInf;
        boolean isSave = saveInfCheckBox.isChecked();
        boolean getSaveInf = loginViewModel.getSaveInf();
        if (getSaveInf) {
            emailEditText.setText(loginViewModel.getEmail());
            passwordEditText.setText(loginViewModel.getPassword());
        }

        boolean isLogin = loginViewModel.getLogin();
        if (isLogin) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        loginButton.setOnClickListener(login -> {
            String username = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginViewModel.login(username, password, isSave);
            loginViewModel.getIslogin().observe(this, result -> {
                if (result) {
                    Toast.makeText(this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        });

        binding.loginTvGoToRegister.setOnClickListener(register->{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.loginTvForgotPassword.setOnClickListener(forgotPass -> {
            Intent intent = new Intent(this, ForgotPassActivity.class);
            startActivity(intent);
        });


    }
}