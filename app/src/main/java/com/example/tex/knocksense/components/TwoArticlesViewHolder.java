package com.example.tex.knocksense.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tex.knocksense.R;

/**
 * Created by anuragdwivedi on 06/09/17.
 */

public class TwoArticlesViewHolder extends RecyclerView.ViewHolder{
    public final View containerView;
    public final View leftArticleView;
    public final View rightArticleView;

    public final TextView leftViewTitle;
   // public final TextView leftViewAuthor;
    //public final TextView leftViewDate;
    public final ImageView leftImageView;
    public Article leftViewArticle;

    public final TextView rightViewTitle;
    //public final TextView rightViewAuthor;
    //public final TextView rightViewDate;
    public final ImageView rightImageView;
    public Article rightViewArticle;

    public TwoArticlesViewHolder(View view){
        super(view);
        containerView=view;
        leftArticleView=view.findViewById(R.id.article_two_item_row_left_relative_layout);
        rightArticleView=view.findViewById(R.id.article_two_item_row_right_relative_layout);

        leftViewTitle=(TextView)view.findViewById(R.id.two_item_row_left_content);
       /* leftViewAuthor=(TextView)view.findViewById(R.id.two_item_row_left_author);
        leftViewDate=(TextView)view.findViewById(R.id.two_item_row_left_date);
       */
       leftImageView=(ImageView)view.findViewById(R.id.two_item_row_left_image);


       rightViewTitle=(TextView)view.findViewById(R.id.two_item_row_right_content);
       /*  rightViewAuthor=(TextView)view.findViewById(R.id.two_item_row_right_author);

       rightViewDate=(TextView)view.findViewById(R.id.two_item_row_right_date);
       */
       rightImageView=(ImageView)view.findViewById(R.id.two_item_row_right_image);

    }
}