package com.example.eletterprojek;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    // Endpoint untuk registrasi GURU (memerlukan token)
    @POST("auth/register")
    Call<RegisterGuruResponse> registerGuru(@Body RegisterGuruRequest registerGuruRequest);

    // Endpoint untuk rlegistrasi SISWA (TIDAK memerlukan token)


    // Endpoint untuk login
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
