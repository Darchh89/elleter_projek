package com.example.eletterprojek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class masuk_siswa extends AppCompatActivity {

    private EditText etNamaLengkap, etEmail, etPassword, etConfirmPassword;
    private Button btnDaftar;
    private TextView tvMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_siswa);

        etNamaLengkap = findViewById(R.id.NamaLengkap);
        etEmail = findViewById(R.id.NipGuru); // Pastikan ID ini sesuai dengan layout untuk email
        etPassword = findViewById(R.id.PasswordGuru);
        etConfirmPassword = findViewById(R.id.ConfirmPasswordGuru);
        btnDaftar = findViewById(R.id.ButtonDaftarGuru);
        tvMasuk = findViewById(R.id.daftarView);

        tvMasuk.setOnClickListener(v -> {
            Intent intent = new Intent(masuk_siswa.this, login_siswa.class);
            startActivity(intent);
            finish();
        });

        btnDaftar.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullname = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterSiswaRequest registerSiswaRequest = new RegisterSiswaRequest(fullname, email, password);
        // Menggunakan metode registerSiswa yang benar dari ApiService
        Call<RegisterSiswaResponse> call = ApiClient.getApiService().registerSiswa(registerSiswaRequest);

        call.enqueue(new Callback<RegisterSiswaResponse>() {
            @Override
            public void onResponse(Call<RegisterSiswaResponse> call, Response<RegisterSiswaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mengambil pesan dari response body
                    String message = response.body().getMessage();
                    Toast.makeText(masuk_siswa.this, "Pendaftaran berhasil: " + message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(masuk_siswa.this, login_siswa.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Menampilkan pesan error jika registrasi gagal
                    Toast.makeText(masuk_siswa.this, "Pendaftaran gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterSiswaResponse> call, Throwable t) {
                Toast.makeText(masuk_siswa.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterError", "API call failed: ", t);
            }
        });
    }
}
