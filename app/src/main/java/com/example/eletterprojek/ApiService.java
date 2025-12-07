package com.example.eletterprojek;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface
ApiService {
    // Endpoint registrasi guru
    @POST("auth/register")
    Call<RegisterGuruResponse> registerGuru(@Body RegisterGuruRequest registerGuruRequest);

    // Endpoint registrasi siswa
    @POST("auth/register_siswa")
    Call<RegisterSiswaResponse> registerSiswa(@Body RegisterSiswaRequest registerSiswaRequest);

    // Endpoint login
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
