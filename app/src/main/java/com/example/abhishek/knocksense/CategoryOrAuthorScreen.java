package com.example.abhishek.knocksense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import static com.example.abhishek.knocksense.R.color.white;

public class CategoryOrAuthorScreen extends AppCompatActivity{

    private RecyclerView recyclerView;
    private CategoryFragmentRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_or_author_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        mAdapter = new CategoryFragmentRecyclerViewAdapter(getApplicationContext(),view,this,type,id);
        recyclerView.setAdapter(mAdapter);
    }


}
