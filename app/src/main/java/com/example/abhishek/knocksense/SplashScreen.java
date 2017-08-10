package com.example.abhishek.knocksense;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new PrefetchData().execute();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
            //// TODO: 05-08-2017 read which city and user then fetch accordingly

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            /*
             * Will make http call here This call will download required data
             * before launching the app
             * example:
             * 1. Downloading and storing in SQLite
             * 2. Downloading images
             * 3. Fetching and parsing the xml / json
             * 4. Sending device information to server
             * 5. etc.,
             */

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // TODO: 05-08-2017 app will go next page after fetching or timer
            // After completing http call
            // will close this activity and launch main activity
//            Intent i = new Intent(SplashScreen.this, MainActivity.class);
//            i.putExtra("now_playing", now_playing);
//            i.putExtra("earned", earned);
//            startActivity(i);

            // close this activity
            //finish();
        }

    }
}

/**
 * Async Task to make http call
 */

