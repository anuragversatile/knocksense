package com.example.abhishek.knocksense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;

public class CategoryOrAuthorScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryFragmentRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_or_author_screen);
        GlobalLists globalListInstance=(GlobalLists)getApplication();
        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("ID");
        String type=bundle.getString("TYPE");

        globalListInstance.fireRefreshData(getApplicationContext(), type, id);

        recyclerView = (RecyclerView) findViewById(R.id.category_or_author_list);

        mAdapter = new CategoryFragmentRecyclerViewAdapter(globalListInstance.getCategoryArticlesList(),null,getApplicationContext());
    }
}
