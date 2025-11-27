package com.example.eletterprojek;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiClient {


    private static final String BASE_URL = "http://192.168.85.117:3000/api/";

    /*
     * Jika backend berjalan di komputer lokal dan diuji dengan HP Fisik:
     * 1. Pastikan HP & komputer terhubung ke jaringan WiFi yang sama.
     * 2. Cari tahu IP Komputer Anda (misal: di Windows, buka cmd dan ketik 'ipconfig').
     * 3. Ganti '192.168.1.10' dengan IP komputer Anda.
     */
    // private static final String BASE_URL = "http://192.168.1.10:3000/api/";
    // ---------------------------------------------

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // Jika instance Retrofit belum pernah dibuat, maka buat yang baru.
        if (retrofit == null) {
            // 1. Buat Logging Interceptor untuk menampilkan log request dan response di Logcat.
            // Ini sangat penting untuk debugging.
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 2. Konfigurasi OkHttpClient untuk menambahkan interceptor dan mengatur timeout.
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS) // Waktu tunggu untuk terhubung
                    .readTimeout(30, TimeUnit.SECONDS)    // Waktu tunggu untuk membaca data
                    .writeTimeout(30, TimeUnit.SECONDS)   // Waktu tunggu untuk menulis data
                    .build();

            // 3. Bangun instance Retrofit.
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Mengatur URL dasar untuk semua request.
                    .client(client) // Menggunakan OkHttpClient yang sudah dikonfigurasi.
                    .addConverterFactory(GsonConverterFactory.create()) // Mengatur Gson sebagai JSON converter.
                    .build();
        }
        // Kembalikan instance Retrofit yang sudah ada atau yang baru dibuat.
        return retrofit;
    }
}
