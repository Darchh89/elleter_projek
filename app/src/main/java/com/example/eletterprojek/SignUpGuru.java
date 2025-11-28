package com.example.eletterprojek;

import android.annotation.SuppressLint;
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

public class SignUpGuru extends AppCompatActivity {

    private EditText etNamaLengkap, etEmail, etPassword, etToken;
    private Button btnDaftar;
    private TextView tvMasuk;

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
        tvMasuk = findViewById(R.id.DaftarView);

        btnDaftar.setOnClickListener(v -> handleRegisterGuru());

        tvMasuk.setOnClickListener(v -> {
            finish();
        });
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

        Call<RegisterGuruResponse> call = ApiClient.getApiService().registerGuru(request);

        call.enqueue(new Callback<RegisterGuruResponse>() {
            @Override
            public void onResponse(Call<RegisterGuruResponse> call, Response<RegisterGuruResponse> response) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");

                if (response.isSuccessful() && response.body() != null) {
                    RegisterGuruResponse registerResponse = response.body();
                    Toast.makeText(SignUpGuru.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpGuru.this, SignInGuru.class);
                    intent.putExtra("USER_CODE", registerResponse.getUserCode());
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Registrasi Gagal (Kode: " + response.code() + ")";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (Exception e) {
                            Log.e("SignUpGuru", "Error parsing error body", e);
                        }
                    }
                    Toast.makeText(SignUpGuru.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterGuruResponse> call, Throwable t) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");
                Log.e("RegisterGuruError", "onFailure: " + t.getMessage());
                Toast.makeText(SignUpGuru.this, "Gagal terhubung ke server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
