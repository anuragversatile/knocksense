package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;

public class SelectCityScreen extends AppCompatActivity {

    private static String selectedCityId;

    public static String getSelectedCityId() {
        return SelectCityScreen.selectedCityId;
    }

    public static void setSelectedCityId(String selectedCityId) {
        SelectCityScreen.selectedCityId = selectedCityId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.knocksenselogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void onCitySelected(View view){
        GlobalLists globalListInstance = (GlobalLists)getApplication();
        TextView textView = (TextView) view;
        String selectedCity = textView.getText().toString().toUpperCase();
       setSelectedCityId(CitiesID.getCityId(selectedCity));


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.selected_city_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.saved_selected_city), getSelectedCityId());
        editor.apply();


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.selected_city_file_key), Context.MODE_PRIVATE);
        Log.d("AFTER_SAVE", "CITY_ID "+sharedPref.getString(getString(R.string.saved_selected_city), "SHOULD NOT GO HERE!!!!!!!!"));


        globalListInstance.fireRefreshData(this, ListNameConstants.CITY, getSelectedCityId());



        Intent intent = new Intent(this, MainActivityScreen.class);
        startActivity(intent);
//// TODO: 19-08-2017 cities should be ordered alphabetically
    }

}
