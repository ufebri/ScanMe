package com.raytalktech.scanme.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.raytalktech.scanme.config.AppConfig;

public class PermissionHelper extends Activity {

    private boolean isAllowing = false;
    private final Context context;
    private final String TAG = getClass().getSimpleName();

    public PermissionHelper(Context context) {
        this.context = context;
    }

    public void setAllowing(boolean allowing) {
        isAllowing = allowing;
    }

    public static class PERMISSION_CONSTANT_CODE {
        public static final int CAMERA = 0;
    }

    public boolean isPermissionAllowing(String permissionItem) {
        if (ActivityCompat.checkSelfPermission(context, permissionItem) == PackageManager.PERMISSION_GRANTED) {
            //Added Check Permission Item
            switch (permissionItem) {
                case Manifest.permission.CAMERA:
                    setAllowing(true);
                    break;
                default:
                    getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_03);
                    break;
            }
        } else {
            try {
                if (ActivityCompat.shouldShowRequestPermissionRationale((FragmentActivity) context, permissionItem)) {
                    requestPermission(context, permissionItem);
                } else {
                    requestPermission(context, permissionItem);
                }
            } catch (Exception e) {
                requestPermission(context, permissionItem);
                Log.d(TAG, "isPermissionAllowing: " + e.getLocalizedMessage());
            }
        }
        return isAllowing;
    }

    private void requestPermission(Context context, String permissionItem) {
        //Added Rationale Request Permission
        switch (permissionItem) {
            case Manifest.permission.CAMERA:
                ActivityCompat.requestPermissions((FragmentActivity) context, new String[]{permissionItem}, PERMISSION_CONSTANT_CODE.CAMERA);
                break;
            default:
                getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_03);
                break;
        }
    }

    private boolean isPermissionGranted(int lengthResult, int isGranted) {
        boolean isAccepted = lengthResult == 1 && isGranted == PackageManager.PERMISSION_GRANTED;
        if (!isAccepted) getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_05);
        return isAccepted;
    }

    private void getGeneralErrorMessagePermission(String message) {
        setAllowing(false);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CONSTANT_CODE.CAMERA:
                setAllowing(isPermissionGranted(grantResults.length, grantResults[0]));
                break;
            default:
                getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_03);
                break;
        }
    }
}
