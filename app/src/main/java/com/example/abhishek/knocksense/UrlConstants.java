package com.example.abhishek.knocksense;

/**
 * Created by Abhishek on 19-08-2017.
 */

public class UrlConstants {
    // TODO: 20-08-2017 maybe just fetch data necessary for listView then when article has to be read fetch remaining
    //// TODO: 20-08-2017 use _embed to put author's name in response to fetch posts

    //featuredMedia has the id of the media
    private static final String allArticlesURL = "http://www.knocksense.com/wp-json/wp/v2/posts?per_page=25&fields=id,date,title,content,author,link,categories,better_featured_image,featured_media";
    private static final String specificCategoryOrCityArticlesURL = "http://www.knocksense.com/wp-json/wp/v2/categories/<__categoryID__>?per_page=25";
    private static final String specificAuthorArticlesURl = "http://www.knocksense.com/wp-json/wp/v2/posts?author=<__authorID__>&per_page=25&fields=id,date,title,content,author,featured_media,categories,better_featured_image";
    private static final String allCategoriesURL = "http://www.knocksense.com/wp-json/wp/v2/categories?fields=id,count";
    //private static String allCitiesURL = "http://www.knocksense.com/wp-json/wp/v2/categories?filter[category_name]=Cities&fields=id,count,link";
    //private static String allAuthorsURL = "http://www.knocksense.com/wp-json/wp/v2/users?fields=id,name";


    public static String getAllArticlesURL() {
        return allArticlesURL;
    }

    public static String getSpecificCategoryOrCityArticlesURL(String categoryId) {
        return specificCategoryOrCityArticlesURL.replace("<__categoryID__>",categoryId);
    }

    public static String getSpecificAuthorArticlesURl(String authorId) {
        return specificAuthorArticlesURl.replace("<__authorID__>",authorId);
    }

    public static String getAllCategoriesURL() {
        return allCategoriesURL;
    }
}
