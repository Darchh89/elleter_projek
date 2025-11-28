package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("user_code")
    private String userCode;

    private String password;

    public LoginRequest(String userCode, String password) {
        this.userCode = userCode;
        this.password = password;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
