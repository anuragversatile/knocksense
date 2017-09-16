package com.example.abhishek.knocksense;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
    private Font font;

    private List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private List<Article> finalList;

    public HomeArticleRecyclerViewAdapter(List<Article> items, OnListFragmentInteractionListener listener,Context context) {
        mValues = items;
        mListener = listener;
        this.context=context;
        GlobalLists.getGlobalListsInstance().registerObserver(ListNameConstants.HOME,this);
        List<ArticleCategory> articleCategoryList=makeCategoryArticleList(items);
        finalList = makeFinalList(articleCategoryList);
    }
    private List<Article> makeFinalList(List<ArticleCategory> articleCategoryList){
        List<Article> finalList=new ArrayList<>();
        int currentPosition=0;
        for(int i=0;i<articleCategoryList.size();i++){
            ArticleCategory articleCategory=articleCategoryList.get(i);
            String id=articleCategory.getCategoryId();
            String title=articleCategory.getCategoryName();
            finalList.add(new Article(id, null, null, null, title, null,null, null, null));
            ++currentPosition;
            List<Article> articles=articleCategory.getArticleList();
            for(int j=0;j<5;j++){
                if(currentPosition%16==8 || currentPosition%16==12){
                    Article leftArticle = articles.get(j);
                    Article rightArticle = articles.get(++j);
                    Article twoArticles = new TwoArticles(null, null, null, null, null, null, null, null, null, leftArticle,rightArticle);
                    finalList.add(twoArticles);
                    ++currentPosition;
                }
                else{
                    Article article=articles.get(j);
                    finalList.add(article);
                    ++currentPosition;
                }
            }
        }
        return finalList;
    }
    private List<ArticleCategory> makeCategoryArticleList(List<Article> items) {
        ArticleCategory obBollywood = new ArticleCategory("BOLLYWOOD", "7006");
        ArticleCategory obDineSense = new ArticleCategory("DINESENSE", "765");
        ArticleCategory obEntertainment = new ArticleCategory("ENTERTAINMENT", "5");
        ArticleCategory obCulture = new ArticleCategory("CULTURE", "3");
        ArticleCategory obKnock = new ArticleCategory("KNOCK", "9");
        ArticleCategory obNews = new ArticleCategory("NEWS", "14");
        ArticleCategory obSports = new ArticleCategory("SPORTS", "16");
        ArticleCategory obTechSense = new ArticleCategory("TECHSENSE", "17");
        ArticleCategory obWeReview = new ArticleCategory("WEREVIEW", "19");
        ArticleCategory obYourSpace = new ArticleCategory("YOURSPACE", "522");
        ArticleCategory obHindi = new ArticleCategory("HINDI", "7005");


        List<ArticleCategory> articleCategoryList = new ArrayList<>();
        for (Article article : items) {
            String articleId = article.getId();
            String[] articleCategories = article.getCategories();
            String categoryName;
            for (String categoryId : articleCategories) {
                categoryName = (CategoryId.getCategoryId(categoryId));
                switch (categoryName.toUpperCase()) {
                    case "BOLLYWOOD":
                        obBollywood.addArticleToCategory(article);
                        break;
                    case "DINESENSE":
                        obDineSense.addArticleToCategory(article);
                        break;
                    case "ENTERTAINMENT":
                        obEntertainment.addArticleToCategory(article);
                        break;
                    case "CULTURE":
                        obCulture.addArticleToCategory(article);
                        break;
                    case "KNOCKKNOCK":
                        obKnock.addArticleToCategory(article);
                        break;
                    case "NEWS":
                        obNews.addArticleToCategory(article);
                        break;
                    case "SPORTS":
                        obSports.addArticleToCategory(article);
                        break;
                    case "TECHSENSE":
                        obTechSense.addArticleToCategory(article);
                        break;
                    case "WEREVIEW":
                        obWeReview.addArticleToCategory(article);
                        break;
                    case "YOURSPACE":
                        obYourSpace.addArticleToCategory(article);
                        break;
                    case "HINDI":
                        obHindi.addArticleToCategory(article);
                        break;
                    default:
                        //articles of categories which are not hard coded are not considered
                        break;

                }


            }
        }
        if (obBollywood.getArticleList().size() > 4) {
            articleCategoryList.add(obBollywood);
        }
        if (obDineSense.getArticleList().size() > 4) {
            articleCategoryList.add(obDineSense);

        }
        if (obCulture.getArticleList().size() > 4)
        {
            articleCategoryList.add(obCulture);

        }
        if(obEntertainment.getArticleList().size()>4)
        {
            articleCategoryList.add(obEntertainment);
        }
        if (obKnock.getArticleList().size()>4)
        {
            articleCategoryList.add(obKnock);
        }
        if(obNews.getArticleList().size()>4)
        {
            articleCategoryList.add(obNews);
        }
        if(obSports.getArticleList().size()>4)
        {
            articleCategoryList.add(obSports);
        }
        if (obTechSense.getArticleList().size()>4)
        {
            articleCategoryList.add(obTechSense);
        }
        if (obWeReview.getArticleList().size()>4)
        {
            articleCategoryList.add(obWeReview);
        }
        if (obYourSpace.getArticleList().size()>4)
        {
            articleCategoryList.add(obYourSpace);
        }
        if (obHindi.getArticleList().size()>4)
        {
            articleCategoryList.add(obHindi);
        }
        return articleCategoryList;
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

            final Article article=finalList.get(position);
            holder.mItem = article;
            holder.title.setText(article.getTitle());
            holder.author.setText(article.getAuthor());
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
            font.setFont2(context,holder.date);
            font.setFont2(context,holder.author);
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
        else if(temp==8 || temp==12){
            final TwoArticlesViewHolder holder=(TwoArticlesViewHolder)h;
            final TwoArticles twoArticles = (TwoArticles) finalList.get(position);
            final Article leftArticle = twoArticles.getLeftArticle();
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

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.leftViewArticle);
                    }
                }
            });
            holder.mView.findViewById(R.id.article_item_row_more_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 26-08-2017 save and share functionality
                    PopupMenu popup = new PopupMenu(context, holder.mView.findViewById(R.id.article_item_row_more_left));
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


            final Article rightArticle = twoArticles.getRightArticle();
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

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.rightViewArticle);
                    }
                }
            });
            holder.mView.findViewById(R.id.article_item_row_more_right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 26-08-2017 save and share functionality
                    PopupMenu popup = new PopupMenu(context, holder.mView.findViewById(R.id.article_item_row_more_right));
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
        else if(temp==0 || temp==6 || temp==11){
            final CategoryViewHolder holder=(CategoryViewHolder) h;
            Article category = finalList.get(position);
            holder.category.setText(category.getTitle());

        }
        else if(temp==2 || temp==3 || temp==4 || temp==5 || temp==9 || temp==10 || temp==13 || temp==14 || temp==15){
            final SingleArticleViewHolder holder=(SingleArticleViewHolder) h;
            final Article article=finalList.get(position);
            holder.mItem = article;
            holder.title.setText(article.getTitle());
            holder.author.setText(article.getAuthor());
            holder.date.setText(article.getDate());
            holder.date.setText(dateConverter.getDate(article.getDate())+" "+ dateConverter.getMonth(article.getDate())+ " "+dateConverter.getYear(article.getDate()));
            font  = new Font();
            font.setFont(context,holder.title);
            font.setFont1(context,holder.date);
            font.setFont1(context,holder.author);
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
        return finalList.size();
    }

    @Override
    public void updateList(List<Article> articleList, boolean hasLoaded, boolean isLoading) {
        //// TODO: 16-09-2017
    }


}