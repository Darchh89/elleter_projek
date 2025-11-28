package com.example.eletterprojek;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/register")
    Call<RegisterGuruResponse> registerGuru(@Body RegisterGuruRequest registerGuruRequest);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
