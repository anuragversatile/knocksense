package com.example.abhishek.knocksense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CategoryOrAuthorScreen extends AppCompatActivity{

    private RecyclerView recyclerView;
    private CategoryOrAuthorRecyclerViewAdapter mAdapter;
    private OnCategoryListItemClickedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_or_author_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.knocksenselogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View view = (View)findViewById(R.id.category_or_author_relative_layout);
        GlobalLists globalListInstance=(GlobalLists)getApplication();
        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("ID");
        String type=bundle.getString("TYPE");




        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        globalListInstance.fireRefreshData(getApplicationContext(), type, id);

        recyclerView = (RecyclerView) findViewById(R.id.category_or_author_list);
        final CategoryOrAuthorScreen that = this;
        mAdapter = new CategoryOrAuthorRecyclerViewAdapter(new OnCategoryListItemClickedListener() {
            @Override
            public void onItemClicked(Article item, String what, CategoryOrAuthorRecyclerViewAdapter.ViewHolder holder) {
                if(what.equals("more")){
                    //// TODO: 26-08-2017 save and share functionality
                    PopupMenu popup = new PopupMenu(that, holder.mView.findViewById(R.id.article_item_row_more));
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu);
                    //adding click listener
                    final Article article=item;
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
                                        that.startActivity(Intent.createChooser(shareIntent,"Share via"));
                                    } catch (Exception ex) {
                                        Toast.makeText(that, ex.getMessage(),Toast.LENGTH_LONG).show();
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

               else{
                    Intent intent = new Intent(that, WebViewScreen.class);
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

