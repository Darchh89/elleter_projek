package com.example.eletterprojek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// Impor yang diperlukan untuk TextInputLayout
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class masuk_siswa extends AppCompatActivity {

    // Sesuaikan tipe dan nama variabel dengan XML yang baru
    private EditText etNamaLengkap, etEmail;
    private TextInputLayout tilPassword, tilConfirmPassword;
    private Button btnDaftar;
    private TextView tvMasuk;
    private Toolbar toolbarBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_siswa);

        // Gunakan ID yang sudah diperbaiki dari file layout
        etNamaLengkap = findViewById(R.id.NamaLengkapSiswa);
        etEmail = findViewById(R.id.EmailSiswa);
        tilPassword = findViewById(R.id.PasswordSiswa);
        tilConfirmPassword = findViewById(R.id.ConfirmPasswordSiswa);
        btnDaftar = findViewById(R.id.ButtonDaftarSiswa);
        tvMasuk = findViewById(R.id.MasukView);
        toolbarBack = findViewById(R.id.toolbarBack);

        // --- Logika Tombol Kembali di Toolbar ---
        toolbarBack.setOnClickListener(v -> {
            // Cukup tutup halaman ini untuk kembali ke halaman LoginSiswa
            finish();
        });

        // --- Logika Tombol "Masuk" ---
        tvMasuk.setOnClickListener(v -> {
            // Cukup tutup halaman ini untuk kembali ke halaman LoginSiswa
            finish();
        });

        // --- Logika Tombol "Daftar" ---
        btnDaftar.setOnClickListener(v -> registerSiswa());
    }

    private void registerSiswa() {
        // Hapus pesan error sebelumnya
        tilConfirmPassword.setError(null);

        String fullname = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        // Ambil teks dari dalam TextInputLayout
        String password = tilPassword.getEditText().getText().toString().trim();
        String confirmPassword = tilConfirmPassword.getEditText().getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            // Tampilkan pesan error di bawah kolom konfirmasi password
            tilConfirmPassword.setError("Password dan konfirmasi password tidak cocok");
            return;
        }

        btnDaftar.setEnabled(false);
        btnDaftar.setText("Mendaftar...");

        // API call tetap sama, menggunakan RegisterGuruRequest dengan token null
        RegisterGuruRequest registerRequest = new RegisterGuruRequest(fullname, email, password, null);
        Call<RegisterGuruResponse> call = ApiClient.getApiService().registerGuru(registerRequest);

        call.enqueue(new Callback<RegisterGuruResponse>() {
            @Override
            public void onResponse(Call<RegisterGuruResponse> call, Response<RegisterGuruResponse> response) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(masuk_siswa.this, "Pendaftaran berhasil! Silakan masuk.", Toast.LENGTH_LONG).show();

                    // Kembali ke halaman login setelah berhasil mendaftar
                    Intent intent = new Intent(masuk_siswa.this, login_siswa.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Pendaftaran gagal. Email mungkin sudah terdaftar.";
                    try {
                        if (response.errorBody() != null) {
                            Log.e("RegisterSiswa", "Error Body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        Log.e("RegisterSiswa", "Gagal membaca pesan error", e);
                    }
                    Toast.makeText(masuk_siswa.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterGuruResponse> call, Throwable t) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");
                Toast.makeText(masuk_siswa.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterSiswaError", "API call failed: ", t);
            }
        });
    }
}
