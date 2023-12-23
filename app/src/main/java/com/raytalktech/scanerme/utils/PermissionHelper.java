package com.raytalktech.scanerme.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.raytalktech.scanerme.config.AppConfig;
import com.raytalktech.scanerme.config.Constant;

public class PermissionHelper extends Activity {

    private boolean isAllowing = false;
    private final Context context;
    private final String TAG = getClass().getSimpleName();
    private final String permissionItem;

    public PermissionHelper(Context context, String permissionItem) {
        this.context = context;
        this.permissionItem = permissionItem;
    }

    public void setAllowing(boolean allowing) {
        isAllowing = allowing;
    }

    public boolean isPermissionAllowing() {
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
                ActivityCompat.requestPermissions((FragmentActivity) context, new String[]{permissionItem}, Constant.PERMISSION_CAMERA);
                break;
            default:
                getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_03);
                break;
        }
    }

    public boolean isPermissionGranted(int lengthResult, int isGranted, String permission) {
        boolean isAccepted = lengthResult == 1 && isGranted == PackageManager.PERMISSION_GRANTED;
        if (!isAccepted) getGeneralErrorMessagePermission(AppConfig.GeneralResponseMessage.RC_05);
        return isAccepted;
    }

    public void getGeneralErrorMessagePermission(String message) {
        setAllowing(false);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
