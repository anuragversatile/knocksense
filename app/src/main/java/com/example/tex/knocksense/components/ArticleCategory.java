package com.example.tex.knocksense.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 09-09-2017.
 */

public class ArticleCategory {
    private List<Article> articleList;
    private String categoryName;
    private String categoryId;


    public List<Article> getArticleList() {
        return articleList;
    }

    public void addArticleToCategory(Article article) {
        this.articleList.add(article);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ArticleCategory(String categoryName, String categoryId) {

        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.articleList = new ArrayList<>();
    }
}
