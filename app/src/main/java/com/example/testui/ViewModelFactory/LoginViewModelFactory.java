package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public LoginViewModelFactory(Context context) {
        this.context = context.getApplicationContext(); // tránh leak memory
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}