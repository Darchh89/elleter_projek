package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class RegisterSiswaRequest {

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public RegisterSiswaRequest(String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
}
