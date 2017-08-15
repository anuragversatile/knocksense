package com.example.abhishek.knocksense.components;

/**
 * Created by Abhishek on 15-08-2017.
 */

public class Article {
    private String id, date, author, title, content, featuredImage, articleImages[], imageAltText[];

    //TODO id, date, articleImages data types; imageAltText maybe [][] to hold image id and alt text
    public Article() {
    }

    public Article(String id, String date, String author, String title, String content, String featuredImage, String[] articleImages, String imageAltText[]) {

        this.id = id;
        this.date = date;
        this.author = author;
        this.title = title;
        this.content = content;
        this.featuredImage = featuredImage;
        this.articleImages = articleImages;
        this.imageAltText = imageAltText;
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

    public String[] getArticleImages() {
        return articleImages;
    }

    public void setArticleImages(String[] articleImages) {
        this.articleImages = articleImages;
    }

    public String[] getImageAltText() {
        return imageAltText;
    }

    public void setImageAltText(String imageAltText[]) {
        this.imageAltText = imageAltText;
    }
}
