package com.example.abhishek.knocksense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;

public class SelectCityScreen extends AppCompatActivity {

    private static String selectedCity;

    public static String getSelectedCity() {
        return SelectCityScreen.selectedCity;
    }

    public static void setSelectedCity(String selectedCity) {
        SelectCityScreen.selectedCity = selectedCity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city_screen);
    }
    public void onCitySelected(View view){
        TextView textView = (TextView) view;
        selectedCity = textView.getText().toString();
       setSelectedCity(CitiesID.getCityId(selectedCity));


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.selected_city_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.saved_selected_city), getSelectedCity());
        editor.commit();
        GlobalLists.fireRefreshData(getApplicationContext(), ListNameConstants.CITY,false,getSelectedCity());



        Intent intent = new Intent(this, MainActivityScreen.class);
        startActivity(intent);
//// TODO: 19-08-2017 cities should be ordered alphabetically
    }
}
