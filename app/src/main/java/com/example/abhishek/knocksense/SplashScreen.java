package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;

public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private static final String NOT_SELECTED = "not selected";
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.selected_city_file_key), MODE_PRIVATE);
        selectedCity = sharedPreferences.getString(getString(R.string.saved_selected_city),NOT_SELECTED);
        Log.e("SPLASH SCREEN", "selectedCity read="+selectedCity,null);

        if (selectedCity != NOT_SELECTED) {
            GlobalLists.fireRefreshData(getApplicationContext(), ListNameConstants.CITY, false,  CitiesID.getCityId(selectedCity));
        }


        GlobalLists.fireRefreshData(getApplicationContext(),ListNameConstants.HOME, false, null);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i;
                // This method will be executed once the timer is over

                //if no city is selected goto SelectCityScreen
                if (NOT_SELECTED.equals(selectedCity)) {
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

