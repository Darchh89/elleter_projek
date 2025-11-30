package com.example.eletterprojek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_siswa extends AppCompatActivity {

    private TextView tvDaftar;
    private EditText etIdSiswa;
    private TextInputEditText etPasswordSiswa;
    private Button btnMasuk;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_siswa);

        tvDaftar = findViewById(R.id.TextDaftar);
        etIdSiswa = findViewById(R.id.IDGuru); // ID from layout is IDGuru
        etPasswordSiswa = findViewById(R.id.PasswordLoginG); // ID from layout is PasswordLoginG
        btnMasuk = findViewById(R.id.buttonMasuk2);

        apiService = ApiClient.getClient().create(ApiService.class);

        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_siswa.this, masuk_siswa.class);
                startActivity(intent);
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginSiswa();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleLoginSiswa() {
        String userCode = etIdSiswa.getText().toString().trim();
        String password = etPasswordSiswa.getText().toString().trim();

        if (userCode.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "ID dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate that user_code starts with "S"
        if (!userCode.toUpperCase().startsWith("S")) {
            Toast.makeText(this, "Mohon masukan ID yang benar", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(userCode, password);
        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(login_siswa.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login_siswa.this, Beranda.class);
                    // You can pass user data to Beranda activity if needed
                    // intent.putExtra("USER_DATA", response.body());
                    startActivity(intent);
                    finish(); // Close login activity
                } else {
                    // Handle different error responses
                    String errorMessage = "Login gagal. Periksa kembali ID dan Password Anda.";
                    if (response.code() == 404) {
                        errorMessage = "User code tidak ditemukan.";
                    } else if (response.code() == 401) {
                        errorMessage = "Password salah.";
                    }
                    Toast.makeText(login_siswa.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(login_siswa.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
