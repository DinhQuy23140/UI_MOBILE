package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.TraCuuHoiDongViewModel;

public class TraCuuHoiDongViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public TraCuuHoiDongViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TraCuuHoiDongViewModel.class)) {
            return (T) new TraCuuHoiDongViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
