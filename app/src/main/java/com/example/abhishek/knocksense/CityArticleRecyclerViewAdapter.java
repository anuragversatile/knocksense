package com.example.abhishek.knocksense;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.knocksense.CityFragment.OnListFragmentInteractionListener;
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
public class CityArticleRecyclerViewAdapter extends RecyclerView.Adapter<CityArticleRecyclerViewAdapter.ViewHolder> {

    private final List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CityArticleRecyclerViewAdapter(List<Article> items, OnListFragmentInteractionListener listener, RecyclerView recyclerView, final Context context) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       DateConverter dateConverter=new DateConverter();
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());

        holder.author.setText(mValues.get(position).getAuthor());
        holder.date.setText(mValues.get(position).getDate());
        holder.date.setText(dateConverter.getDate(mValues.get(position).getDate())+" "+ dateConverter.getMonth(mValues.get(position).getDate())+ " "+dateConverter.getYear(mValues.get(position).getDate()));

        final CityArticleRecyclerViewAdapter.ViewHolder finalHolder = holder;

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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mView.findViewById(R.id.article_item_row_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 26-08-2017 save and share functionality
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView author;
        public final TextView date;
public  final ImageView featuredImage;
        public Article mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.article_item_row_title);
            author = (TextView) view.findViewById(R.id.article_item_row_author);
            date = (TextView) view.findViewById(R.id.article_item_row_date);
            featuredImage=(ImageView) view.findViewById(R.id.featuredImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }
}
