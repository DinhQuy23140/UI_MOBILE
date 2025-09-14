package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testui.ViewModel.ChiTietDoAnViewModel;
import com.example.testui.ViewModel.NopBaoCaoViewModel;

public class NopBaoCaoViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public NopBaoCaoViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(NopBaoCaoViewModel.class)) {
            return (T) new NopBaoCaoViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
