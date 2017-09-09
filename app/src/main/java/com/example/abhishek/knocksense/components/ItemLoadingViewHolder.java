package com.example.abhishek.knocksense.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.abhishek.knocksense.R;

/**
 * Created by Abhishek on 09-09-2017.
 */

public class ItemLoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public ItemLoadingViewHolder(View view) {
        super(view);
        progressBar = (ProgressBar) view.findViewById(R.id.item_loading_progressBar);
    }
}
