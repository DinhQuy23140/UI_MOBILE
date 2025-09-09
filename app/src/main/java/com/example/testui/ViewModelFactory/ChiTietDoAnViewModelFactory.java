package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.ChiTietDoAnViewModel;

public class ChiTietDoAnViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public ChiTietDoAnViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(ChiTietDoAnViewModel.class)) {
            return (T) new ChiTietDoAnViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
