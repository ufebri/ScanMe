package com.raytalktech.scanme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.raytalktech.scanme.databinding.ActivitySplashScreenBinding;
import com.raytalktech.scanme.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTextVersion.setText(String.format("v%s", BuildConfig.VERSION_NAME));

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 5000);
    }
}