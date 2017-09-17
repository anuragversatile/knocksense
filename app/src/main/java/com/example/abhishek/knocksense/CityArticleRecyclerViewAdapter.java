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
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;
import com.example.abhishek.knocksense.components.ListObserver;
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
public class CityArticleRecyclerViewAdapter extends RecyclerView.Adapter<CityArticleRecyclerViewAdapter.ViewHolder> implements ListObserver {

    private List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private  Context context;
    private ProgressBar progressBar;
    private  Font font;
    private static final int ads=4;
private static int normal=1;
    public CityArticleRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context context, View view,CityFragment cityFragment) {
        GlobalLists globalListInstance=(GlobalLists) cityFragment.getActivity().getApplication();
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder=null;
        switch (viewType) {

            case ads:{
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.admoblist, parent, false);
                viewHolder= new ViewHolder(view);
                break;
            }
            default:{
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_item_row, parent, false);
                viewHolder= new ViewHolder(view);
                break;}
        }
        return viewHolder;
        }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DateConverter dateConverter = new DateConverter();
        switch (holder.getItemViewType()) {

            case ads: {

                break;
            }
 default: {
     final Article article = mValues.get(position);
     holder.mItem = article;
                if(article.getTitle().contains("&#8217;")) {
                    String title = article.getTitle().replace("&#8217;s", "'");
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
     font.setFont(context, holder.title);
     font.setFont1(context, holder.date);
     font.setFont1(context, holder.author);


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
 break;
 }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    @Override
    public int getItemViewType(int position) {
        int viewType;
     int temp=position%5;
        if(position%5==4)
            viewType=ads;
        else viewType=normal;

        return viewType;
    }

    @Override
    public void updateList(List<Article> articleList, boolean hasLoaded, boolean isLoading) {
        if(isLoading){
            this.progressBar.setVisibility(View.VISIBLE);
        }
        else if(!isLoading && hasLoaded){
            this.progressBar.setVisibility(View.GONE);
            mValues=articleList;
            this.notifyDataSetChanged();
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
    public  class NativeAdViewHolder extends RecyclerView.ViewHolder {
        private final NativeExpressAdView mNativeAd;

        public NativeAdViewHolder(View itemView) {
            super(itemView);
            mNativeAd = (NativeExpressAdView) itemView.findViewById(R.id.adView);
            mNativeAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (mListener != null) {
                        Log.i("AndroidBash", "onAdLoaded");
                    }
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    if (mListener != null) {
                        Log.i("AndroidBash", "onAdClosed");
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    if (mListener != null) {
                        Log.i("AndroidBash", "onAdFailedToLoad");
                    }
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    if (mListener != null) {
                        Log.i("AndroidBash", "onAdLeftApplication");
                    }
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    if (mListener != null) {
                        Log.i("AndroidBash", "onAdOpened");
                    }
                }
            });
           /* AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(MainActivityScreen.DEVICE_ID_EMULATOR)
                    .build();*/
            //You can add the following code if you are testing in an emulator
            AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
            mNativeAd.loadAd(adRequest);
        }
    }
        }




