package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.DotDoAnViewModel;

public class DotDoAnViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public DotDoAnViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(DotDoAnViewModel.class)) {
            return (T) new DotDoAnViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
