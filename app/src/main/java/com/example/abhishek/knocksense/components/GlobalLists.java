package com.example.abhishek.knocksense.components;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abhishek.knocksense.R;
import com.example.abhishek.knocksense.SelectCityScreen;
import com.example.abhishek.knocksense.UrlConstants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.abhishek.knocksense.components.ListNameConstants.AUTHOR;
import static com.example.abhishek.knocksense.components.ListNameConstants.CATEGORY;
import static com.example.abhishek.knocksense.components.ListNameConstants.CITY;
import static com.example.abhishek.knocksense.components.ListNameConstants.HOME;


/**
 * Created by anuragdwivedi on 21/08/17.
 */

public class GlobalLists extends Application implements ListPublisher {

    private  List<Article> cityArticlesList;
    private  List<Article> homeArticlesList;
    private List<Article> categoryArticlesList;
    private String selectedCityId;

    private  List<ListObserver> cityListObserverList;
    private  List<ListObserver> homeListObserverList;
    private List<ListObserver> categoryListObserverList;
    private  static GlobalLists instance=null;


    private GlobalLists(){
        cityArticlesList=new ArrayList<>();
        homeArticlesList=new ArrayList<>();
        categoryArticlesList=new ArrayList<>();
        cityListObserverList=new ArrayList<>();
        homeListObserverList=new ArrayList<>();
        categoryListObserverList=new ArrayList<>();
    }

    public static GlobalLists getGlobalListsInstance(){
        if(instance==null){
            instance = new GlobalLists();
        }
        return instance;
    }

    public List<Article> getCategoryArticlesList() {
        return this.categoryArticlesList;
    }

    public void setCategoryArticlesList(List<Article> categoryArticlesList) {
        this.categoryArticlesList = categoryArticlesList;
    }

    public List<Article> getCityArticlesList() {
        return this.cityArticlesList;
    }

    private void setCityArticlesList(List<Article> cityArticlesList) {
        this.cityArticlesList = cityArticlesList;
    }


    public  List<Article> getHomeArticlesList() {
        return this.homeArticlesList;
    }

    private void setHomeArticlesList(List<Article> articles) {
        this.homeArticlesList = articles;
    }
void fetchHomeData(Context context) {

        this.notifyListObservers(HOME,null,false,true);
        String url = UrlConstants.getAllArticlesURL();

        final List<Article> articleList = new ArrayList<>();
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
                                articleModel.setCategories(gson.fromJson(ParentObject.getJSONArray("categories").toString(),String[].class));
                                articleModel.setFeaturedImage(ParentObject.getJSONObject("better_featured_image").getString("source_url"));

                                articleList.add(articleModel);
                                if (articleList.size() == ParentArray.length()) {
                                        GlobalLists.getGlobalListsInstance().setHomeArticlesList(articleList);
                                        GlobalLists.getGlobalListsInstance().notifyListObservers(HOME,articleList,true,false);

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
        stringRequest.setTag(HOME);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    private void fetchCityData(Context context, String selectedCityId) {
        this.notifyListObservers(CITY, null, false, true);
        if (selectedCityId == null) {
            this.selectedCityId= SelectCityScreen.getSelectedCityId();
            Log.d("SELECTED_ID_GLOBAL", "fetchCityData: "+this.selectedCityId);
        }
        else{
            this.selectedCityId=selectedCityId;
        }
        String url = UrlConstants.getSpecificCategoryOrCityArticlesURL(this.selectedCityId);

        final List<Article> articleList = new ArrayList<>();
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
                                if (articleList.size() == ParentArray.length()) {
                                        GlobalLists.getGlobalListsInstance().setCityArticlesList(articleList);
                                    GlobalLists.getGlobalListsInstance().notifyListObservers(CITY,articleList,true,false);
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
        stringRequest.setTag(CITY);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }
    private void fetchCategoryData(Context context, String categoryId) {
        this.notifyListObservers(CITY, null, false, true);
        String url = UrlConstants.getSpecificCategoryOrCityArticlesURL(categoryId);

        final List<Article> articleList = new ArrayList<>();
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
                                if (articleList.size() == ParentArray.length()) {
                                        GlobalLists.getGlobalListsInstance().setCategoryArticlesList(articleList);
                                    GlobalLists.getGlobalListsInstance().notifyListObservers(CITY,articleList,true,false);
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
        stringRequest.setTag(CATEGORY);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }

    public void fireRefreshData(Context context, String listType, String id){
        /*
            * id :? selectedCityId, authorId, categoryId, etc
            * listType :? home, city, author, category
        */
        RequestQueue requestQueue=VolleySingleton.getInstance(context).getRequestQueue();
        switch (listType){
            case HOME:
                //cancel previous request before starting new one
//                requestQueue.cancelAll(HOME);

                fetchHomeData(context);
                break;
            case CITY:
                //cancel previous request before starting new one
                requestQueue.cancelAll(CITY);
                fetchCityData(context, id);
                break;
            case CATEGORY:
                //// TODO: 09/09/17
//                requestQueue.cancelAll(CATEGORY);
                fetchCategoryData(context, id);
                break;
            case AUTHOR:
                //// TODO: 09/09/17
                break;
        }

    }

    @Override
    public void registerObserver(String listType, ListObserver listObserver) {
        switch (listType){
            case HOME:
                homeListObserverList.add(listObserver);
                break;
            case CITY:
                cityListObserverList.add(listObserver);
                break;
        }
    }

    @Override
    public void removeObserver(String listType, ListObserver listObserver) {
        switch (listType){
            case HOME:
                homeListObserverList.remove(listObserver);
                break;
            case CITY:
                cityListObserverList.remove(listObserver);
                break;
        }
    }

    @Override
    public void notifyListObservers(String listType, List<Article> articles, boolean hasLoaded, boolean isLoading) {
        switch (listType){
            case HOME:
                for(ListObserver listObserver: homeListObserverList){
                    listObserver.updateList(articles, hasLoaded, isLoading);
                }
                break;
            case CITY:
                for(ListObserver listObserver: cityListObserverList){
                    listObserver.updateList(articles, hasLoaded, isLoading);
                }
                break;
        }
    }
}