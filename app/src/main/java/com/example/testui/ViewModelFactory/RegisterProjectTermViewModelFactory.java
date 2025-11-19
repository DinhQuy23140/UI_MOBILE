package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.RegisterProjectTermViewModel;

public class RegisterProjectTermViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public RegisterProjectTermViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterProjectTermViewModel.class)) {
            return (T) new RegisterProjectTermViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
