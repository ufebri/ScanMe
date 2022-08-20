package com.raytalktech.scanme.ui.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.util.concurrent.ListenableFuture;
import com.raytalktech.scanme.R;
import com.raytalktech.scanme.config.AppConfig;
import com.raytalktech.scanme.config.Constant;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.databinding.ActivityScanBinding;
import com.raytalktech.scanme.databinding.ContentDetailResultBinding;
import com.raytalktech.scanme.utils.PermissionHelper;
import com.raytalktech.scanme.utils.QRCodeFoundListener;
import com.raytalktech.scanme.utils.QRCodeImageAnalyzer;
import com.raytalktech.scanme.viewmodel.ViewModelFactory;

import java.util.concurrent.ExecutionException;

public class ScanActivity extends AppCompatActivity {

    private ActivityScanBinding binding;
    private ContentDetailResultBinding bottomSheetBinding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageAnalysis imageAnalysis;
    private ScanViewModel viewModel;
    static String token;
    private PermissionHelper permissionHelper;

    public static void launchIntent(Activity caller, String accessToken) {
        Intent intent = new Intent(caller, ScanActivity.class);
        token = accessToken;
        caller.startActivityForResult(intent, Constant.SCAN_QR_MENU);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanBinding.inflate(getLayoutInflater());
        bottomSheetBinding = binding.llBottomSheet;
        setContentView(binding.getRoot());

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        permissionHelper = new PermissionHelper(this, Manifest.permission.CAMERA);

        if (permissionHelper.isPermissionAllowing())
            startCamera();

        imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ScanViewModel.class);
        viewModel.setToken(token);
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraPreview(ProcessCameraProvider cameraProvider) {
        binding.previewCamera.setVisibility(View.VISIBLE);
        binding.pbScan.setVisibility(View.GONE);
        binding.previewCamera.setImplementationMode(PreviewView.ImplementationMode.PERFORMANCE);

        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.previewCamera.getSurfaceProvider());

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {
                binding.previewCamera.setVisibility(View.GONE);
                processResult(_qrCode);
            }

            @Override
            public void qrCodeNotFound() {

            }
        }));

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }

    private void processResult(String code) {
        binding.pbScan.setVisibility(View.VISIBLE);
        viewModel.setCodeResult(code);

        viewModel.getResultCheckIn().observe(this, result -> {
            try {
                if (result.body != null) {
                    switch (result.status) {
                        case SUCCESS:
                            showBottomSheet(result.body);
                            break;
                    }
                }
            } catch (Exception e) {
                Log.d("TAG", "processResult: " + e.getLocalizedMessage());
            }
        });
    }

    private void showBottomSheet(BaseResponse.ResultCheckIn mData) {
        binding.pbScan.setVisibility(View.GONE);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBinding.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        bottomSheetBinding.getRoot().setVisibility(View.VISIBLE);

        bottomSheetBinding.ivIcon.setImageDrawable(getDrawable(mData.getStatus()));
        bottomSheetBinding.tvTitle.setText(mData.getMessage());
        bottomSheetBinding.tvMessage.setText(mData.getMessage());
        bottomSheetBinding.btnAction.setOnClickListener(view -> onBottomSheetGone());
    }

    private void onBottomSheetGone() {
        binding.previewCamera.setVisibility(View.VISIBLE);
        bottomSheetBinding.getRoot().setVisibility(View.GONE);
    }

    private Drawable getDrawable(String id) {
        Drawable drawable;
        switch (id) {
            case "200":
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_success);
                break;
            case "403":
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_warning);
                break;
            default:
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_failed);
                break;
        }
        return drawable;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.HOME_MENU && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.PERMISSION_CAMERA:
                if (permissionHelper.isPermissionGranted(grantResults.length, grantResults[0], permissions[0]))
                    startCamera();
                break;
            default:
                permissionHelper.getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_03);
                break;
        }
    }
}