package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.NopDeCuongViewModel;

public class NopDeCuongViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public NopDeCuongViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NopDeCuongViewModel.class)) {
            return (T) new NopDeCuongViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
