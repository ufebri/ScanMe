package com.raytalktech.scanerme.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.raytalktech.scanerme.config.Constant;
import com.raytalktech.scanerme.data.BaseResponse;
import com.raytalktech.scanerme.databinding.ActivityHomeBinding;
import com.raytalktech.scanerme.ui.adapter.HomeAdapter;
import com.raytalktech.scanerme.ui.scan.ScanActivity;
import com.raytalktech.scanerme.utils.ResponseDataHelper;

public class HomeActivity extends AppCompatActivity {

    static BaseResponse.ResultLogin mData;
    private ActivityHomeBinding binding;

    public static void lunchIntent(Activity caller, BaseResponse.ResultLogin resultLogin) {
        Intent intent = new Intent(caller, HomeActivity.class);
        mData = resultLogin;
        caller.startActivityForResult(intent, Constant.HOME_MENU);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //RecyclerView
        binding.rvHome.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHome.setHasFixedSize(true);
        binding.rvHome.setAdapter(new HomeAdapter(this, ResponseDataHelper.getListIntroduction()));

        //Hello Text
        binding.tvTitle.setText(mData != null ? mData.getMessage() : "Hello User");

        //onClick Button
        binding.btnNext.setOnClickListener(view -> ScanActivity.launchIntent(HomeActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.HOME_MENU && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}