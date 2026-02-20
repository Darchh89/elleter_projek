package com.example.eletterprojek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Beranda extends AppCompatActivity {

    LinearLayout navBeranda, navPanduan, navRiwayat, navProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        navBeranda = findViewById(R.id.nav_beranda);
        navPanduan = findViewById(R.id.nav_panduan);
        navRiwayat = findViewById(R.id.nav_riwayat);
        navProfil  = findViewById(R.id.nav_profil);

        // Beranda sudah aktif, tidak perlu pindah halaman
        navBeranda.setOnClickListener(v -> {
            // sudah di halaman ini
        });

//        navPanduan.setOnClickListener(v -> {
//            Intent intent = new Intent(Beranda.this, Panduan.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//        });
//
//        navRiwayat.setOnClickListener(v -> {
//            Intent intent = new Intent(Beranda.this, Riwayat.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//        });
//
//        navProfil.setOnClickListener(v -> {
//            Intent intent = new Intent(Beranda.this, Profil.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//        });
    }
}