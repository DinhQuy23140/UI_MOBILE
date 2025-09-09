package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.HomeViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public HomeViewModelFactory(Context context) {
        this.context = context.getApplicationContext(); // tránh leak memory
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
