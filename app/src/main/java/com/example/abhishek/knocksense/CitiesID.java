package com.example.abhishek.knocksense;

import android.util.Log;

/**
 * Created by Abhishek on 27-08-2017.
 */

public class CitiesID {
    public static final String JAIPUR_ID = "634";
    public static final String GOA_ID = "637";
    public static final String NEW_DELHI_ID = "641";
    public static final String LUCKNOW_ID = "630";
    public static final String REST_OF_INDIA_ID = "629";

    public static String getCityId(String cityName) {
        String id;
        switch (cityName.toUpperCase()) {
            case "JAIPUR":
                id = JAIPUR_ID;
                break;
            case "GOA":
                id = GOA_ID;
                break;
            case "NEW DELHI":
                id = NEW_DELHI_ID;
                break;
            case "LUCKNOW":
                id = LUCKNOW_ID;
                break;
            default:
                id = REST_OF_INDIA_ID;
                break;
        }
        Log.e("SWITCH CITY_ID", "getCityId="+id);
        return id;
    }
}
