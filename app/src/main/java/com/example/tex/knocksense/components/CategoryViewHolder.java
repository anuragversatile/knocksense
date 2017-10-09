package com.example.tex.knocksense.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tex.knocksense.R;

/**
 * Created by anuragdwivedi on 06/09/17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    public final View mView;
    public final TextView category;
    public Article mItem;
    public CategoryViewHolder(View view){
        super(view);
        mView=view;
        category=(TextView)view.findViewById(R.id.category_article_textView);
    }
}
