package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    // PENTING: Nama field di @SerializedName HARUS SAMA PERSIS dengan di backend
    @SerializedName("fullname") // Ganti dari "name" menjadi "fullname"
    private String fullname;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("role_id") // TAMBAHKAN INI
    private int role_id;

    public RegisterRequest(String fullname, String email, String password, int role_id) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role_id = role_id; // TAMBAHKAN INI
    }
}
    