package com.example.testui.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testui.untilities.Constants;

public class SharePreferenceManage {
    private final SharedPreferences sharedPreferences;

    public SharePreferenceManage (Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.KEY_SHARE_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void saveTokenType(String tokenType) {
        sharedPreferences.edit().putString(Constants.KEY_TOKEN_TYPE, tokenType).apply();
    }

    public String getTokenType() {
        return sharedPreferences.getString(Constants.KEY_TOKEN_TYPE, "");
    }

    public void saveAccessToken(String accessToken) {
        sharedPreferences.edit().putString(Constants.KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(Constants.KEY_ACCESS_TOKEN, "");
    }

    public void saveUserId(String userId) {
        sharedPreferences.edit().putString(Constants.KEY_USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(Constants.KEY_USER_ID, "");
    }

    public String saveIdStudent(String id) {
        sharedPreferences.edit().putString(Constants.KEY_ID_STUDENT, id).apply();
        return id;
    }

    public String getIdStudent() {
        return sharedPreferences.getString(Constants.KEY_ID_STUDENT, "");
    }

    public void saveFullName(String fullname) {
        sharedPreferences.edit().putString(Constants.KEY_FULL_NAME, fullname).apply();
    }

    public String getFullName() {
        return sharedPreferences.getString(Constants.KEY_FULL_NAME, "");
    }

    public void saveEmail(String email) {
        sharedPreferences.edit().putString(Constants.KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(Constants.KEY_EMAIL, "");
    }

    public void saveRole(String role) {
        sharedPreferences.edit().putString(Constants.KEY_ROLE, role).apply();
    }

    public String getRole() {
        return sharedPreferences.getString(Constants.KEY_ROLE, "");
    }

    public void savePassword(String password) {
        sharedPreferences.edit().putString(Constants.KEY_PASSWORD, password).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(Constants.KEY_PASSWORD, "");
    }

    public void saveIsLogin(boolean isLogin) {
        sharedPreferences.edit().putBoolean(Constants.KEY_IS_LOGIN, isLogin).apply();
    }

    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(Constants.KEY_IS_LOGIN, false);
    }

    public void saveInfoUser(boolean isSave) {
        sharedPreferences.edit().putBoolean(Constants.KEY_SAVE_INF,isSave).apply();
    }

    public boolean getInfoUser() {
        return sharedPreferences.getBoolean(Constants.KEY_SAVE_INF, false);
    }

    public void saveDOB(String dob) {
        sharedPreferences.edit().putString(Constants.KEY_DOB, dob).apply();
    }
}
