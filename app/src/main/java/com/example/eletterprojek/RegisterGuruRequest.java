package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class RegisterGuruRequest {

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("password")
    private String password;

    @SerializedName("nip")
    private String nip;

    @SerializedName("kode_guru")
    private String kodeGuru;

    public RegisterGuruRequest(String fullname, String password, String nip, String kodeGuru) {
        this.fullname = fullname;
        this.password = password;
        this.nip = nip;
        this.kodeGuru = kodeGuru;
    }
}
