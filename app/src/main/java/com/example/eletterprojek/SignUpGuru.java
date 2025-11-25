package com.example.eletterprojek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpGuru extends AppCompatActivity {

    private EditText etEmail, etNama, etPassword;
    private MaterialButton btnDaftar, btnMasuk;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    // Anda bisa menghapus annotation ini jika ID sudah benar semua
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_guru);

        // --- PERBAIKAN 1: Inisialisasi komponen dengan ID yang benar dari XML ---
        etEmail = findViewById(R.id.PWDaftar);
        etNama = findViewById(R.id.NamaDaftar);
        etPassword = findViewById(R.id.PasswordDaftar);
        btnDaftar = findViewById(R.id.ButtonMasuk); // Tombol "Daftar"
        btnMasuk = findViewById(R.id.ButtonMasuk);   // Tombol/Teks "Masuk"

        // Listener untuk tombol/teks "Masuk" (kembali ke halaman login)
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninPage();
            }
        });

        // Listener untuk tombol "Daftar"
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    private void handleRegister() {
        // --- PERBAIKAN 2: Menggunakan nama variabel yang sesuai dengan backend (fullname) ---
        String fullname = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi minimal panjang password sesuai dengan backend
        if (password.length() < 6) {
            Toast.makeText(this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tampilkan status loading di UI
        btnDaftar.setEnabled(false);
        btnDaftar.setText("Mendaftar...");

        // --- PERBAIKAN 3: Mengirimkan data yang dibutuhkan oleh backend (termasuk role_id) ---
        // Asumsikan role_id untuk GURU adalah 2. Sesuaikan jika berbeda di database Anda.
        int roleIdGuru = 2;
        RegisterRequest registerRequest = new RegisterRequest(fullname, email, password, roleIdGuru);

        // Panggil ApiService yang endpoint-nya sudah diperbaiki
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AuthResponse> call = apiService.registerUser(registerRequest);

        // Jalankan panggilan API
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                // Kembalikan tombol ke kondisi semula
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");

                if (response.isSuccessful() && response.body() != null) {
                    // Kode 201: Registrasi berhasil
                    AuthResponse authResponse = response.body();
                    Toast.makeText(SignUpGuru.this, authResponse.getMessage(), Toast.LENGTH_LONG).show();

                    if (!authResponse.isError()) {
                        Toast.makeText(SignUpGuru.this, "Registrasi berhasil, silakan masuk.", Toast.LENGTH_SHORT).show();
                        openSigninPage(); // Arahkan ke halaman login setelah berhasil
                    }
                } else {
                    // Tangani error dari server (400, 409, 500, dll.)
                    // Anda bisa menambahkan logika untuk membaca pesan error dari body jika ada
                    String errorMessage = "Registrasi gagal. Kode: " + response.code();
                    Toast.makeText(SignUpGuru.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // Kembalikan tombol ke kondisi semula
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");

                // Tangani error koneksi (tidak ada internet, server mati, URL salah)
                Log.e("RetrofitError", "onFailure: " + t.getMessage());
                Toast.makeText(SignUpGuru.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Method untuk berpindah ke halaman SigninGuru.
     */
    private void openSigninPage() {
        Intent intent = new Intent(SignUpGuru.this, SignInGuru.class);
        startActivity(intent);
        finish(); // Tutup activity ini agar pengguna tidak bisa kembali ke halaman daftar dengan tombol back
    }
}
