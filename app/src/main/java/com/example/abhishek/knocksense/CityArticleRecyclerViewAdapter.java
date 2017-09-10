package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishek.knocksense.CityFragment.OnListFragmentInteractionListener;
import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListObserver;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CityArticleRecyclerViewAdapter extends RecyclerView.Adapter<CityArticleRecyclerViewAdapter.ViewHolder> implements ListObserver {

    private List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private  Context context;
private  Font font;
    public CityArticleRecyclerViewAdapter(List<Article> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       DateConverter dateConverter=new DateConverter();
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        for(Article article:  GlobalLists.getAuthorList()) {
            String authorId = article.getId();
            if (mValues.get(position).getAuthor().equals(authorId)) {
                holder.author.setText(article.getName());
                break;
            }
        }

        holder.date.setText(mValues.get(position).getDate());
        holder.date.setText(dateConverter.getDate(mValues.get(position).getDate())+" "+ dateConverter.getMonth(mValues.get(position).getDate())+ " "+dateConverter.getYear(mValues.get(position).getDate()));
        font  = new Font();
        font.setFont(context,holder.title);
        font.setFont1(context,holder.date);
        font.setFont1(context,holder.author);

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
                PopupMenu popup = new PopupMenu(context, holder.mView.findViewById(R.id.article_item_row_more));
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.share:
                                //handle menu1 click
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_TEXT,mValues.get(position).getLink());

                                try {
                                   context.startActivity(Intent.createChooser(shareIntent,"Share via"));
                                } catch (Exception ex) {
                                    Toast.makeText(context, ex.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                break;
                            case R.id.save:
                                //handle menu2 click
                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }





        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void updateList(List<Article> articleList, Integer newItemCount) {
        if(newItemCount==null){
            this.notifyDataSetChanged();
        }
        else{
            this.notifyItemRangeChanged(mValues.size(),newItemCount);
        }
        //change mValues after its old size has been determined
        mValues = articleList;
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
