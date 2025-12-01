package com.example.eletterprojek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    private EditText etUserCode, etPassword;
    private MaterialButton btnLogin;
    private TextView textDaftar;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_guru);

        etUserCode = findViewById(R.id.IDGuru);
        etPassword = findViewById(R.id.PasswordLoginG);
        btnLogin = findViewById(R.id.buttonMasuk);
        textDaftar = findViewById(R.id.TextDaftar);

        // --- Menerima dan mengisi User Code dari halaman pendaftaran ---
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USER_CODE")) {
            String userCode = intent.getStringExtra("USER_CODE");
            etUserCode.setText(userCode);
        }
        // --------------------------------------------------------------

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInGuru.this, SignUpGuru.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String userCode = etUserCode.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userCode) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "User code dan password wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(userCode, password);
        Call<LoginResponse> call = ApiClient.getApiService().login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(SignInGuru.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Navigasi ke halaman Beranda
                    Intent intent = new Intent(SignInGuru.this, Beranda.class);
                    startActivity(intent);
                    finish(); // Menutup halaman login agar tidak bisa kembali

                } else {
                    // Tangani error response
                    Toast.makeText(SignInGuru.this, "Login Gagal. Periksa kembali user code dan password Anda.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SignInGuru.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
