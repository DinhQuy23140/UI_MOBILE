package com.example.testui.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.testui.ViewModel.NopDeCuongViewModel;
import com.example.testui.ViewModel.TimeLineViewModel;
import com.example.testui.repository.AssignmentRepository;

public class TimeLineViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;
    public TimeLineViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TimeLineViewModel.class)) {
            return (T) new TimeLineViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
