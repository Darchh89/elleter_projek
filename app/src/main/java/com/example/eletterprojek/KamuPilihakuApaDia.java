package com.example.eletterprojek; // Pastikan nama package ini sesuai dengan proyek Anda

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class KamuPilihakuApaDia extends AppCompatActivity {

    private MaterialButton buttonGuru;
    private MaterialButton buttonSiswa;
    private MaterialButton buttonPembina;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamu_pilihaku_apa_dia);

        buttonGuru = findViewById(R.id.btnGuru);
        buttonSiswa = findViewById(R.id.btnSiswa);



        buttonGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninGuruPage();
            }
        });
        buttonSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninSiswaPage();
            }
        });



    }
    private void openSigninGuruPage() {

        Intent intent = new Intent(KamuPilihakuApaDia.this, SignUpGuru.class);
        startActivity(intent);
    }
    private void openSigninSiswaPage() {

        Intent intent = new Intent(KamuPilihakuApaDia.this, masuk_siswa.class);
        startActivity(intent);
    }


}
