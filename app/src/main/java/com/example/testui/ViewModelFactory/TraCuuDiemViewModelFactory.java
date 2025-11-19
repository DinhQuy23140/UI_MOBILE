package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.TraCuuDiemViewModel;

public class TraCuuDiemViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public TraCuuDiemViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TraCuuDiemViewModel.class)) {
            return (T) new TraCuuDiemViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
