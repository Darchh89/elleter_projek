package com.example.eletterprojek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        etEmail = findViewById(R.id.NipGuru); // Note: ID from layout seems incorrect, but using it as is.
        etPassword = findViewById(R.id.PasswordGuru);
        etConfirmPassword = findViewById(R.id.ConfirmPasswordGuru);
        btnDaftar = findViewById(R.id.ButtonDaftarGuru);
        tvMasuk = findViewById(R.id.daftarView);

        tvMasuk.setOnClickListener(v -> {
            // Finish this activity to go back to the previous one (login screen)
            finish();
        });

        btnDaftar.setOnClickListener(v -> registerSiswa());
    }

    private void registerSiswa() {
        String fullname = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Nama Lengkap, Email, dan Password harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
            return;
        }

        // For student registration, token is null, as per your Node.js code.
        RegisterGuruRequest registerRequest = new RegisterGuruRequest(fullname, email, password, null);

        // The server uses the same endpoint for both students and teachers.
        Call<RegisterGuruResponse> call = ApiClient.getApiService().registerGuru(registerRequest);

        call.enqueue(new Callback<RegisterGuruResponse>() {
            @Override
            public void onResponse(Call<RegisterGuruResponse> call, Response<RegisterGuruResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterGuruResponse registerResponse = response.body();
                    Toast.makeText(masuk_siswa.this, "Pendaftaran berhasil!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(masuk_siswa.this, login_siswa.class);
                    // Pass the new user_code to the login screen
                    intent.putExtra("USER_CODE", registerResponse.getUserCode());
                    startActivity(intent);
                    finish(); // Close the registration activity
                } else {
                    Toast.makeText(masuk_siswa.this, "Pendaftaran gagal. Email mungkin sudah terdaftar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterGuruResponse> call, Throwable t) {
                Toast.makeText(masuk_siswa.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterSiswaError", "API call failed: ", t);
            }
        });
    }
}
