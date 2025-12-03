package com.example.eletterprojek;

import com.google.gson.annotations.SerializedName;

public class    AuthResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;


    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }


    public boolean isSuccess() {
        return success;
    }

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
