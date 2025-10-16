package com.example.testui.ViewModelFactory;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.TraCuuBaoVeViewModel;

public class TraCuuBaoVeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public TraCuuBaoVeViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TraCuuBaoVeViewModel.class)) {
            return (T) new TraCuuBaoVeViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
