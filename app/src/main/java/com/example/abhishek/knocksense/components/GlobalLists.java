package com.example.abhishek.knocksense.components;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

    public static Context getContext() {
        return getContext();
    }

    public static List<Article> getCategoryArticlesList() {
        return getGlobalListsInstance().categoryArticlesList;
    }

    public static void setCategoryArticlesList(List<Article> categoryArticlesList) {
        getGlobalListsInstance().categoryArticlesList = categoryArticlesList;
    }

    public static List<Article> getCityArticlesList() {
        return getGlobalListsInstance().cityArticlesList;
    }

    private static void setCityArticlesList(List<Article> cityArticlesList) {
        getGlobalListsInstance().cityArticlesList = cityArticlesList;
    }


    public static List<Article> getHomeArticlesList() {
        return getGlobalListsInstance().homeArticlesList;
    }

    private static void setHomeArticlesList(List<Article> articles) {
        getGlobalListsInstance().homeArticlesList = articles;
    }

    private static void fetchHomeData(Context context, String d) {
        final String date = d;
        String url = UrlConstants.getAllArticlesURL();
        if (date != null) {
            url = UrlConstants.getAllArticlesBeforeDate(date);
        }

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
                                    if (date != null) {
                                        //having date means that it has to append
                                        List<Article> globalArticles = GlobalLists.getHomeArticlesList();
                                        globalArticles.addAll(articleList);
                                        GlobalLists.setHomeArticlesList(globalArticles);
                                    } else {
                                        GlobalLists.setHomeArticlesList(articleList);

                                    }
                                    Integer newItemCount = (date==null)?null:articleList.size();
                                    getGlobalListsInstance().notifyListObservers(HOME,newItemCount);
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

    private static void fetchCityData(Context context, String selectedCityId, String d) {
        final String date = d;

        if(selectedCityId==null){
            selectedCityId=getGlobalListsInstance().selectedCityId;
        }
        else{
            //this will be called once from SelectCityScreen
            getGlobalListsInstance().selectedCityId=selectedCityId;
        }
        String url = UrlConstants.getSpecificCategoryOrCityArticlesURL(selectedCityId);
        if(date!=null){
            url = UrlConstants.getAllArticlesBeforeDate(date);
        }

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
                                    if (date != null) {
                                        //having date means that it has to append
                                        List<Article> globalArticles = GlobalLists.getCityArticlesList();
                                        globalArticles.addAll(articleList);
                                        GlobalLists.setCityArticlesList(globalArticles);
                                    } else {
                                        GlobalLists.setCityArticlesList(articleList);
                                    }
                                    Integer newItemCount= (date==null)?null:articleList.size();
                                    getGlobalListsInstance().notifyListObservers(CITY,newItemCount);
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
    private static void fetchCategoryData(Context context, String categoryId, String d) {
        final String date = d;
        String url = UrlConstants.getSpecificCategoryOrCityArticlesURL("7006");
        if(date!=null){
            url = UrlConstants.getAllArticlesBeforeDate(date);
        }

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
                                    if (date != null) {
                                        //having date means that it has to append
                                        List<Article> globalArticles = GlobalLists.getCategoryArticlesList();
                                        globalArticles.addAll(articleList);
                                        GlobalLists.setCategoryArticlesList(globalArticles);
                                    } else {
                                        GlobalLists.setCategoryArticlesList(articleList);
                                    }
                                    Integer newItemCount= (date==null)?null:articleList.size();
                                    getGlobalListsInstance().notifyListObservers(CATEGORY,newItemCount);
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

    public static void fireRefreshData(Context context, String listType, boolean fetchByDate, String id){
        /*
            * id :? selectedCityId, authorId, categoryId, etc
            * listType :? home, city, author, category
        */
        RequestQueue requestQueue=VolleySingleton.getInstance(context).getRequestQueue();
        String date=null;
        switch (listType){
            case HOME:
                //cancel previous request before starting new one
                requestQueue.cancelAll(HOME);
                if(fetchByDate){
                    date = getHomeArticlesList().get(getHomeArticlesList().size()-1).getDate();
                }
                fetchHomeData(context, date);
                break;
            case CITY:
                //cancel previous request before starting new one
                requestQueue.cancelAll(CITY);
                if(fetchByDate){
                    date = getCityArticlesList().get(getCityArticlesList().size()-1).getDate();
                }
                fetchCityData(context, id, date);
                break;
            case CATEGORY:
                //// TODO: 09/09/17
                requestQueue.cancelAll(CATEGORY);
                if(fetchByDate){
                    date = getCategoryArticlesList().get(getCategoryArticlesList().size()-1).getDate();
                }
                fetchCategoryData(context, id, date);
                break;
            case AUTHOR:
                //// TODO: 09/09/17
                break;
        }

    }

    @Override
    public void registerObserver(String listType, ListObserver listObserver) {
        switch (listType){
            case "home":
                homeListObserverList.add(listObserver);
                break;
            case "city":
                cityListObserverList.add(listObserver);
                break;
            case "category":
                categoryListObserverList.add(listObserver);
                break;
            default:
                Log.e("List observers", "registerObserver: incorrect string passed");
        }
    }

    @Override
    public void removeObserver(String listType, ListObserver listObserver) {
        switch (listType){
            case "home":
                homeListObserverList.remove(listObserver);
                break;
            case "city":
                cityListObserverList.remove(listObserver);
                break;
            case "category":
                categoryListObserverList.remove(listObserver);
                break;

            default:
                Log.e("List observers", "removeObserver: incorrect string passed");
        }

    }

    @Override
    public void notifyListObservers(String listType, Integer newItemCount) {
        switch (listType){
            case HOME:
                for(ListObserver o: homeListObserverList){
                    o.updateList(homeArticlesList, newItemCount);
                }
                break;
            case CITY:
                for(ListObserver o: cityListObserverList){
                    o.updateList(cityArticlesList, newItemCount);
                }
                break;
            case CATEGORY:
                for(ListObserver o: categoryListObserverList){
                    o.updateList(categoryArticlesList, newItemCount);
                }
                break;

            default:
                Log.e("List observers", "registerObserver: incorrect string passed");
        }

    }
}