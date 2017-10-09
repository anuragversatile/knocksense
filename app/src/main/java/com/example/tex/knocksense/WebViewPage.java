package com.example.tex.knocksense;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by anuragdwivedi on 21/09/17.
 */

public class WebViewPage extends AppCompatActivity {
    TextView title;
    WebView content;
    ProgressDialog progressDialog;
    Gson gson;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.tex.knocksense.R.layout.webviewpage);
        Toolbar toolbar = (Toolbar) findViewById(com.example.tex.knocksense.R.id.my_toolbar_webViewPage);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(com.example.tex.knocksense.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle extras = getIntent().getExtras();





        TextView tx=(TextView)findViewById(com.example.tex.knocksense.R.id.titlePage);
        String titles=extras.getString("title");
        tx.setText(titles);

        final String id = getIntent().getExtras().getString("id");


        content = (WebView)findViewById(com.example.tex.knocksense.R.id.contentPage);

        progressDialog = new ProgressDialog(WebViewPage.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String url = "http://www.knocksense.com/wp-json/wp/v2/pages/"+id+"?fields=title,content";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);

                mapContent = (Map<String, Object>) mapPost.get("content");
                String test=mapContent.get("rendered").toString();

                WebSettings settings = content.getSettings();
                settings.setJavaScriptEnabled(true);
                //     settings.setLoadWithOverviewMode(true);
                //   settings.setUseWideViewPort(true);
                settings.setBuiltInZoomControls(false);
                settings.setDisplayZoomControls(false);
                //     settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

                content.setWebChromeClient(new WebChromeClient());
                String changeFontHtml =    changedHeaderHtml(test);
                content.loadDataWithBaseURL(null, changeFontHtml,
                        "text/html", "UTF-8", null);
                // title.setText(mapTitle.get("rendered").toString());
                //   content.loadData(mapContent.get("rendered").toString(),"text/html","UTF-8");

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(WebViewPage.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(WebViewPage.this);
        rQueue.add(request);
    }
    public  String changedHeaderHtml(String htmlText) {
        Display display = getWindowManager().getDefaultDisplay();
        int width=display.getWidth();

        String head = "<head><meta name=\"viewport\" content=\"width=device-width,  user-scalable=no\" /><style>img{display:inline;max-width: 100%; width:auto; height: auto;}</style><style>iframe{max-width: 100%; width:auto; height: auto;}</style></head>";

        String closedTag = "</body></html>";
        String changeFontHtml = head + htmlText + closedTag;
        return changeFontHtml;
    }



}
