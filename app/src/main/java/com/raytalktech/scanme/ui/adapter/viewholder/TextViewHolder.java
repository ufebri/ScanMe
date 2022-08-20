package com.raytalktech.scanme.ui.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raytalktech.scanme.R;

public class TextViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvText;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);

        tvText = itemView.findViewById(R.id.tv_text);
    }

    public void setTitleText(Context context, String text) {
        tvText.setTextAppearance(context, R.style.TextAppearance_AppCompat_Title);
        tvText.setText(text);
    }

    public void setNormalText(String text) {
        tvText.setText(text);
    }
}
