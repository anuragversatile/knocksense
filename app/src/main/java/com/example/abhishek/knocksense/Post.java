package com.example.abhishek.knocksense;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class Post extends AppCompatActivity {
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
        setContentView(R.layout.post);
        DateConverter dc=new DateConverter();
        final Bundle extras = getIntent().getExtras();





        ImageView iv=(ImageView)findViewById(R.id.featured);
        String feature=extras.getString("feature");
        Picasso.with(this).load(feature).into(iv);
        ImageView im=(ImageView)findViewById(R.id.avatar);
        TextView tx=(TextView)findViewById(R.id.title);
        String titles=extras.getString("title");
        if(titles.contains("&#8216;")) {
            String title = titles.replace("&#8216;", "'");
            tx.setText(title);
        }
        else if(titles.contains("&#8217;")) {
            String title = titles.replace("&#8217;", "'");
            tx.setText(title);
        }

        else if(titles.contains("&#038;")) {
            String title = titles.replace("&#038;", "&");
            tx.setText(title);
        }
        else {
            tx.setText(titles);
        }
        TextView tx1=(TextView)findViewById(R.id.date);
        TextView tx2=(TextView)findViewById(R.id.author);
        String dt=extras.getString("date");
        Log.e("thdhfjfdjfjdhfjfh","fudytsdffjtdyt"+extras.getString("feature"));

        tx1.setText(dc.getDate(dt)+" "+ dc.getMonth(dt)+ " "+dc.getYear(dt));

        for(Article arti:  GlobalLists.getAuthorList()) {
            String authorId = arti.getId();
            if (extras.getString("author").equals(authorId)) {
                tx2.setText(arti.getName());
                Log.e("thdhfjfdjfjdhfjfh", "fudytsdffjtdyt" + arti.getAuthorImage());
                Picasso.with(this).load(arti.getAuthorImage()).into(im);
                break;
            }
        }

        final String id = getIntent().getExtras().getString("id");


        content = (WebView)findViewById(R.id.contentPost);

        progressDialog = new ProgressDialog(Post.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String url = "http://www.knocksense.com/wp-json/wp/v2/posts/"+id+"?fields=title,content";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);

                mapContent = (Map<String, Object>) mapPost.get("content");
String test=mapContent.get("rendered").toString();

                WebSettings settings = content.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setLoadWithOverviewMode(true);
                settings.setUseWideViewPort(true);
                settings.setBuiltInZoomControls(false);
                settings.setDisplayZoomControls(false);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

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
                Toast.makeText(Post.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Post.this);
        rQueue.add(request);
    }
    public static String changedHeaderHtml(String htmlText) {

        String head = "<head><meta name=\"viewport\" content=\"width=device-width,  user-scalable=no\" /><style>img{max-width: 100%; width:auto; height: auto;}</style><style>iframe{max-width: 100%; width:auto; height: auto;}</style></head>";

        String closedTag = "</body></html>";
        String changeFontHtml = head + htmlText + closedTag;
        return changeFontHtml;
    }
}