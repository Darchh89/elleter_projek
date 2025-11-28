package com.example.eletterprojek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class KamuPilihakuApaDia extends AppCompatActivity {

    private MaterialButton btnGuru;
    private MaterialButton btnSiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamu_pilihaku_apa_dia);
        btnGuru = findViewById(R.id.btnGuru);
        btnSiswa = findViewById(R.id.btnSiswa);

        btnGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KamuPilihakuApaDia.this, SignInGuru.class);
                startActivity(intent);
            }
        });

        btnSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KamuPilihakuApaDia.this, login_siswa.class);
                startActivity(intent);
            }
        });
    }
}
