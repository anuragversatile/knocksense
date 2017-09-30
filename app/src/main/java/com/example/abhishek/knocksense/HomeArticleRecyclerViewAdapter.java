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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhishek.knocksense.HomeFragment.OnListFragmentInteractionListener;
import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.ArticleCategory;
import com.example.abhishek.knocksense.components.BigArticleViewHolder;
import com.example.abhishek.knocksense.components.CategoryViewHolder;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;
import com.example.abhishek.knocksense.components.ListObserver;
import com.example.abhishek.knocksense.components.SingleArticleViewHolder;
import com.example.abhishek.knocksense.components.TwoArticles;
import com.example.abhishek.knocksense.components.TwoArticlesViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
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
private Context context;
    private ProgressBar progressBar;
    private Font font;

    private List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private OnCategoryClickListener onCategoryClickListener;

    public HomeArticleRecyclerViewAdapter(OnListFragmentInteractionListener listener,Context context,View view, HomeFragment homeFragment, OnCategoryClickListener onCategoryClickListener) {
        GlobalLists globalListInstance = (GlobalLists)homeFragment.getActivity().getApplication();
        globalListInstance.registerObserver(ListNameConstants.HOME,this);
        mValues=makeFinalList(globalListInstance.getHomeArticlesList());
        mListener = listener;
        this.onCategoryClickListener=onCategoryClickListener;
        this.context=context;
        this.progressBar=(ProgressBar)view.findViewById(R.id.home_progress_bar);
        globalListInstance.registerObserver(ListNameConstants.HOME,this);

        if(mValues!=null && mValues.size()>0){
            progressBar.setVisibility(View.GONE);
        }
        else{
            globalListInstance.fireRefreshData(context,ListNameConstants.HOME,null);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public List<Article> makeFinalList(List<Article> articleList){
        List<Article> finalList=new ArrayList<>();
        Article lastTwoArticle;
        for(int i=0;i<articleList.size();i++){
            if(i==8 || i==13 || i==26){
                Article leftArticle = articleList.get(i);
                Article rightArticle = articleList.get(++i);
                lastTwoArticle = new TwoArticles(null,null,null,null,null,null,null,null,null,leftArticle, rightArticle);
                finalList.add(lastTwoArticle);
            }
            else{
                finalList.add(articleList.get(i));
            }
        }
        return finalList;
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
        else if(temp==8 || temp==12){
            final TwoArticlesViewHolder holder=(TwoArticlesViewHolder)h;
            final TwoArticles twoArticles = (TwoArticles) mValues.get(position);
            final Article leftArticle = twoArticles.getLeftArticle();
            holder.leftViewArticle=leftArticle;
            if(leftArticle.getTitle().contains("&#8216;")) {
                String title = leftArticle.getTitle().replace("&#8216;", "'").replace("&#8217;", "'");
                holder.leftViewTitle.setText(title);
            }
           else if(leftArticle.getTitle().contains("&#8217;")) {
                String title = leftArticle.getTitle().replace("&#8217;", "'");
                holder.leftViewTitle.setText(title);
            }
            else if(leftArticle.getTitle().contains("&#038;")) {
                String title = leftArticle.getTitle().replace("&#038;", "&");
                holder.leftViewTitle.setText(title);
            }
            else   if(leftArticle.getTitle().contains("&#8211;")) {
                String title = leftArticle.getTitle().replace("&#8211;", "-");
                holder.leftViewTitle.setText(title);
            }
            else {
                holder.leftViewTitle.setText(leftArticle.getTitle());
            }
            font  = new Font();
            font.setFont3(context,holder.leftViewTitle);

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

            holder.leftArticleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.leftViewArticle);
                    }
                }
            });
            holder.leftArticleView.findViewById(R.id.article_item_row_more_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 26-08-2017 save and share functionality
                    PopupMenu popup = new PopupMenu(context, holder.leftArticleView.findViewById(R.id.article_item_row_more_left));
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
                                    shareIntent.putExtra(Intent.EXTRA_TEXT,leftArticle.getLink());

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


            final Article rightArticle = twoArticles.getRightArticle();
            holder.rightViewArticle = rightArticle;
            if(rightArticle.getTitle().contains("&#8216;")) {
                String title = rightArticle.getTitle().replace("&#8216;", "'").replace("&#8217;", "'");
                holder.rightViewTitle.setText(title);
            }
           else if(rightArticle.getTitle().contains("&#8217;")) {
                String title = rightArticle.getTitle().replace("&#8217;", "'");
                holder.rightViewTitle.setText(title);
            }
            else if(rightArticle.getTitle().contains("&#038;")) {
                String title = rightArticle.getTitle().replace("&#038;", "&");
                holder.rightViewTitle.setText(title);
            }
            else   if(rightArticle.getTitle().contains("&#8211;")) {
                String title = rightArticle.getTitle().replace("&#8211;", "-");
                holder.rightViewTitle.setText(title);
            }
            else {
                holder.rightViewTitle.setText(rightArticle.getTitle());
            }
            font  = new Font();
            font.setFont3(context,holder.rightViewTitle);

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

            holder.rightArticleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.rightViewArticle);
                    }
                }
            });
            holder.rightArticleView.findViewById(R.id.article_item_row_more_right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 26-08-2017 save and share functionality
                    PopupMenu popup = new PopupMenu(context, holder.rightArticleView.findViewById(R.id.article_item_row_more_right));
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
                                    shareIntent.putExtra(Intent.EXTRA_TEXT,rightArticle.getLink());

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
        else if(temp==0 || temp==6 || temp==11){
            final CategoryViewHolder holder=(CategoryViewHolder) h;
            final Article category = mValues.get(position);
            holder.category.setText(category.getTitle());
            holder.mItem= category;
            font  = new Font();
            font.setFont(context,holder.category);
            holder.mView.findViewById(R.id.category_article_textView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryClickListener.onCategoryViewPressed(category.getId());
                }
            });

        }
        else if(temp==2 || temp==3 || temp==4 || temp==5 || temp==9 || temp==10 || temp==13 || temp==14 || temp==15){
            final SingleArticleViewHolder holder=(SingleArticleViewHolder) h;
            final Article article=mValues.get(position);
            holder.mItem = article;
            if(article.getTitle().contains("&#8216;") ) {
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
    public void updateList(List<Article> articleList, boolean hasLoaded, boolean isLoading) {
        if(isLoading){
            this.progressBar.setVisibility(View.VISIBLE);
        }
        else if(!isLoading && hasLoaded){
            this.progressBar.setVisibility(View.GONE);
            mValues = makeFinalList(articleList);
            this.notifyDataSetChanged();
        }
    }


}
interface OnCategoryClickListener{
    public void onCategoryViewPressed(String categoryId);
}