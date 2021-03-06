package com.example.tex.knocksense;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.example.tex.knocksense.components.Article;
import com.example.tex.knocksense.components.GlobalLists;
import com.example.tex.knocksense.components.ListNameConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class SearchResultsActivity extends AppCompatActivity {

    private TextView title;
    private RecyclerView recyclerView;
    private SearchResultsRecyclerViewAdapter mAdapter;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.tex.knocksense.R.layout.activity_search_results);
        view=(View)findViewById(com.example.tex.knocksense.R.id.search_results_relative_layout);

        Toolbar toolbar=(Toolbar) findViewById(com.example.tex.knocksense.R.id.my_toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title=(TextView)findViewById(com.example.tex.knocksense.R.id.search_text_view);
        recyclerView=(RecyclerView)findViewById(com.example.tex.knocksense.R.id.search_results_list);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            showResults(query);
        }
    }

    private void showResults(String query) {
        // Query your data set and show results
        GlobalLists globalListInstance=(GlobalLists)getApplication();
        title.setText(query);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        globalListInstance.fireRefreshData(getApplicationContext(), ListNameConstants.SEARCH, query);

        final SearchResultsActivity that = this;
        mAdapter = new SearchResultsRecyclerViewAdapter(new OnSearchListItemClickedListener() {
            @Override
            public void onItemClicked(Article item, String what, SearchResultsRecyclerViewAdapter.ViewHolder holder) {
                if(what.equals("more")){
                    //// TODO: 26-08-2017 save and share functionality
                    PopupMenu popup = new PopupMenu(that, holder.mView.findViewById(com.example.tex.knocksense.R.id.article_item_row_more));
                    //inflating menu from xml resource
                    popup.inflate(com.example.tex.knocksense.R.menu.options_menu);
                    //adding click listener
                    final Article article=item;
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case com.example.tex.knocksense.R.id.share:
                                    //handle menu1 click
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_TEXT,article.getLink());

                                    try {
                                        that.startActivity(Intent.createChooser(shareIntent,"Share via"));
                                    } catch (Exception ex) {
                                        Toast.makeText(that, ex.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                    break;



                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }

                else{
                    Intent intent = new Intent(that, Post.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",item.getId());
                    bundle.putString("uri", item.getLink());
                    bundle.putString("date",item.getDate());
                    bundle.putString("author",item.getAuthor());
                    bundle.putString("feature",item.getFeaturedImage());
                    bundle.putString("title",item.getTitle());

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }, getApplicationContext(), view, this, ListNameConstants.SEARCH, query);

        recyclerView.setAdapter(mAdapter);


    }

}
