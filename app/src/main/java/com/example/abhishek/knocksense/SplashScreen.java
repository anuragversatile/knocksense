package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.abhishek.knocksense.components.GlobalLists;

public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        selectedCity = sharedPref.getString(getString(R.string.shared_preference_saved_city), "not selected");

        if (selectedCity != "not selected") {
            GlobalLists.fetchCityData(getApplicationContext(), CitiesID.getCityId(selectedCity), null);
        }
        //// TODO: 27-08-2017 change this
        GlobalLists.fetchCityData(getApplicationContext(), "630", null);
        GlobalLists.fetchHomeData(getApplicationContext(), null, null);
        GlobalLists.fetchCategoryData(getApplicationContext(), null);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i;
                // This method will be executed once the timer is over

                //if no city is selected goto SelectCityScreen
                if ("not selected".equals(selectedCity)) {
                    i = new Intent(SplashScreen.this, SelectCityScreen.class);
                }
                else{
                    i= new Intent(SplashScreen.this, MainActivityScreen.class);
                }

                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}

