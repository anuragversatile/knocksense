package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishek.knocksense.CityFragment.OnListFragmentInteractionListener;
import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.BigArticleViewHolder;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;
import com.example.abhishek.knocksense.components.ListObserver;
import com.example.abhishek.knocksense.components.SingleArticleViewHolder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CityArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListObserver {

    private List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private  Context context;
    private ProgressBar progressBar;
    private  Font font;
    private List<Object> nativeAd;
    private static final int ads=12345;
private static int normal=23245;
    private static final int bigArticle=321331;
    private GlobalLists globalListInstance;

    public CityArticleRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context context, View view,CityFragment cityFragment) {
        globalListInstance=(GlobalLists)cityFragment.getActivity().getApplication();
        globalListInstance.registerObserver(ListNameConstants.CITY,this);
        mValues = globalListInstance.getCityArticlesList();
        mListener = listener;
        this.context=context;
        this.progressBar = (ProgressBar)view.findViewById(R.id.city_progress_bar);
        if(mValues!=null && mValues.size()>0){
            progressBar.setVisibility(View.GONE);
        }
        else{
            globalListInstance.fireRefreshData(context,ListNameConstants.CITY,null);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*if(viewType==ads)

           {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.admoblist, parent, false);

                return new NativeAdViewHolder(view);


            }*/
           if(viewType==bigArticle){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_big_item_row, parent, false);
                return new BigArticleViewHolder(view);
        }
            else if(viewType==normal){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_item_row, parent, false);
                return new SingleArticleViewHolder(view);

                }
                return null;
        }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder h, int position) {

        DateConverter dateConverter = new DateConverter();
   int temp=position%6;

       /* if(temp==-1)
        {

            // The NativeExpressAdViewHolder recycled by the RecyclerView may be a different
            // instance than the one used previously for this position. Clear the
            // NativeExpressAdViewHolder of any subviews in case it has a different
            // AdView associated with it, and make sure the AdView for this position doesn't
            // already have a parent of a different recycled NativeExpressAdViewHolder.

            }
       */    if(position==0){
                final BigArticleViewHolder holder=(BigArticleViewHolder) h;

                final Article article=mValues.get(position);
                holder.mItem = article;
                if(article.getTitle().contains("&#8216;")) {
                    String title = article.getTitle().replace("&#8216;", "'").replace("&#8217;", "'");
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
                holder.date.setText(dateConverter.getDate(article.getDate())+" "+ dateConverter.getMonth(article.getDate())+ " "+dateConverter.getYear(mValues.get(position).getDate()));
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
                font  = new Font();
                font.setFont3(context,holder.title);
                font.setFont3(context,holder.date);
                font.setFont3(context,holder.author);
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
                                        shareIntent.putExtra(Intent.EXTRA_TEXT,article.getLink());

                                        try {
                                            context.startActivity(Intent.createChooser(shareIntent,"Share via"));
                                        } catch (Exception ex) {
                                            Toast.makeText(context, ex.getMessage(),Toast.LENGTH_LONG).show();
                                        }
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
 else if(position!=0 ) {
     final SingleArticleViewHolder holder=(SingleArticleViewHolder) h;
     final Article article = mValues.get(position);
     holder.mItem = article;
     if(article.getTitle().contains("&#8216;")) {
         String title = article.getTitle().replace("&#8216;", "'").replace("&#8217;", "'");
         holder.title.setText(title);
     }
             else   if(article.getTitle().contains("&#8217;")) {
                    String title = article.getTitle().replace("&#8217;", "'");
                    holder.title.setText(title);
                }
     else   if(article.getTitle().contains("&#8211;")) {
         String title = article.getTitle().replace("&#8211;", "-");
         holder.title.setText(title);
     }
                else if(article.getTitle().contains("&#038;")) {
                    String title = article.getTitle().replace("&#038;", "&");
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
     holder.date.setText(dateConverter.getDate(article.getDate()) + " " + dateConverter.getMonth(article.getDate()) + " " + dateConverter.getYear(article.getDate()));
     font = new Font();
     font.setFont3(context, holder.title);
     font.setFont3(context, holder.date);
     font.setFont3(context, holder.author);


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
                             shareIntent.putExtra(Intent.EXTRA_TEXT, article.getLink());

                             try {
                                 context.startActivity(Intent.createChooser(shareIntent, "Share via"));
                             } catch (Exception ex) {
                                 Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                             }
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
        }


    @Override
    public int getItemCount() {
        return mValues.size();
    }
    @Override
    public int getItemViewType(int position) {
        int viewType;
     int temp=position%6;/*
        if(position%6==4)
            viewType=ads;*/
        if(position==0)
            viewType=bigArticle;
        else viewType=normal;

        return viewType;
    }

    @Override
    public void updateList(List<Article> articleList, boolean hasLoaded, boolean isLoading) {
        if(isLoading && globalListInstance.shouldShowLoader){
            this.progressBar.setVisibility(View.VISIBLE);
        }
        else if(!isLoading && hasLoaded){
            this.progressBar.setVisibility(View.GONE);
            mValues=articleList;
            this.notifyDataSetChanged();
            globalListInstance.shouldShowLoader=true;
        }
    }



    public  class NativeAdViewHolder extends RecyclerView.ViewHolder {


        public NativeAdViewHolder(View itemView) {
            super(itemView);

        }
    }
        }




