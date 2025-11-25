package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    // Tambahkan field lain jika server mengirimkan data user atau token
    // @SerializedName("token")
    // private String token;

    // Buat getter untuk mengambil datanya
    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    // public String getToken() { return token; }
}
    