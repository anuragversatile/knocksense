package com.example.abhishek.knocksense;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.knocksense.HomeFragment.OnListFragmentInteractionListener;
import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.BigArticleViewHolder;
import com.example.abhishek.knocksense.components.CategoryViewHolder;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListObserver;
import com.example.abhishek.knocksense.components.SingleArticleViewHolder;
import com.example.abhishek.knocksense.components.TwoArticlesViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HomeArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListObserver {

    private int CATEGORY = 1011;
    private int SINGLE_ARTICLE = 2022;
    private int DOUBLE_ARTICLES = 3033;
    private int BIG_ARTICLE = 4044;


    private List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private int toReadItemPosition=0;

    public HomeArticleRecyclerViewAdapter(List<Article> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        GlobalLists.getGlobalListsInstance().registerObserver("home",this);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==BIG_ARTICLE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_big_item_row, parent, false);
            return new BigArticleViewHolder(view);
        }
        else if(viewType==SINGLE_ARTICLE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item_row, parent, false);
            return new SingleArticleViewHolder(view);
        }
        else if(viewType==DOUBLE_ARTICLES){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_two_item_row, parent, false);
            return new TwoArticlesViewHolder(view);
        }
        else if(viewType==CATEGORY){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_category_item_row, parent, false);
            return new CategoryViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        DateConverter dateConverter=new DateConverter();
        int temp=position%16;

        if(temp==1 || temp==7) {
            final BigArticleViewHolder holder=(BigArticleViewHolder) h;

            Article article=mValues.get(toReadItemPosition);
            holder.mItem = article;
            holder.title.setText(article.getTitle());
            holder.author.setText(article.getAuthor());
            holder.date.setText(dateConverter.getDate(mValues.get(position).getDate())+" "+ dateConverter.getMonth(article.getDate())+ " "+dateConverter.getYear(mValues.get(position).getDate()));
            ImageLoader.getInstance().displayImage(article.getFeaturedImage(), holder.big_item_row_image, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    //finalHolder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    //  finalHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            ++toReadItemPosition;

        }
        else if(temp==8 || temp==12){
            final TwoArticlesViewHolder holder=(TwoArticlesViewHolder)h;
            Article leftArticle = mValues.get(toReadItemPosition);
            holder.leftViewArticle=leftArticle;
            holder.leftViewTitle.setText(leftArticle.getTitle());
            /*holder.leftViewDate.setText(leftArticle.getDate());
            holder.leftViewAuthor.setText(leftArticle.getAuthor());
            */
            ImageLoader.getInstance().displayImage(leftArticle.getFeaturedImage(), holder.leftImageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    //finalHolder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    //  finalHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            ++toReadItemPosition;

            Article rightArticle = mValues.get(toReadItemPosition);
            holder.rightViewArticle = rightArticle;
            holder.rightViewTitle.setText(rightArticle.getTitle());
          /*  holder.rightViewDate.setText(rightArticle.getDate());
            holder.rightViewAuthor.setText(rightArticle.getAuthor());
          */
          ImageLoader.getInstance().displayImage(rightArticle.getFeaturedImage(), holder.rightImageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    //finalHolder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    //  finalHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            ++toReadItemPosition;
        }
        else if(temp==0 || temp==6 || temp==11){
            final CategoryViewHolder holder=(CategoryViewHolder) h;
            holder.category.setText("Category here.");

        }
        else if(temp==2 || temp==3 || temp==4 || temp==5 || temp==9 || temp==10 || temp==13 || temp==14 || temp==15){
            final SingleArticleViewHolder holder=(SingleArticleViewHolder) h;
            Article article=mValues.get(toReadItemPosition);
            holder.mItem = article;
            holder.title.setText(article.getTitle());
            holder.author.setText(article.getAuthor());
            holder.date.setText(article.getDate());
            holder.date.setText(dateConverter.getDate(article.getDate())+" "+ dateConverter.getMonth(article.getDate())+ " "+dateConverter.getYear(article.getDate()));
            ImageLoader.getInstance().displayImage(article.getFeaturedImage(), holder.featuredImage, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    //finalHolder.progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    //  finalHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            ++toReadItemPosition;

        }

    }

    @Override
    public int getItemViewType(int position) {
        int temp = position%16;
        int viewType;
        if(temp==0 || temp==6 || temp==11){
            viewType=CATEGORY;
        }
        else if(temp==1 || temp==7){
            viewType=BIG_ARTICLE;
        }
        else if(temp==8 || temp==12){
            viewType=DOUBLE_ARTICLES;
        }
        else{
            viewType=SINGLE_ARTICLE;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void updateList(List<Article> articleList) {
        mValues= articleList;
        this.notifyDataSetChanged();
    }







}