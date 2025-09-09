package com.example.testui.model;

public class LoginResponse {
    boolean success;
    String message;
    String token_type;
    String access_token;
    UserLogin user;

    public LoginResponse(String access_token, String message, boolean success, String token_type, UserLogin user) {
        this.access_token = access_token;
        this.message = message;
        this.success = success;
        this.token_type = token_type;
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public UserLogin getUserLogin() {
        return user;
    }

    public void setUserLogin(UserLogin user) {
        this.user = user;
    }
}
