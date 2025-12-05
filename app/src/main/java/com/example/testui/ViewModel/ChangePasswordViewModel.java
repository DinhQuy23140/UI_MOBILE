package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.repository.SinhVienRepository;

import java.util.Map;

public class ChangePasswordViewModel extends ViewModel {
    Context context;
    SinhVienRepository sinhVienRepository;
    MutableLiveData<Boolean> isChangePasswordSuccess = new MutableLiveData();

    public ChangePasswordViewModel(Context context) {
        this.context = context;
        sinhVienRepository = new SinhVienRepository(context);
        isChangePasswordSuccess = sinhVienRepository.getIsChangePasswordSuccess();
    }
    public void changePassword(String token, Map<String, String> body) {
        sinhVienRepository.changePassword(token, body);
    }

    public MutableLiveData<Boolean> getIsChangePasswordSuccess() {
        return isChangePasswordSuccess;
    }

    public String getEmail() {
        return sinhVienRepository.getEmail();
    }

    public String getAccessToken() {
        return sinhVienRepository.getAccessToken();
    }
}
