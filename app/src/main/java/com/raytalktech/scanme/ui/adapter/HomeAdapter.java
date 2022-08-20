package com.raytalktech.scanme.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raytalktech.scanme.R;
import com.raytalktech.scanme.config.Constant;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.ui.adapter.viewholder.TextViewHolder;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseResponse.GeneralData> mData;

    public HomeAdapter(Context context, List<BaseResponse.GeneralData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constant.VIEW_TYPE_TEXT_TITLE:
            case Constant.VIEW_TYPE_TEXT_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_type, parent, false);
                return new TextViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Constant.VIEW_TYPE_TEXT_TITLE:
                TextViewHolder vhTitle = (TextViewHolder) holder;
                vhTitle.setTitleText(context, mData.get(position).getTitle());
                break;
            case Constant.VIEW_TYPE_TEXT_NORMAL:
                TextViewHolder vhText = (TextViewHolder) holder;
                vhText.setNormalText(mData.get(position).getMessage());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
