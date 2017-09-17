package com.example.abhishek.knocksense;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abhishek.knocksense.components.VolleySingleton;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

public class Post extends AppCompatActivity implements View.OnClickListener {
    WebView content;
    ProgressDialog progressDialog;
    public static final String POST_ID = "id";
    public static final String POST_TITLE = "title";
    public static final String POST_URL = "uri";
    public static final String POST_DATE = "date";

    private String postTitle;
    private Toolbar toolbar;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        int id = getIntent().getExtras().getInt(POST_ID);
        postTitle = getIntent().getExtras().getString(POST_TITLE);

        content = (WebView)findViewById(R.id.web_view);
        final WebViewLoader webViewLoader = new WebViewLoader(content);
        webViewLoader.setWebSettings();




        String url = UrlConstants.getSpecificCategoryOrCityArticlesURL(String.valueOf(id));



        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject ParentObject = new JSONObject(s);
                    webViewLoader.setLoadDataWithBaseUrl(ParentObject.getJSONObject("content").getString("rendered"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(Post.this,"Unable to Load", Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void onClick(View v) {

    }
}