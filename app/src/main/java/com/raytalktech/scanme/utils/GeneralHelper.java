package com.raytalktech.scanme.utils;


import android.content.Context;
import android.widget.Toast;

public class GeneralHelper {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
