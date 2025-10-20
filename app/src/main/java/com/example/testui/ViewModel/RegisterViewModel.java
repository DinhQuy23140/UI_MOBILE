package com.example.testui.ViewModel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.User;
import com.example.testui.repository.SinhVienRepository;

public class RegisterViewModel extends ViewModel {
    Context context;
    SinhVienRepository sinhVienRepository;
    MutableLiveData<Boolean> registerResult = new MutableLiveData<>(false);

    public RegisterViewModel(Context context) {
        this.context = context;
        sinhVienRepository = new SinhVienRepository(context);
    }

    public void register(User user) {
        sinhVienRepository.register(user);
        registerResult = sinhVienRepository.getRegisterResult();
    }

    public MutableLiveData<Boolean> getRegisterResult() {
        return registerResult;
    }
}
