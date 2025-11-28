package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private String message;

    @SerializedName("user_id")
    private int userId;

    private String fullname;

    @SerializedName("user_code")
    private String userCode;

    @SerializedName("role_id")
    private int roleId;

    private String token;

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUserCode() {
        return userCode;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getToken() {
        return token;
    }
}
