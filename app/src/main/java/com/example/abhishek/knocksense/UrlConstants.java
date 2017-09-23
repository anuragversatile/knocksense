package com.example.abhishek.knocksense;

/**
 * Created by Abhishek on 19-08-2017.
 */

public class UrlConstants {
    //// TODO: 20-08-2017 use _embed to put author's name in response to fetch posts

    private static String specificCategoryOrCityArticlesURL = "http://www.knocksense.com/wp-json/wp/v2/posts/?categories=<__categoryID__>&per_page=<__number_of_posts__>&fields=id,date,title,author,link,categories,better_featured_image,featured_media";
    private static String searchURL="http://www.knocksense.com/wp-json/wp/v2/posts/?search=<__search__>&per_page=25&fields=id,date,title,author,link,categories,better_featured_image,featured_media";


  private static String allAuthorsURL = "http://www.knocksense.com/wp-json/wp/v2/users?fields=id,name,avatar_urls";

    public static String getAllAuthorsURL() {
        return allAuthorsURL;
    }

    public static void setAllAuthorsURL(String allAuthorsURL) {
        UrlConstants.allAuthorsURL = allAuthorsURL;
    }


    public static String getSpecificCategoryOrCityArticlesURL(String categoryId) {
        return specificCategoryOrCityArticlesURL.replace("<__categoryID__>",categoryId).replace("<__number_of_posts__>",String.valueOf(25));

    }

    public static String getSpecificCategoryOrCityArticlesURL(String categoryId, int numberOfPosts) {
        return specificCategoryOrCityArticlesURL.replace("<__categoryID__>",categoryId).replace("<__number_of_posts__>",String.valueOf(numberOfPosts));
    }

    public static String getSearchURL(String searchText){
        return searchURL.replace("<__search__>",searchText);
    }
}


    //    private static final String specificAuthorArticlesURl = "http://www.knocksense.com/wp-json/wp/v2/posts?author=<__authorID__>&per_page=25&fields=id,date,title,content,author,featured_media,categories,better_featured_image";
//    private static final String allCategoriesURL = "http://www.knocksense.com/wp-json/wp/v2/categories?fields=id,count,name";
//    private static final String articleContentURL = "http://www.knocksense.com/wp-json/wp/v2/posts/<__postID__>?fields=id,content";
    //private static final String allArticlesAfterDate="http://www.knocksense.com/wp-json/wp/v2/posts?per_page=25&fields=id,date,title,author,link,categories,better_featured_image,featured_media&filter[date_query][after]=<__timeStamp__>";
    //private static String allCitiesURL = "http://www.knocksense.com/wp-json/wp/v2/categories?filter[category_name]=Cities&fields=id,count,link";
//    private static String allAuthorsURL = "http://www.knocksense.com/wp-json/wp/v2/users?fields=id,name";
//    private static String allArticlesBeforeDateURL = "https://www.knocksense.com/wp-json/wp/v2/posts?per_page=50&orderby=date&order=asc&before=<__date__>&fields=id,date,title,author,link,categories,better_featured_image,featured_media";
//    private static String specificCategoryOrCityArticlesBeforeDateURL = "http://www.knocksense.com/wp-json/wp/v2/posts/?categories=<__categoryID__>&per_page=25&orderby=date&order=asc&before=<__date__>&fields=id,date,title,author,link,categories,better_featured_image,featured_media";
//    private static String searchUrl = "http://www.knocksense.com/wp-json/wp/v2/posts?search=<__search__>&per_page=25&fields=id,date,title";
//    private static final String allArticlesURL = "http://www.knocksense.com/wp-json/wp/v2/posts?per_page=25&fields=id,date,title,author,link,categories,better_featured_image,featured_media";
