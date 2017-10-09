package com.example.tex.knocksense.components;

/**
 * Created by Abhishek on 10-09-2017.
 */

public class TwoArticles extends Article {
    private Article leftArticle;
    private Article rightArticle;

    public Article getLeftArticle() {
        return leftArticle;
    }

    public Article getRightArticle() {
        return rightArticle;
    }

    public TwoArticles(String id, String date, String author, String count, String title, String content, String featuredImage, String[] categories, String link, Article leftArticle, Article rightArticle) {

        super(id, date, author, count, title, content, featuredImage, categories, link);
        this.leftArticle = leftArticle;
        this.rightArticle = rightArticle;
    }
}
