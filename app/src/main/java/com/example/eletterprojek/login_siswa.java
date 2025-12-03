package com.example.eletterprojek;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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

public class login_siswa extends AppCompatActivity {

    private EditText etIdSiswa;
    private TextInputLayout tilPassword;
    private Button btnMasuk;
    private TextView tvDaftar, tvLupaPassword;
    private Toolbar toolbarBack;

    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_siswa);

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        etIdSiswa = findViewById(R.id.IDSiswa);
        tilPassword = findViewById(R.id.PasswordLoginSiswaLayout);
        btnMasuk = findViewById(R.id.buttonMasuk2);
        tvDaftar = findViewById(R.id.TextDaftar);
        toolbarBack = findViewById(R.id.toolbarBack2);
        tvLupaPassword = findViewById(R.id.textView12);


        toolbarBack.setOnClickListener(v -> {
            Intent intent = new Intent(login_siswa.this, KamuPilihakuApaDia.class);
            startActivity(intent);
            finish();
        });

        btnMasuk.setOnClickListener(v -> handleLogin());

        tvDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(login_siswa.this, Register_siswa.class);
            startActivity(intent);
        });

        tvLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_popup_lupa_sandi, null);

                final Dialog popupDialog = new Dialog(login_siswa.this);
                popupDialog.setContentView(popupView);

                if (popupDialog.getWindow() != null) {
                    popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final float scale = getResources().getDisplayMetrics().density;
                int width = (int) (282 * scale + 0.5f);

                popupDialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

                popupDialog.show();

            }
        });

    }

    private void handleLogin() {
        String userCode = etIdSiswa.getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();

        if (userCode.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "ID dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnMasuk.setEnabled(false);
        btnMasuk.setText("Loading...");

        LoginRequest loginRequest = new LoginRequest(userCode, password);
        Call<LoginResponse> call = ApiClient.getApiService().login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnMasuk.setEnabled(true);
                btnMasuk.setText("Masuk");

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(login_siswa.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("is_logged_in", true);
                    editor.putString("user_token", loginResponse.getToken()); // Simpan token jika ada
                    editor.apply();

                    Intent intent = new Intent(login_siswa.this, Beranda.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    String errorMessage = "Login Gagal. Periksa kembali ID dan password Anda.";
                    if (response.errorBody() != null) {
                        try {
                            Log.e("LoginSiswa", "Error Body: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e("LoginSiswa", "Gagal membaca pesan error", e);
                        }
                    }
                    Toast.makeText(login_siswa.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnMasuk.setEnabled(true);
                btnMasuk.setText("Masuk");
                Log.e("LoginSiswaError", "onFailure: " + t.getMessage());
                Toast.makeText(login_siswa.this, "Gagal terhubung ke server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
