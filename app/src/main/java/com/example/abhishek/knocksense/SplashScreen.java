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
    private String selectedCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        selectedCityId = sharedPref.getString(getString(R.string.shared_preference_saved_city),"not selected");

        if (selectedCityId != "not selected") {
            //GlobalLists.fetchCityData(getApplicationContext(),selectedCityId);
        }
        GlobalLists.fetchHomeData(getApplicationContext());
//        GlobalLists.fetchCategoryData(getApplicationContext());
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i;
                // This method will be executed once the timer is over

                //if no city is selected goto SelectCityScreen
                if ("not selected".equals(selectedCityId)) {
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

