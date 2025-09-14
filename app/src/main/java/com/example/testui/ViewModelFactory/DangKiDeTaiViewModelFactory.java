package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.ChiTietDoAnViewModel;
import com.example.testui.ViewModel.DangKiDeTaiViewModel;

public class DangKiDeTaiViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public DangKiDeTaiViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(DangKiDeTaiViewModel.class)) {
            return (T) new DangKiDeTaiViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
