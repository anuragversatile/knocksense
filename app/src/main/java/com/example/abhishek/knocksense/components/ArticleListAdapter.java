package com.example.abhishek.knocksense.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.knocksense.R;

import java.util.List;

/**
 * Created by Abhishek on 11-08-2017.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private List<Article> articleList;

    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_view_item_row, parent, false);

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleListAdapter.ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        //---not sure about format yet
        holder.articleImage.setImageURI(article.getFeaturedImage());
        //---
        holder.articleTitle.setText(article.getTitle());
        holder.articleAuthor.setText(article.getAuthor());
        holder.articlePublishedDate.setText(article.getDate());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        private ImageView articleImage;
        private TextView articleTitle, articleAuthor, articlePublishedDate;

        public ArticleViewHolder(View view) {
            super(view);
            articleImage = (ImageView) view.findViewById(R.id.list_view_item_row_image);
            articleTitle = (TextView) view.findViewById(R.id.list_view_item_row_title);
            articleAuthor = (TextView) view.findViewById(R.id.list_view_item_row_author);
            articlePublishedDate = (TextView) view.findViewById(R.id.list_view_item_row_date);
        }
    }
}
