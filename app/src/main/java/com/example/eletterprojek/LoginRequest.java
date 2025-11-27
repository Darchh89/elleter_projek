package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("user_code")
    private String userCode;

    @SerializedName("password")
    private String password;

    public LoginRequest(String userCode, String password) {
        this.userCode = userCode;
        this.password = password;
    }
}
