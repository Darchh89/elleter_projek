package com.example.eletterprojek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpGuru extends AppCompatActivity {

    private EditText etNamaLengkap, etEmail, etPassword, etToken;
    private Button btnDaftar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_guru);

        etNamaLengkap = findViewById(R.id.NamaLengkap);
        etEmail = findViewById(R.id.EmailGuru);
        etPassword = findViewById(R.id.PasswordGuru);
        etToken = findViewById(R.id.Token);
        btnDaftar = findViewById(R.id.ButtonDaftarGuru);

        btnDaftar.setOnClickListener(v -> handleRegisterGuru());
    }

    private void handleRegisterGuru() {
        String fullname = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String token = etToken.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || token.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnDaftar.setEnabled(false);
        btnDaftar.setText("Mendaftar...");

        RegisterGuruRequest request = new RegisterGuruRequest(fullname, email, password, token);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<AuthResponse> call = apiService.registerGuru(request);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    Toast.makeText(SignUpGuru.this, authResponse.getMessage(), Toast.LENGTH_LONG).show();

                    if (authResponse.isSuccess()) {
                        // Jika sukses, arahkan ke halaman login
                        Intent intent = new Intent(SignUpGuru.this, SignInGuru.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    // Tangani error, misalnya token salah
                    String errorMessage = "Registrasi Gagal (Kode: " + response.code() + ")";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (Exception e) {}
                    }
                    Toast.makeText(SignUpGuru.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");
                Log.e("RegisterGuruError", "onFailure: " + t.getMessage());
                Toast.makeText(SignUpGuru.this, "Gagal terhubung ke server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
