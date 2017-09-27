package com.example.abhishek.knocksense;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.List;
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




Font font=new Font();
        ImageView iv=(ImageView)findViewById(R.id.featured);
        String feature=extras.getString("feature");
        Picasso.with(this).load(feature).into(iv);

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

                break;
            }
        }
        ImageView sharingButton = (ImageView)findViewById(R.id.article_item_row_more);

        final String links=extras.getString("uri");
final Post that=this;
      sharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 26-08-2017 save and share functionality
                                //handle menu1 click
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_TEXT,links );

                                try {
                                   that.startActivity(Intent.createChooser(shareIntent, "Share via"));
                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                }

            }


        });
        ImageView sharing = (ImageView)findViewById(R.id.facebook);
        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();

                Intent facebookIntent = getShareIntent("facebook",  links);
                if(facebookIntent != null)
                    targetedShareIntents.add(facebookIntent);


                Intent chooser = Intent.createChooser(targetedShareIntents.remove(0), "Facebook");

                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                startActivity(chooser);
            }
        });
        ImageView sharingb = (ImageView)findViewById(R.id.facebookshare);
        sharingb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();

                Intent facebookIntent = getShareIntent("facebook",  links);
                if(facebookIntent != null)
                    targetedShareIntents.add(facebookIntent);


                Intent chooser = Intent.createChooser(targetedShareIntents.remove(0), "Facebook");

                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                startActivity(chooser);
            }
        });
        ImageView sharingc = (ImageView)findViewById(R.id.whatsappshare);
        sharingc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();

                Intent facebookIntent = getShareIntent("com.whatsapp",  links);
                if(facebookIntent != null)
                    targetedShareIntents.add(facebookIntent);


                Intent chooser = Intent.createChooser(targetedShareIntents.remove(0), "Facebook");

                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                startActivity(chooser);
            }
        });
        ImageView sharingd = (ImageView)findViewById(R.id.allshare);
        sharingd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,links );

                try {
                    getApplicationContext().startActivity(Intent.createChooser(shareIntent, "Share via"));
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            }


        });

        ImageView sharing1 = (ImageView)findViewById(R.id.whatsapp);
        sharing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();

                Intent whatsAppIntent = getShareIntent("com.whatsapp",  links);
                if(whatsAppIntent != null)
                    targetedShareIntents.add(whatsAppIntent);


                Intent chooser = Intent.createChooser(targetedShareIntents.remove(0), "WhatsApp");

                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                startActivity(chooser);
            }
        });


        ImageView back = (ImageView)findViewById(R.id.backs);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, MainActivityScreen.class);

                startActivity(intent);
            }
        });

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
                Toast.makeText(Post.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Post.this);
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
    private Intent getShareIntent(String type,  String text)
    {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = this.getPackageManager().queryIntentActivities(share, 0);
        System.out.println("resinfo: " + resInfo);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type) ) {
                    share.putExtra(Intent.EXTRA_TEXT,     text);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return null;

            return share;
        }
        return null;
    }


}