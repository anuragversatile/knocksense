package com.example.abhishek.knocksense;

/**
 * Created by Abhishek on 09-09-2017.
 */

public class CategoryId {
    public static final String BOLLYWOOD_ID = "7006";
    public static final String DINESENSE_ID = "765";
    public static final String ENTERTAINMENT_ID = "5";// //
    public static final String CULTURE_ID = "3";
    public static final String REST_OF_INDIA_ID = "629";// //
    public static final String KNOCKKNOCK_ID = "9";// //
    public static final String NEWS_ID = "14";
    public static final String SPORTS_ID = "16";
    public static final String TECHSENSE_ID = "17";// //
    public static final String WEREVIEW_ID = "19";
    public static final String YOURSPACE_ID = "5224";// //
    public static final String HINDI_ID = "7005"; //ZERO POSTS

    public static String getCategoryName(String categoryId) {
        String categoryName;
        switch (categoryId) {
            case BOLLYWOOD_ID:
                categoryName = "BOLLYWOOD";
                break;
            case DINESENSE_ID:
                categoryName = "DINESENSE";
                break;
            case ENTERTAINMENT_ID:
                categoryName = "ENTERTAINMENT";
                break;
            case CULTURE_ID:
                categoryName = "CULTURE";
                break;
            case KNOCKKNOCK_ID:
                categoryName = "KNOCK KNOCK";
                break;
            case NEWS_ID:
                categoryName = "NEWS";
                break;
            case SPORTS_ID:
                categoryName = "SPORTS";
                break;
            case TECHSENSE_ID:
                categoryName = "TECHSENSE";
                break;
            case WEREVIEW_ID:
                categoryName = "WEREVIEW";
                break;
            case YOURSPACE_ID:
                categoryName = "YOURSPACE";
                break;

            case HINDI_ID:
                categoryName = "HINDI";
                break;
            default:
                categoryName = "Experience India";
                break;
        }
        return categoryName;
    }
}
