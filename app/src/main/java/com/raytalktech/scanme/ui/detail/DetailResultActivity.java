package com.raytalktech.scanme.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.raytalktech.scanme.R;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.databinding.ActivityDetailResultBinding;
import com.raytalktech.scanme.databinding.ContentDetailResultBinding;

public class DetailResultActivity extends AppCompatActivity {

//   // public static void launchIntent(Activity caller, BaseResponse.ResultData data) {
//        Intent intent = new Intent(caller, DetailResultActivity.class);
//        mData = data;
//        caller.startActivityForResult(intent, 99);
//    }

    private ActivityDetailResultBinding binding;
    private ContentDetailResultBinding contentBinding;
    //private static BaseResponse.ResultData mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailResultBinding.inflate(getLayoutInflater());
        contentBinding = binding.llBottomSheet;
        setContentView(binding.getRoot());

        showBottomSheet();
    }

    private void showBottomSheet() {
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(contentBinding.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(250);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_COLLAPSED != newState)
                    onBottomSheetGone();
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //binding.llBottomSheet.ivIcon.setImageDrawable(getDrawable(mData.getStatus()));
        //binding.llBottomSheet.tvTitle.setText(mData.getTitle());
       // binding.llBottomSheet.tvMessage.setText(mData.getMessage());
        binding.llBottomSheet.btnAction.setOnClickListener(view -> onBottomSheetGone());
    }

    private void onBottomSheetGone() {
        finish();
    }

    private Drawable getDrawable(String id) {
        Drawable drawable;
        switch (id) {
            case "04":
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_success);
                break;
            case "02":
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_warning);
                break;
            default:
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_failed);
                break;
        }
        return drawable;
    }
}
