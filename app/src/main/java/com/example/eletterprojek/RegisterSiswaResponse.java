package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class RegisterSiswaResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_code")
    private String userCode;

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserCode() {
        return userCode;
    }
}
