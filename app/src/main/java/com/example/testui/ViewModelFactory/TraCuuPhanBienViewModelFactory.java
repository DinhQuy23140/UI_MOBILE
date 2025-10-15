package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.TraCuuPhanBienViewModel;

public class TraCuuPhanBienViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public TraCuuPhanBienViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TraCuuPhanBienViewModel.class)) {
            return (T) new TraCuuPhanBienViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
