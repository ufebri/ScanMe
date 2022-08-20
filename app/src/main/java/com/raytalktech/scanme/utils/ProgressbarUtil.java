package com.raytalktech.scanme.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.annotation.NonNull;

import com.raytalktech.scanme.R;

public class ProgressbarUtil extends Dialog {

    public ProgressbarUtil(@NonNull Context context) {
        super(context, R.style.MyDialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_progress_bar);
        setCancelable(false);
        show();
    }
}
