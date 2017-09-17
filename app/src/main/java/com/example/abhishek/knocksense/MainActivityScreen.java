package com.example.abhishek.knocksense;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;
import com.example.abhishek.knocksense.components.ListNameConstants;

public class MainActivityScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnListFragmentInteractionListener, CityFragment.OnListFragmentInteractionListener{

    SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private int backButtonCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GlobalLists globalListInstance = (GlobalLists)getApplication();
        backButtonCount=0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.knocksenselogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 3000);
                        globalListInstance.fireRefreshData(getApplicationContext(),ListNameConstants.CITY,null);
                        globalListInstance.fireRefreshData(getApplicationContext(), ListNameConstants.HOME, null);
                        //// TODO: 09/09/17 refresh city too. Show loader till loading has not finished
                        Toast.makeText(MainActivityScreen.this, "refreshed!!!", Toast.LENGTH_SHORT).show();

                    }
                }
        );
       }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(backButtonCount >= 1)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
                backButtonCount++;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String categoryId=null;
        if(id==R.id.nav_Entertainment){
            categoryId=CategoryId.ENTERTAINMENT_ID;
        }
        else if(id==R.id.KnockKnock){
            categoryId=CategoryId.KNOCKKNOCK_ID;
        }
        else if(id==R.id.News){
            categoryId=CategoryId.NEWS_ID;
        }
        else if(id==R.id.Sports){
            categoryId=CategoryId.SPORTS_ID;
        }
        else if(id==R.id.YourSpace){
            categoryId=CategoryId.YOURSPACE_ID;
        }
        else if(id==R.id.TechSense){
            categoryId=CategoryId.TECHSENSE_ID;
        }
        else if(id==R.id.WeReview){
            categoryId=CategoryId.WEREVIEW_ID;
        }
        else if(id==R.id.DineSense){
            categoryId=CategoryId.DINESENSE_ID;
        }
        else if(id==R.id.nav_home){
            //// TODO: 10-09-2017
            categoryId="0";

        }
        else if(id==R.id.nav_city)
        { categoryId="00";

        }
if(categoryId.equals("0"))
{
    Intent intent =new Intent(this,MainActivityScreen.class);
    startActivity(intent);
}
else if(categoryId.equals("00"))
{
    Intent intent =new Intent(this,SelectCityScreen.class);
    startActivity(intent);
}
else {
    Intent intent = new Intent(this, CategoryOrAuthorScreen.class);
    Bundle bundle = new Bundle();
    bundle.putString("ID", categoryId);
    bundle.putString("TYPE", ListNameConstants.CATEGORY);
    intent.putExtras(bundle);
    startActivity(intent);
}
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;

    }
    private void displayView(int position) {
        fragment = null;
        String fragmentTags = "Entertainment";
        switch (position) {
            case 0:
                fragment= new SearchFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment, fragmentTags).commit();
        }
    }


    @Override
    public void onListFragmentInteraction(Article item) {
        Intent intent = new Intent(this, WebView.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",item.getId());
        bundle.putString("uri", item.getLink());
       bundle.putString("date",item.getDate());
        bundle.putString("author",item.getAuthor());
        bundle.putString("feature",item.getFeaturedImage());
        bundle.putString("title",item.getTitle());

        intent.putExtras(bundle);
        startActivity(intent);
    }


}
