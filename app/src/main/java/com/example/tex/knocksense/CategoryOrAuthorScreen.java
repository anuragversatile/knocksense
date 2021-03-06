package com.example.tex.knocksense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tex.knocksense.components.Article;
import com.example.tex.knocksense.components.GlobalLists;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CategoryOrAuthorScreen extends AppCompatActivity{

    private RecyclerView recyclerView;
    private CategoryOrAuthorRecyclerViewAdapter mAdapter;
    private Font font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.example.tex.knocksense.R.layout.activity_category_or_author_screen);

        Toolbar toolbar = (Toolbar) findViewById(com.example.tex.knocksense.R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(com.example.tex.knocksense.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        View view = (View)findViewById(com.example.tex.knocksense.R.id.category_or_author_relative_layout);
        TextView textView=(TextView)findViewById(com.example.tex.knocksense.R.id.category_title_text_view);
        GlobalLists globalListInstance=(GlobalLists)getApplication();
        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("ID");
        String type=bundle.getString("TYPE");
        String categoryName=bundle.getString("TITLE");
        textView.setText(categoryName);


        Font font = new Font();
        font.setFont(this,textView);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        globalListInstance.fireRefreshData(getApplicationContext(), type, id);

        recyclerView = (RecyclerView) findViewById(com.example.tex.knocksense.R.id.category_or_author_list);
        final CategoryOrAuthorScreen that = this;
        mAdapter = new CategoryOrAuthorRecyclerViewAdapter(new OnCategoryListItemClickedListener() {
            @Override
            public void onItemClicked(Article item, String what, CategoryOrAuthorRecyclerViewAdapter.ViewHolder holder) {
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
        }, getApplicationContext(), view, this, type, id);

        recyclerView.setAdapter(mAdapter);
    }

}

