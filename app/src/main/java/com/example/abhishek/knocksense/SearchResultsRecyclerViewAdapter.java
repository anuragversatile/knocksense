package com.example.abhishek.knocksense;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;
import com.example.abhishek.knocksense.components.ListObserver;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Abhishek on 17-09-2017.
 */

public class SearchResultsRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolder> implements ListObserver {


    private  List<Article> mValues;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;


    private final OnSearchListItemClickedListener mListener;
    private  final Context context;
    private  Font font;
    public SearchResultsRecyclerViewAdapter(OnSearchListItemClickedListener listener, Context context, View view, SearchResultsActivity searchResultsActivity, String type, String queryString) {
        GlobalLists globalListInstance=(GlobalLists) searchResultsActivity.getApplication();
        globalListInstance.registerObserver(ListNameConstants.SEARCH,this);
        mValues = globalListInstance.getSearchArticlesList();
        mListener=listener;
        this.context=context;

        this.progressBar =(ProgressBar)view.findViewById(R.id.search_progress_bar);
        this.recyclerView = (RecyclerView)view.findViewById(R.id.search_results_list);

           recyclerView.setVisibility(View.GONE);
            globalListInstance.setLastCategoryOrAuthorId(queryString);
            progressBar.setVisibility(View.VISIBLE);
            globalListInstance.fireRefreshData(context,ListNameConstants.SEARCH,queryString);

    }

    @Override
    public SearchResultsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item_row, parent, false);
        return new SearchResultsRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SearchResultsRecyclerViewAdapter.ViewHolder holder, final int position) {
        DateConverter dateConverter=new DateConverter();
        final Article article=mValues.get(position);
        holder.mItem =article;
        if(article.getTitle().contains("&#8216;")) {
            String title = article.getTitle().replace("&#8216;", "'");
            holder.title.setText(title);
        }
        else if(article.getTitle().contains("&#8217;")) {
            String title = article.getTitle().replace("&#8217;", "'");
            holder.title.setText(title);
        }

        else if(article.getTitle().contains("&#038;")) {
            String title = article.getTitle().replace("&#038;", "&");
            holder.title.setText(title);
        }
        else   if(article.getTitle().contains("&#8211;")) {
            String title = article.getTitle().replace("&#8211;", "-");
            holder.title.setText(title);
        }
        else {
            holder.title.setText(article.getTitle());
        }
        for (Article arti : GlobalLists.getAuthorList()) {
            String authorId = arti.getId();
            if (mValues.get(position).getAuthor().equals(authorId)) {
                holder.author.setText(arti.getName());
                break;
            }
        }
        holder.date.setText(article.getDate());
        holder.date.setText(dateConverter.getDate(article.getDate())+" "+ dateConverter.getMonth(article.getDate())+ " "+dateConverter.getYear(article.getDate()));
        font  = new Font();
        font.setFont3(context,holder.title);
        font.setFont3(context,holder.date);
        font.setFont3(context,holder.author);


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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClicked(holder.mItem,"list", null);
                }
            }
        });
        holder.mView.findViewById(R.id.article_item_row_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(holder.mItem,"more", holder);

            }

        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void updateList(List<Article> articleList, boolean hasLoaded, boolean isLoading) {
        if(isLoading){
            this.progressBar.setVisibility(View.VISIBLE);
        }
        else if(!isLoading && hasLoaded){
            mValues=articleList;
            this.notifyDataSetChanged();
            this.progressBar.setVisibility(View.GONE);
            this.recyclerView.setVisibility(View.VISIBLE);

        }

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

    }
}
interface OnSearchListItemClickedListener{
    public void onItemClicked(Article item, String what, SearchResultsRecyclerViewAdapter.ViewHolder holder);
}
