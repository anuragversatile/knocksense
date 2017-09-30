package com.example.abhishek.knocksense.components;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abhishek.knocksense.CategoryId;
import com.example.abhishek.knocksense.R;
import com.example.abhishek.knocksense.SelectCityScreen;
import com.example.abhishek.knocksense.UrlConstants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.abhishek.knocksense.CategoryId.ENTERTAINMENT_ID;
import static com.example.abhishek.knocksense.CategoryId.KNOCKKNOCK_ID;
import static com.example.abhishek.knocksense.CategoryId.REST_OF_INDIA_ID;
import static com.example.abhishek.knocksense.CategoryId.TECHSENSE_ID;
import static com.example.abhishek.knocksense.CategoryId.YOURSPACE_ID;
import static com.example.abhishek.knocksense.components.ListNameConstants.AUTHOR;
import static com.example.abhishek.knocksense.components.ListNameConstants.CATEGORY;
import static com.example.abhishek.knocksense.components.ListNameConstants.CITY;
import static com.example.abhishek.knocksense.components.ListNameConstants.HOME;
import static com.example.abhishek.knocksense.components.ListNameConstants.SEARCH;


/**
 * Created by anuragdwivedi on 21/08/17.
 */

public class GlobalLists extends Application implements ListPublisher {

    public boolean shouldShowLoader=true;

    private  List<Article> cityArticlesList;
    private  List<Article> homeArticlesList;
    private List<Article> categoryArticlesList;

    private List<Article> searchArticlesList;


    private static List<Article>authorList;
    private String selectedCityId;
    private String lastCategoryOrAuthorId=null;

    private List<ListObserver> searchListObserverList;

    public List<Article> getSearchArticlesList() {
        return searchArticlesList;
    }

    public void setSearchArticlesList(List<Article> searchArticlesList) {
        this.searchArticlesList = searchArticlesList;
    }

    public String getLastCategoryOrAuthorId() {
        return lastCategoryOrAuthorId;
    }

    public void setLastCategoryOrAuthorId(String lastCategoryOrAuthorId) {
        this.lastCategoryOrAuthorId = lastCategoryOrAuthorId;
    }


    private  List<ListObserver> cityListObserverList;
    private  List<ListObserver> homeListObserverList;
    private List<ListObserver> categoryListObserverList;
    private List<ListObserver> authorListObserverList;
    private  static GlobalLists instance=null;



    public GlobalLists(){
        cityArticlesList=new ArrayList<>();
        homeArticlesList=new ArrayList<>();
        authorList=new ArrayList<>();
        categoryArticlesList=new ArrayList<>();
        cityListObserverList=new ArrayList<>();
        homeListObserverList=new ArrayList<>();
        categoryListObserverList=new ArrayList<>();
        authorListObserverList=new ArrayList<>();

        searchArticlesList=new ArrayList<>();
        searchListObserverList=new ArrayList<>();

    }

    public List<Article> getCategoryArticlesList() {
        return this.categoryArticlesList;
    }

    public void setCategoryArticlesList(List<Article> categoryArticlesList) {
        this.categoryArticlesList = categoryArticlesList;
    }

    public static List<Article> getAuthorList() {
        return GlobalLists.authorList;
    }

    public static void setAuthorList(List<Article> authorList) {
        GlobalLists.authorList = authorList;
    }

    public List<ListObserver> getAuthorListObserverList() {
        return authorListObserverList;
    }

    public void setAuthorListObserverList(List<ListObserver> authorListObserverList) {
        this.authorListObserverList = authorListObserverList;
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

        final String[] categoriesToFetch = {ENTERTAINMENT_ID, KNOCKKNOCK_ID, YOURSPACE_ID, TECHSENSE_ID, REST_OF_INDIA_ID};

        final GlobalLists globalListInstance = this;
        this.notifyListObservers(HOME, null, false, true);
        final List<Article> articleList = new ArrayList<>();
        for (int k = 0; k < categoriesToFetch.length; k++) {
            final String categoryId = categoriesToFetch[k];
            final String categoryName = CategoryId.getCategoryName(categoryId);
            String url = UrlConstants.getSpecificCategoryOrCityArticlesURL(categoryId,5);
            final List<Article> specificCategoryList=new ArrayList<>(1+5);//category label + articles
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            Gson gson = new Gson();
                            JSONArray ParentArray = new JSONArray(s);
                            for (int i = 0; i < 5; i++) {
                                JSONObject ParentObject = ParentArray.getJSONObject(i);
                                Article articleModel = gson.fromJson(ParentObject.toString(), Article.class);
                                articleModel.setId(ParentObject.getString("id"));
                                articleModel.setDate(ParentObject.getString("date"));
                                articleModel.setTitle(ParentObject.getJSONObject("title").getString("rendered"));
                                articleModel.setAuthor(ParentObject.getString("author"));
                                articleModel.setLink(ParentObject.getString("link"));
                                articleModel.setCategories(gson.fromJson(ParentObject.getJSONArray("categories").toString(), String[].class));
                                articleModel.setFeaturedImage(ParentObject.getJSONObject("better_featured_image").getString("source_url"));

                                specificCategoryList.add(articleModel);
                                if (specificCategoryList.size() == 5) {
                                    specificCategoryList.add(0,new Article(categoryId, null, null,  null, categoryName, null, null, null, null));
                                    articleList.addAll(specificCategoryList);

                                    if(i==categoriesToFetch.length-1){
                                        globalListInstance.setHomeArticlesList(articleList);
                                        globalListInstance.notifyListObservers(HOME,articleList,true,false);
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
        stringRequest.setTag(HOME);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
    }



    private void fetchCityData(Context context, String selectedCityId) {
        final GlobalLists globalListInstance=this;
        this.notifyListObservers(CITY, null, false, true);
        try {
            if (selectedCityId != null) {
                this.selectedCityId= selectedCityId;

                Log.d("SELECTED_ID_GLOBAL", "fetchCityData: "+this.selectedCityId);
            }

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_LONG).show();
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
                                        globalListInstance.setCityArticlesList(articleList);
                                    globalListInstance.notifyListObservers(CITY,articleList,true,false);
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


    private void fetchAuthorData(Context context) {
        final GlobalLists globalListInstance=this;
        this.notifyListObservers(AUTHOR, null, false, true);

        String url = UrlConstants.getAllAuthorsURL();

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
                                articleModel.setName(ParentObject.getString("name"));
                                articleModel.setAuthorImage(ParentObject.getJSONObject("avatar_urls").getString("96"));
                             articleList.add(articleModel);
                                if (articleList.size() == ParentArray.length()) {
                                    globalListInstance.setAuthorList(articleList);
                                    globalListInstance.notifyListObservers(AUTHOR,articleList,true,false);
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
        stringRequest.setTag(AUTHOR);
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }

    private void fetchCategoryData(Context context, String categoryId) {
        final GlobalLists globalListInstance=this;
        this.notifyListObservers(CATEGORY, null, false, true);
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
                                    globalListInstance.setCategoryArticlesList(articleList);
                                    globalListInstance.notifyListObservers(CATEGORY,articleList,true,false);
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

    private void fetchSearchData(Context context, String searchQuery){
        final GlobalLists globalListInstance=this;
        this.notifyListObservers(SEARCH, null, false, true);
        String url = UrlConstants.getSearchURL(searchQuery);

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
                                    globalListInstance.setSearchArticlesList(articleList);
                                    globalListInstance.notifyListObservers(SEARCH,articleList,true,false);
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
        stringRequest.setTag(SEARCH);
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
                requestQueue.cancelAll(HOME);
                fetchHomeData(context);
                break;
            case CITY:
                requestQueue.cancelAll(CITY);
                fetchCityData(context, id);
                break;
            case CATEGORY:
                requestQueue.cancelAll(CATEGORY);
                fetchCategoryData(context, id);
                break;
            case AUTHOR:

                requestQueue.cancelAll(AUTHOR);
                fetchAuthorData(context);
                break;
            case SEARCH:
                requestQueue.cancelAll(SEARCH);
                fetchSearchData(context,id);

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

            case AUTHOR:
                authorListObserverList.add(listObserver);

            break;
            case CATEGORY:
                categoryListObserverList.add(listObserver);
            break;
            case SEARCH:
                searchListObserverList.add(listObserver);
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
            case AUTHOR:
                authorListObserverList.remove(listObserver);
            case CATEGORY:
                categoryListObserverList.remove(listObserver);
                break;
            case SEARCH:
                searchListObserverList.remove(listObserver);
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

            case AUTHOR:
                for(ListObserver listObserver: authorListObserverList){
                    listObserver.updateList(articles, hasLoaded, isLoading);
                }
                break;
            case CATEGORY:
                for(ListObserver listObserver: categoryListObserverList){

                    listObserver.updateList(articles, hasLoaded, isLoading);
                }
                break;
            case SEARCH:
                for (ListObserver listObserver:searchListObserverList){
                    listObserver.updateList(articles, hasLoaded, isLoading);
                }
        }

    }
}