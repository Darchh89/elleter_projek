package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class    AuthResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    // Tambahkan inner class untuk menangkap objek "data" dari JSON
    @SerializedName("data")
    private Data data;

    // --- Getter ---

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    // ==========================================================
    // INI YANG PERLU ANDA TAMBAHKAN
    public boolean isSuccess() {
        return success;
    }
    // ==========================================================

    // Inner Class untuk objek "data"
    public static class Data {
        @SerializedName("token")
        private String token;

        @SerializedName("user_code")
        private String user_code;

        public String getToken() {
            return token;
        }

        public String getUserCode() {
            return user_code;
        }
    }
}
