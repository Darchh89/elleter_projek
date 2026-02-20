package com.example.eletterprojek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Panduan extends AppCompatActivity {

    // Accordion views
    LinearLayout btnDispensasi, contentDispensasi;
    LinearLayout btnIzinMasuk, contentIzinMasuk;
    LinearLayout btnIzinKeluar, contentIzinKeluar;

    // State accordion
    boolean isDispensasiOpen = false;
    boolean isIzinMasukOpen  = false;
    boolean isIzinKeluarOpen = false;

    // Navbar
    LinearLayout navBeranda, navPanduan, navRiwayat, navProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panduan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Bind accordion
        btnDispensasi    = findViewById(R.id.btn_dispensasi);
        contentDispensasi = findViewById(R.id.content_dispensasi);
        btnIzinMasuk     = findViewById(R.id.btn_izinmasuk);
        contentIzinMasuk  = findViewById(R.id.content_izinmasuk);
        btnIzinKeluar    = findViewById(R.id.btn_izinkeluar);
        contentIzinKeluar = findViewById(R.id.content_izinkeluar);

        // Accordion click listeners
        btnDispensasi.setOnClickListener(v -> toggleAccordion(contentDispensasi, isDispensasiOpen = !isDispensasiOpen));
        btnIzinMasuk.setOnClickListener(v  -> toggleAccordion(contentIzinMasuk,  isIzinMasukOpen  = !isIzinMasukOpen));
        btnIzinKeluar.setOnClickListener(v -> toggleAccordion(contentIzinKeluar, isIzinKeluarOpen = !isIzinKeluarOpen));

        // Bind navbar
        navBeranda = findViewById(R.id.nav_beranda);
        navPanduan = findViewById(R.id.nav_panduan);
        navRiwayat = findViewById(R.id.nav_riwayat);
        navProfil  = findViewById(R.id.nav_profil);

        navBeranda.setOnClickListener(v -> {
            startActivity(new Intent(Panduan.this, Beranda.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        navPanduan.setOnClickListener(v -> { /* sudah di halaman ini */ });

//        navRiwayat.setOnClickListener(v -> {
//            startActivity(new Intent(Panduan.this, Riwayat.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
//        });
//
//        navProfil.setOnClickListener(v -> {
//            startActivity(new Intent(Panduan.this, Profil.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
//        });
    }

    private void toggleAccordion(LinearLayout content, boolean isOpen) {
        content.setVisibility(isOpen ? View.VISIBLE : View.GONE);
    }
}