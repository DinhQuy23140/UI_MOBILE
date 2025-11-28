package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.ForgotPasswordViewModel;

public class ForgotPasswordViewModelFactory implements ViewModelProvider.Factory {
    Context context;
    public ForgotPasswordViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel.class)) {
            return (T) new ForgotPasswordViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
