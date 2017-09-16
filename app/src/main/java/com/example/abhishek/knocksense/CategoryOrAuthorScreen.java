package com.example.abhishek.knocksense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CategoryOrAuthorScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryFragmentRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_or_author_screen);
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

        mAdapter = new CategoryFragmentRecyclerViewAdapter(null,getApplicationContext(),view,this,type,id);
        recyclerView.setAdapter(mAdapter);
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Article article);
    }
}
