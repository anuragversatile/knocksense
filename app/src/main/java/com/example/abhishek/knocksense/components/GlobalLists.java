package com.example.abhishek.knocksense.components;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abhishek.knocksense.UrlConstants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuragdwivedi on 21/08/17.
 */

public class GlobalLists extends Application {
    private static List<Article> cityArticlesList = new ArrayList<>();
    private static List<Article> homeArticlesList = new ArrayList<>();
    private static List<Article> categoriesList = new ArrayList<>();
    private static boolean cityDataLoaded = false;
    private static boolean homeDataLoaded = false;
    private static boolean categoryDataLoaded = false;

    public static Context getContext() {
        return getContext();
    }

    public static boolean isCityDataLoaded() {
        return cityDataLoaded;
    }

    public static void setCityDataLoaded(boolean cityDataLoaded) {
        GlobalLists.cityDataLoaded = cityDataLoaded;
    }

    public static boolean isHomeDataLoaded() {
        return homeDataLoaded;
    }

    public static void setHomeDataLoaded(boolean homeDataLoaded) {
        GlobalLists.homeDataLoaded = homeDataLoaded;
    }

    public static boolean isCategoryDataLoaded() {
        return categoryDataLoaded;
    }

    public static void setCategoryDataLoaded(boolean categoryDataLoaded) {
        GlobalLists.categoryDataLoaded = categoryDataLoaded;
    }

    public static List<Article> getCityArticlesList() {
        return GlobalLists.cityArticlesList;
    }

    public static void setCityArticlesList(List<Article> cityArticlesList) {
        GlobalLists.cityArticlesList = cityArticlesList;
    }


    public static List<Article> getHomeArticlesList() {
        return GlobalLists.homeArticlesList;
    }

    public static void setHomeArticlesList(List<Article> articles) {
        GlobalLists.homeArticlesList = articles;
    }

    public static List<Article> getCategoriesList() {
        return GlobalLists.categoriesList;
    }

    public static void setCategoriesList(List<Article> categoriesList) {
        GlobalLists.categoriesList = categoriesList;
    }

    public static void fetchHomeData(Context context, String d, final RecyclerView.Adapter adapter) {
        final String date = d;
        String url = UrlConstants.getAllArticlesURL();
        if (date != null) {
            url = UrlConstants.getAllArticlesBeforeDate(date);
        }

        final List<Article> articleList = new ArrayList<>();
        GlobalLists.setHomeDataLoaded(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                                articleModel.setLink(ParentObject.getString("link"));
                                articleModel.setFeaturedImage(ParentObject.getJSONObject("better_featured_image").getString("source_url"));

                                articleList.add(articleModel);
                                Log.d(articleModel.getFeaturedImage(),"Count");
                                if (articleList.size() == ParentArray.length()) {
                                    GlobalLists.setHomeDataLoaded(true);
                                }
                            }
                            if (GlobalLists.isHomeDataLoaded()) {
                                if (date != null) {
                                    //having date means that it has to append
                                    List<Article> globalArticles = GlobalLists.getHomeArticlesList();
                                    globalArticles.addAll(articleList);
                                    GlobalLists.setHomeArticlesList(globalArticles);
                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    GlobalLists.setHomeArticlesList(articleList);
                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
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
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public static void fetchCityData(Context context, String selectedCityId, final RecyclerView.Adapter adapter) {

        final List<Article> articleList = new ArrayList<>();
        GlobalLists.setCityDataLoaded(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConstants.getSpecificCategoryOrCityArticlesURL(selectedCityId),
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
                                articleModel.setLink(ParentObject.getString("link"));
                                articleModel.setFeaturedImage(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                                articleList.add(articleModel);
                                if (articleList.size() == ParentArray.length()) {
                                    GlobalLists.setCityDataLoaded(true);
                                }
                            }
                            Log.d("XYZ", "setCityDataLoaded()==>" + GlobalLists.isCityDataLoaded());
                            if (GlobalLists.isCityDataLoaded()) {
                                GlobalLists.setCityArticlesList(articleList);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            }

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
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }

    public static void fetchCategoryData(Context context, RecyclerView.Adapter adapter) {
//        final List<Article> articleList = new ArrayList<>();
//        GlobalLists.setCategoryDataLoaded(false);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConstants.getAllCategoriesURL(),
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String s) {
//                                    try {
//                                        Gson gson = new Gson();
//                                        JSONArray ParentArray = new JSONArray(s);
//                                        for (int i = 0; i < ParentArray.length(); i++) {
//                                            JSONObject ParentObject = ParentArray.getJSONObject(i);
//                                            Article articleModel = gson.fromJson(ParentObject.toString(), Article.class);
//                                            articleModel.setId(ParentObject.getString("id"));
//                                            articleModel.setDate(ParentObject.getString("date"));
//                                            articleModel.setTitle(ParentObject.getJSONObject("title").getString("rendered"));
//                                            articleModel.setAuthor(ParentObject.getString("author"));
//                                            articleModel.setLink(ParentObject.getString("link"));
//                                            articleModel.setFeaturedImage(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
//                                            Log.d("Article modal", "onResponse:" + articleModel);
//                                            articleList.add(articleModel);
//                                            if (articleList.size() == ParentArray.length()) {
//                                                GlobalLists.setCategoryDataLoaded(true);
//                                            }
//                                        }
//                                        GlobalLists.setCategoriesList(articleList);
//                                        //// TODO: 21-08-2017 use timestamps to determine which last timestamp data is in the list the after fetching compare
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//                volleyError.printStackTrace();
//            }
//        });
//        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}