package com.example.abhishek.knocksense.components;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhishek on 15-08-2017.
 */

public class Article {

    private String id, date, author, count;

    @SerializedName("title.rendered")
    private String title;

    @SerializedName("content.rendered")
    private String content;

    @SerializedName("better_featured_image.source_url")
    private String featuredImage;
    private String link;
    private String[] categories;

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public Article(String id, String date, String author,  String count, String title, String content, String featuredImage, String[] categories, String link) {

        this.id = id;
        this.count = count;
        this.date = date;
        this.author = author;
        this.title = title;
        this.content = content;
        this.featuredImage = featuredImage;
        this.link = link;
        this.categories=categories;

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }


}