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

// Impor yang diperlukan untuk TextInputLayout
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.Toolbar;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpGuru extends AppCompatActivity {

    // Sesuaikan variabel dengan komponen di XML
    private EditText etNamaLengkap, etEmail, etToken;
    private TextInputLayout tilPassword, tilConfirmPassword;
    private Button btnDaftar;
    private TextView tvMasuk;
    private Toolbar toolbarBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_guru);

        etNamaLengkap = findViewById(R.id.NamaLengkap);
        etEmail = findViewById(R.id.EmailGuru);
        etToken = findViewById(R.id.Token);
        btnDaftar = findViewById(R.id.ButtonDaftarGuru);
        tvMasuk = findViewById(R.id.DaftarView);
        toolbarBack = findViewById(R.id.toolbarBack);

        tilPassword = findViewById(R.id.PasswordGuru);
        tilConfirmPassword = findViewById(R.id.ConfirmPasswordGuru);

        toolbarBack.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpGuru.this, KamuPilihakuApaDia.class);
            startActivity(intent);
            finish();
        });

        btnDaftar.setOnClickListener(v -> handleRegisterGuru());

        tvMasuk.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpGuru.this, SignInGuru.class);
            startActivity(intent);
        });
    }

    private void handleRegisterGuru() {
        tilConfirmPassword.setError(null);

        String fullname = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        String confirmPassword = tilConfirmPassword.getEditText().getText().toString().trim();
        String token = etToken.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || token.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Password tidak cocok!"); // Tampilkan error di bawah kolom
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
                    
                    String newUserCode = registerResponse.getUserCode();

                    Intent intent = new Intent(SignUpGuru.this, SignInGuru.class);
                    if (newUserCode != null && !newUserCode.isEmpty()) {
                        intent.putExtra("NEW_USER_CODE", newUserCode);
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Registrasi Gagal (Kode: " + response.code() + ")";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (Exception e) {
                            Log.e("SignUpGuru", "Gagal membaca pesan error", e);
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
