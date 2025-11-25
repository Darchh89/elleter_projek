plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.eletterprojek"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.eletterprojek"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // 1. Deklarasikan variabel untuk versi di sini
    val retrofitVersion = "2.11.0" // Versi Retrofit yang lebih baru dan stabil
    val okhttpVersion = "4.12.0"   // Versi OkHttp yang cocok dengan Retrofit 2.11.0

    // 2. Gunakan variabel tersebut di dependensi Anda
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion") // Sebaiknya tambahkan ini juga
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion") // Sekarang ${okhttpVersion} sudah dikenali

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.games)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
