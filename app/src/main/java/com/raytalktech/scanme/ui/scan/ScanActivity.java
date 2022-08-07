package com.raytalktech.scanme.ui.scan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.util.concurrent.ListenableFuture;
import com.raytalktech.scanme.R;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.databinding.ActivityScanBinding;
import com.raytalktech.scanme.databinding.ContentDetailResultBinding;
import com.raytalktech.scanme.ui.detail.DetailResultActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanBinding.inflate(getLayoutInflater());
        bottomSheetBinding = binding.llBottomSheet;
        setContentView(binding.getRoot());

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        PermissionHelper permissionHelper = new PermissionHelper(this);

        if (permissionHelper.isPermissionAllowing(Manifest.permission.CAMERA))
            startCamera();

        imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ScanViewModel.class);
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
                processResult(_qrCode);
            }

            @Override
            public void qrCodeNotFound() {

            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
    }

    private void processResult(String url) {
        binding.pbScan.setVisibility(View.VISIBLE);
        viewModel.checkURLValidation(url);
        showBottomSheet(viewModel.getData());
    }

    private void showBottomSheet(BaseResponse.ResultData mData) {
        binding.pbScan.setVisibility(View.GONE);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBinding.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        bottomSheetBinding.getRoot().setVisibility(View.VISIBLE);

        bottomSheetBinding.ivIcon.setImageDrawable(getDrawable(mData.getStatus()));
        bottomSheetBinding.tvTitle.setText(mData.getTitle());
        bottomSheetBinding.tvMessage.setText(mData.getMessage());
        bottomSheetBinding.btnAction.setOnClickListener(view -> onBottomSheetGone());
    }

    private void onBottomSheetGone() {
        bottomSheetBinding.getRoot().setVisibility(View.GONE);
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