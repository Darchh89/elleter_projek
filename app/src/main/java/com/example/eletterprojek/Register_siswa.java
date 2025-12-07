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
import androidx.appcompat.widget.Toolbar;

// Impor yang diperlukan untuk TextInputLayout
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_siswa extends AppCompatActivity {

    private EditText etNamaLengkap, etEmail;
    private TextInputLayout tilPassword, tilConfirmPassword;
    private Button btnDaftar;
    private TextView tvMasuk;
    private Toolbar toolbarBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_siswa);

        etNamaLengkap = findViewById(R.id.NamaLengkapSiswa);
        etEmail = findViewById(R.id.EmailSiswa);
        tilPassword = findViewById(R.id.PasswordSiswa);
        tilConfirmPassword = findViewById(R.id.ConfirmPasswordSiswa);
        btnDaftar = findViewById(R.id.ButtonDaftarSiswa);
        tvMasuk = findViewById(R.id.MasukView);
        toolbarBack = findViewById(R.id.toolbarBack);

        toolbarBack.setOnClickListener(v -> {
            finish();
        });

        tvMasuk.setOnClickListener(v -> {
            finish();
        });

        btnDaftar.setOnClickListener(v -> registerSiswa());
    }

    private void registerSiswa() {
        tilConfirmPassword.setError(null);

        String fullname = etNamaLengkap.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        String confirmPassword = tilConfirmPassword.getEditText().getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@gmail.com")) {
            Toast.makeText(this, "Mohon masukan alamat email yang benar", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Password dan konfirmasi password tidak cocok");
            return;
        }

        btnDaftar.setEnabled(false);
        btnDaftar.setText("Mendaftar...");

        RegisterSiswaRequest registerRequest = new RegisterSiswaRequest(fullname, email, password);
        Call<RegisterSiswaResponse> call = ApiClient.getApiService().registerSiswa(registerRequest);

        call.enqueue(new Callback<RegisterSiswaResponse>() {
            @Override
            public void onResponse(Call<RegisterSiswaResponse> call, Response<RegisterSiswaResponse> response) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Register_siswa.this, "Pendaftaran berhasil! Silakan masuk.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Register_siswa.this, login_siswa.class);
                    // Mengirim user_code yang baru dibuat ke halaman login
                    if (response.body().getUserCode() != null) {
                        intent.putExtra("NEW_USER_CODE", response.body().getUserCode());
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Pendaftaran gagal."; // Pesan default
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            JSONObject errorObj = new JSONObject(errorBodyString);
                            if (errorObj.has("message")) {
                                errorMessage = errorObj.getString("message");
                            } else {
                                errorMessage = "Terjadi error yang tidak diketahui.";
                            }
                        } catch (Exception e) {
                            Log.e("RegisterSiswa", "Gagal membaca pesan error", e);
                        }
                    }
                    Toast.makeText(Register_siswa.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterSiswaResponse> call, Throwable t) {
                btnDaftar.setEnabled(true);
                btnDaftar.setText("Daftar");
                Toast.makeText(Register_siswa.this, "Gagal terhubung ke server: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("RegisterSiswaError", "API call failed: ", t);
            }
        });
    }
}
