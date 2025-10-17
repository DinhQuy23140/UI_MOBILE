package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.ViewAllAssignmentViewModel;

public class ViewAllAssignmentViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ViewAllAssignmentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewAllAssignmentViewModel.class))
            return (T) new ViewAllAssignmentViewModel();
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
