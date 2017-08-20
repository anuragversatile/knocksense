package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.VolleySingleton;
import com.google.gson.Gson;


public class SplashScreen extends AppCompatActivity {

    private String selectedCityId;
    private List<Article> articleList = new ArrayList<>();
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private void fetchData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConstants.getAllArticlesURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            JSONArray ParentArray = new JSONArray(s);
                            for (int i = 0; i < ParentArray.length(); i++) {
                                JSONObject ParentObject = ParentArray.getJSONObject(i);
                                Article articleModel = gson.fromJson(ParentObject.toString(), Article.class);
                                articleModel.setId(ParentObject.getString("id"));
                                articleModel.setDate(ParentObject.getString("date"));
                                articleModel.setTitle(ParentObject.getJSONObject("title").getString("rendered"));
                                articleModel.setAuthor(ParentObject.getString("author"));
                                articleModel.setContent(ParentObject.getJSONObject("content").getString("rendered"));
                                articleModel.setLink(ParentObject.getString("link"));
                                articleModel.setFeaturedImage(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                                Log.d("Article modal", "onResponse:"+articleModel);
                                articleList.add(articleModel);
                            }
                            //// TODO: 21-08-2017 store articleList globally.
                            //// TODO: 21-08-2017 use timestamps to determine which last timestamp data is in the list the after fetching compare
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                volleyError.printStackTrace();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        selectedCityId = sharedPref.getString(getString(R.string.shared_preference_saved_city),"not selected");

       // new PrefetchData().execute();
        fetchData();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i;
                // This method will be executed once the timer is over

                //if no city is selected goto SelectCityScreen
                if(selectedCityId == "not selected"){
                    i = new Intent(SplashScreen.this, SelectCityScreen.class);
                }
                else{
                    i= new Intent(SplashScreen.this, MainActivityScreen.class);
                }

                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

//    private class PrefetchData extends AsyncTask<Void, Void, List<Article>> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //// TODO: 05-08-2017 read which city and user then fetch accordingly
//
//        }
//
//        @Override
//        protected List<Article> doInBackground(Void... params) {
//
//        }
//        @Override
//        protected void onPostExecute(List<Article> articles) {
//            Log.d("CHECK DATA POST EXECUTE", String.valueOf(articles.size()));
//        }
//    }
    }


