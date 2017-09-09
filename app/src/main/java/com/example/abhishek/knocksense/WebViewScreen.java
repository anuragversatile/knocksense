package com.example.abhishek.knocksense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle extras = getIntent().getExtras();
        String uri = extras.getString("uri");
        final String post = "post-"+extras.getString("id");
        final WebView webView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    setTitle(R.string.app_name);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl(
                        "javascript:(function() { " +
                                "var saved = document.getElementById('"+post+"');" +
                                "var elms = document.body.childNodes;" +
                                "while (elms.length) document.body.removeChild(elms[0]);" +
                                "document.body.appendChild(saved);" +
                                "})()");
                //// TODO: 07/09/17 set alpha from js
                webView.setAlpha(1);
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        webView.loadUrl(uri);

    }
}

/*
* document.getElementsByClassName("article")
* document.getElementById("post-11120")
*         webView.loadData ("<p>Lucknowites couldn’t even get over the news of Lucknow metro yet, and here is another Yorker coming straight from the International Cricket Stadium in the city. A day after the metro finally opens for the public, people of Lucknow can enjoy the maiden match of Duleep Trophy on Lucknow’s red soil.</p>\\n<p>One after the other, new developments in the city are only building up new excitement amongst its people. In India, cricket is considered a religion and coming up of the international cricket stadium, in the city is the best news ever for all the cricket fans here.</p>\\n<p>This is how the Lucknow&#8217;s cricket stadium looks!</p>\\n<p><iframe style=\\\"border: none; overflow: hidden;\\\" src=\\\"https://www.facebook.com/plugins/post.php?href=https%3A%2F%2Fwww.facebook.com%2FLucknowInternationalCricketStadium%2Fposts%2F1379179342203681&amp;width=500\\\" width=\\\"500\\\" height=\\\"597\\\" frameborder=\\\"0\\\" scrolling=\\\"no\\\"></iframe></p>\\n<!-- WP QUADS Content Ad Plugin v. 1.5.9 -->\\n<div class=\\\"quads-location quads-ad3\\\" id=\\\"quads-ad3\\\" style=\\\"float:none;margin:10px;\\\">\\n<script async src=\\\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\\\"></script>\\n<!-- Middle of Post -->\\n<ins class=\\\"adsbygoogle\\\"\\n     style=\\\"display:block\\\"\\n     data-ad-client=\\\"ca-pub-1928181993792414\\\"\\n     data-ad-slot=\\\"9562537285\\\"\\n     data-ad-format=\\\"auto\\\"></ins>\\n<script>\\n(adsbygoogle = window.adsbygoogle || []).push({});\\n</script>\\n</div>\\n\\n<p>People who used to travel to other cities for watching live matches and see their favourite team take away the wickets, can now enjoy matches in their homeland.</p>\\n<p>The stadium has a seating capacity of around 50,000 people and is spread over an area of 137 acres, out of which 71 acres is for sports infrastructure project.</p>\\n<p>The authorities have decided to distribute free match passes on first come first serve basis for two out of its ten stands. India Red led by Abhinav Kund and India Green led by Parthiv Patel’s will be competing with each other <span data-term=\\\"goog_33104059\\\">on Thursday</span>, in the first match on this field, <span data-term=\\\"goog_33104060\\\">on Thursday</span>.</p>\\n<p><span style=\\\"font-size: 8pt;\\\">Cover image <a href=\\\"http://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2017/09/05/Pictures/stadium-lucknow_fd4c9ec2-925b-11e7-b219-301a51d93d0d.jpg\\\" target=\\\"_blank\\\" rel=\\\"noopener\\\">source</a></span></p>\\n\\n<!-- WP QUADS Content Ad Plugin v. 1.5.9 -->\\n<div class=\\\"quads-location quads-ad2\\\" id=\\\"quads-ad2\\\" style=\\\"float:none;margin:10px 0 10px 0;text-align:center;\\\">\\n<script async src=\\\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\\\"></script>\\n<!-- Before last para -->\\n<ins class=\\\"adsbygoogle\\\"\\n     style=\\\"display:block\\\"\\n     data-ad-client=\\\"ca-pub-1928181993792414\\\"\\n     data-ad-slot=\\\"1975551687\\\"\\n     data-ad-format=\\\"auto\\\"></ins>\\n<script>\\n(adsbygoogle = window.adsbygoogle || []).push({});\\n</script>\\n</div>\\n\\n<div style=\\\"font-size:0px;height:0px;line-height:0px;margin:0;padding:0;clear:both\\\"></div>",
*         "text/html",null);
*
*
*         var saved = document.getElementById('post-10720');
var elms = document.body.childNodes;
var uselessReached = false;
var savedChildren = saved.childNodes;
for (var i = 0; i < savedChildren.length; i++) {
  if (uselessReached === true) {
    saved.removeChild(savedChildren[i]);
  }
  else if (savedChildren[i].className==="snax") {
    uselessReached = true;
    saved.removeChild(savedChildren[i]);
  }
}
while (elms.length) document.body.removeChild(elms[0]);
document.body.appendChild(saved);

*/