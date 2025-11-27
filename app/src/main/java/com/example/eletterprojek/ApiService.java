// File: ApiService.java
package com.example.eletterprojek;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // === PERBAIKAN UTAMA: Sesuaikan path dengan server.js ===
    @POST("auth/register") // Ganti dari "register" menjadi "auth/register"
    Call<AuthResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("auth/login") // Ganti dari "login" menjadi "auth/login"
    Call<AuthResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("api/auth/register-guru")
    Call<AuthResponse> registerGuru(@Body RegisterGuruRequest registerGuruRequest);
}
