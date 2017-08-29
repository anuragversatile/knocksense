package com.example.abhishek.knocksense;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.knocksense.HomeFragment.OnListFragmentInteractionListener;
import com.example.abhishek.knocksense.components.Article;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HomeArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HomeArticleRecyclerViewAdapter(List<Article> items, OnListFragmentInteractionListener listener, RecyclerView recyclerView, final Context context) {
        mValues = items;
        mListener = listener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0 ){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item_row, parent, false);
            return new SingleArticleViewHolder(view);
        }
        if(viewType ==1){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_big_item_row, parent, false);
            return new BigArticleViewHolder(view);
        }
        if(viewType==-999){
            return null;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        if(position%2==0){
            SingleArticleViewHolder holder=(SingleArticleViewHolder) h;
            holder.mItem = mValues.get(position);
            holder.title.setText(mValues.get(position).getTitle());
            holder.author.setText(mValues.get(position).getAuthor());
            holder.date.setText(mValues.get(position).getDate());
            ImageLoader.getInstance().displayImage(mValues.get(position).getFeaturedImage(), holder.featuredImage, new ImageLoadingListener() {
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

//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mListener) {
//                        // Notify the active callbacks interface (the activity, if the
//                        // fragment is attached to one) that an item has been selected.
//                        mListener.onListFragmentInteraction(holder.mItem);
//                    }
//                }
//            });
//            holder.mView.findViewById(R.id.article_item_row_more).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //// TODO: 26-08-2017 save and share functionality
//                }
//            });
        }
        else{
            final BigArticleViewHolder holder=(BigArticleViewHolder) h;
            holder.mItem = mValues.get(position);
            holder.title.setText(mValues.get(position).getTitle());
            holder.author.setText(mValues.get(position).getAuthor());
            holder.date.setText(mValues.get(position).getDate());
            ImageLoader.getInstance().displayImage(mValues.get(position).getFeaturedImage(), holder.big_item_row_image, new ImageLoadingListener() {
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

//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mListener) {
//                        // Notify the active callbacks interface (the activity, if the
//                        // fragment is attached to one) that an item has been selected.
//                        mListener.onListFragmentInteraction(holder.mItem);
//                    }
//                }
//            });
//            holder.mView.findViewById(R.id.article_item_row_more).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //// TODO: 26-08-2017 save and share functionality
//                }
//            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

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

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }

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

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }
}