package com.example.testui.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import com.example.testui.repository.SinhVienRepository;

public class LoginViewModel extends ViewModel {
    private SinhVienRepository loginRepository;

    MutableLiveData<Boolean> islogin = new MutableLiveData<>(false);

    public LoginViewModel(Context context) {
        this.loginRepository = new SinhVienRepository(context);
    }

    public void login(String username, String password, boolean isLogin) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password, isLogin);
        islogin = loginRepository.getLoginResult();
    }

    public boolean getSaveInf() {
        return loginRepository.getSaveInf();
    }

    public boolean getLogin() {
        return loginRepository.getLogin();
    }

    public String getEmail() {
        return loginRepository.getEmail();
    }

    public String getPassword() {
        return loginRepository.getPassword();
    }

    public MutableLiveData<Boolean> getIslogin() {
        return islogin;
    }
}