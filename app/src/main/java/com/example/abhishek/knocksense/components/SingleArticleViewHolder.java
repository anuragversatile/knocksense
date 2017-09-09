package com.example.abhishek.knocksense.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.knocksense.R;

/**
 * Created by anuragdwivedi on 06/09/17.
 */

public class SingleArticleViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView title;
    public final TextView author;
    public final TextView date;
    public final ImageView featuredImage;
    public Article mItem;

    public SingleArticleViewHolder(View view) {
        super(view);
        mView = view;
        title = (TextView) view.findViewById(R.id.article_item_row_title);
        author = (TextView) view.findViewById(R.id.article_item_row_author);
        date = (TextView) view.findViewById(R.id.article_item_row_date);
        featuredImage=(ImageView)view.findViewById(R.id.featuredImage);
    }
}