package com.raytalktech.scanme.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.raytalktech.scanme.config.Constant;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.databinding.ActivityHomeBinding;
import com.raytalktech.scanme.ui.adapter.HomeAdapter;
import com.raytalktech.scanme.ui.scan.ScanActivity;
import com.raytalktech.scanme.utils.ResponseDataHelper;

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
        binding.tvTitle.setText(mData.getMessage());

        //onClick Button
        binding.btnNext.setOnClickListener(view -> ScanActivity.launchIntent(HomeActivity.this, mData.getAccess_token()));
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