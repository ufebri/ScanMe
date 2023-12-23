package com.raytalktech.scanerme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.raytalktech.scanerme.databinding.ActivitySplashScreenBinding;
import com.raytalktech.scanerme.ui.home.HomeActivity;
import com.raytalktech.scanerme.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTextVersion.setText(String.format("v%s", BuildConfig.VERSION_NAME));

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }, 5000);
    }
}