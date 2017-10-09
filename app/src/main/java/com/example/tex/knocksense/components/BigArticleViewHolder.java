package com.example.tex.knocksense.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tex.knocksense.R;

/**
 * Created by anuragdwivedi on 06/09/17.
 */

public class BigArticleViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView title;
    public final TextView author;
    public final TextView date;
    public Article mItem;
    public final ImageView big_item_row_image;
    public BigArticleViewHolder(View view) {
        super(view);
        mView = view;
        title = (TextView) view.findViewById(R.id.big_item_row_title);
        author = (TextView) view.findViewById(R.id.big_item_row_author);
        date = (TextView) view.findViewById(R.id.big_item_row_date);
        big_item_row_image=(ImageView)view.findViewById(R.id.big_item_row_image);

    }
}
