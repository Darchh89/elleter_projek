package com.example.eletterprojek;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class kode_otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kode_otp);

        EditText otpInput = findViewById(R.id.IDSiswa);
        Button konfirmasiButton = findViewById(R.id.buttonMasuk4);

        konfirmasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpInput.getText().toString().trim();

                if (TextUtils.isEmpty(otp)) {
                    otpInput.setError("Kode OTP tidak boleh kosong");
                    return;
                }

                if (otp.length() != 6) {
                    otpInput.setError("Harap masukan 6 digit kode OTP");
                    return;
                }

                // Jika validasi berhasil, tampilkan dialog
                showPopupDialog();
            }
        });
    }

    private void showPopupDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_popup_lupa_sandi, null);

        final Dialog popupDialog = new Dialog(kode_otp.this);
        popupDialog.setContentView(popupView);

        if (popupDialog.getWindow() != null) {
            popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // Set background dim like in themes.xml
            popupDialog.getWindow().setDimAmount(0.6f); // 60% dim
            popupDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        final float scale = getResources().getDisplayMetrics().density;
        int width = (int) (282 * scale + 0.5f);

        if (popupDialog.getWindow() != null) {
            popupDialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        popupDialog.show();
    }
}
