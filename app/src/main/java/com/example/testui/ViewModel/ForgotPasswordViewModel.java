package com.example.testui.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.client.Client;
import com.example.testui.repository.SinhVienRepository;
import com.example.testui.service.ResetPasswordService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordViewModel extends ViewModel {
    SinhVienRepository sinhVienRepository;
    private ResetPasswordService resetPasswordService;
    MutableLiveData<Boolean> isSendSuccess = new MutableLiveData();
    MutableLiveData<Boolean> isResetPasswordSuccess = new MutableLiveData();
    Context context;

    public ForgotPasswordViewModel(Context context) {
        this.context = context;
        sinhVienRepository = new SinhVienRepository(context);
        isSendSuccess = sinhVienRepository.getIsSendSuccess();
        isResetPasswordSuccess = sinhVienRepository.getIsResetPasswordSuccess();
    }

    public void sendResetPassword(Map<String, String> body) {
        sinhVienRepository.sendResetPassword(body);
    }

    public MutableLiveData<Boolean> getIsSendSuccess() {
        return isSendSuccess;
    }

    public void resetPassword(Map<String, String> body) {
        sinhVienRepository.resetPassword(body);
    }

    public MutableLiveData<Boolean> getIsResetPasswordSuccess() {
        return isResetPasswordSuccess;
    }

}
