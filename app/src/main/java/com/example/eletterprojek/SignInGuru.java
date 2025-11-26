package com.example.eletterprojek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInGuru extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private TextView textDaftar; // Tambahkan ini untuk teks "Daftar"

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_guru);

        // --- PERBAIKAN 1: Sesuaikan ID dengan file XML Anda ---
        etEmail = findViewById(R.id.NamaDaftar);         // ID EditText untuk email/ID
        etPassword = findViewById(R.id.PWDaftar); // ID EditText untuk password
        btnLogin = findViewById(R.id.ButtonMasuk);       // ID Tombol "Masuk"
        textDaftar = findViewById(R.id.TextDaftar);      // ID TextView "Daftar"

        // Listener untuk tombol Login
        btnLogin.setOnClickListener(v -> handleLogin());

        // Listener untuk teks "Daftar" (untuk pindah ke halaman registrasi)
        textDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(SignInGuru.this, SignUpGuru.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Memuat...");

        // Gunakan kelas LoginRequest yang sudah Anda buat
        LoginRequest loginRequest = new LoginRequest(email, password);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // --- PERBAIKAN 2: Gunakan "AuthResponse" bukan "LoginResponse" ---
        Call<AuthResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnLogin.setEnabled(true);
                btnLogin.setText("Masuk");

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();

                    // Periksa flag "success" dari backend
                    if (authResponse.isSuccess()) {
                        // Login Berhasil (Kode 200 dan success:true)
                        Toast.makeText(SignInGuru.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // --- PERBAIKAN 3: Ambil token dengan cara yang benar ---
                        if (authResponse.getData() != null && authResponse.getData().getToken() != null) {
                            String token = authResponse.getData().getToken();
                            Log.d("LoginSuccess", "Token berhasil didapat: " + token);

                            // TODO: SIMPAN TOKEN dan PINDAH KE HALAMAN UTAMA
                            // Contoh:
                            // SharedPreferences.Editor editor = getSharedPreferences("APP_PREFS", MODE_PRIVATE).edit();
                            // editor.putString("AUTH_TOKEN", token);
                            // editor.apply();
                            //
                            // startActivity(new Intent(SignInGuru.this, HomeActivity.class));
                            // finish();
                        }

                    } else {
                        // Login Gagal (Kode 200 tapi success:false, misal: akun tidak aktif)
                        Toast.makeText(SignInGuru.this, authResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    // Login Gagal (Kode 401, 404, 500, dll.)
                    // Coba baca pesan error dari server jika ada
                    String errorMessage = "Login Gagal. Kode: " + response.code();
                    Toast.makeText(SignInGuru.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                btnLogin.setText("Masuk");

                Log.e("RetrofitError", "onFailure: " + t.getMessage());
                Toast.makeText(SignInGuru.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
