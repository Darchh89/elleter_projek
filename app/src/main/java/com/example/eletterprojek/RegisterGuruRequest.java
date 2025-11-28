package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class RegisterGuruRequest {

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    public RegisterGuruRequest(String fullname, String email, String password, String token) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.token = token;
    }
}
