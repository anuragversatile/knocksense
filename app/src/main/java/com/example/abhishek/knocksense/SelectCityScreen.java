package com.example.abhishek.knocksense;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SelectCityScreen extends AppCompatActivity {

    private String selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city_screen);
    }
    public void onCitySelected(View view){
        TextView textView = (TextView) view;
        selectedCity = textView.getText().toString();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.shared_preference_saved_city),selectedCity);
        editor.commit();

        Intent intent = new Intent(this, MainActivityScreen.class);
        startActivity(intent);
//// TODO: 19-08-2017 cities should be ordered alphabetically
        //TODO: start async fetching for selected city
    }
}
