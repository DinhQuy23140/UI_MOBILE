package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.GVHDViewModel;

public class GVHDViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public GVHDViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(GVHDViewModel.class)) {
            return (T) new GVHDViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
