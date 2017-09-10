package com.example.abhishek.knocksense;

/**
 * Created by Abhishek on 09-09-2017.
 */

public class CategoryId {
    public static final String BOLLYWOOD_ID = "7006";
    public static final String DINESENSE_ID = "765";
    public static final String ENTERTAINMENT_ID = "5";
    public static final String CULTURE_ID = "3";
    public static final String REST_OF_INDIA_ID = "629";
    public static final String KNOCKKNOCK_ID = "9";
    public static final String NEWS_ID = "14";
    public static final String SPORTS_ID = "16";
    public static final String TECHSENSE_ID = "17";
    public static final String WEREVIEW_ID = "19";
    public static final String YOURSPACE_ID = "522";
    public static final String HINDI_ID = "7005"; //ZERO POSTS

    public static String getCategoryId(String categoryId) {
        String id;
        switch (categoryId) {
            case BOLLYWOOD_ID:
                id = "BOLLYWOOD";
                break;
            case DINESENSE_ID:
                id = "DINESENSE";
                break;
            case ENTERTAINMENT_ID:
                id = "ENTERTAINMENT";
                break;
            case CULTURE_ID:
                id = "CULTURE";
                break;
            case KNOCKKNOCK_ID:
                id = "KNOCKKNOCK";
                break;
            case NEWS_ID:
                id = "NEWS";
                break;
            case SPORTS_ID:
                id = "SPORTS";
                break;
            case TECHSENSE_ID:
                id = "TECHSENSE";
                break;
            case WEREVIEW_ID:
                id = "WEREVIEW";
                break;
            case YOURSPACE_ID:
                id = "YOURSPACE";
                break;

            case HINDI_ID:
                id = "HINDI";
                break;
            default:
                id = REST_OF_INDIA_ID;
                break;
        }
        return id;
    }
}
