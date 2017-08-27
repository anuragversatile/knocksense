package com.example.abhishek.knocksense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebViewScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle extras = getIntent().getExtras();
        String uri = extras.getString("uri");
        WebView myWebView = (WebView) findViewById(R.id.web_view);
        myWebView.loadUrl(uri);
    }
}
