package com.example.abhishek.knocksense;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.example.abhishek.knocksense.components.Article;
import com.example.abhishek.knocksense.components.GlobalLists;

public class MainActivityScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnListFragmentInteractionListener, CityFragment.OnListFragmentInteractionListener {

    SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                        }, 5000);

//                        Fragment homeViewFragment = viewPagerAdapter.getItem(0);
//                        Fragment cityViewFragment = viewPagerAdapter.getItem(1);
                        Fragment homeViewFragment = getSupportFragmentManager().findFragmentByTag("home_fragment");
                        Fragment cityViewFragment = getSupportFragmentManager().findFragmentByTag("city_fragment");
                        //Fragment homeViewFragment=null, cityViewFragment=null;
                        RecyclerView homeRecyclerView;
                        RecyclerView cityRecyclerView;
                        RecyclerView.Adapter homeAdapter=null;
                        RecyclerView.Adapter cityAdapter=null;
                        if(homeViewFragment!=null){
                            homeRecyclerView = (RecyclerView) homeViewFragment.getView().findViewById(R.id.home_fragment_list);
                            if(homeRecyclerView!=null && homeRecyclerView.getAdapter()!=null){
                                homeAdapter = homeRecyclerView.getAdapter();
                            }
                        }
                        if(cityViewFragment!=null){
                            cityRecyclerView = (RecyclerView)cityViewFragment.getView().findViewById(R.id.city_fragment_list);
                            if(cityRecyclerView!=null && cityRecyclerView.getAdapter()!=null){
                                homeAdapter = cityRecyclerView.getAdapter();
                            }
                        }
                        if(homeAdapter==null){
                            Toast.makeText(MainActivityScreen.this, "null :(", Toast.LENGTH_SHORT).show();
                        }
                        GlobalLists.fetchHomeData(getApplicationContext(), null, homeAdapter);
                        //// TODO: 27/08/17 change this
                        GlobalLists.fetchCityData(getApplicationContext(),"630",cityAdapter);
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
            super.onBackPressed();
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void displayView(int position) {
        fragment = null;
        String fragmentTags = "";
        switch (position) {
            case 0:
                fragment=new SearchFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
           // fragmentManager.beginTransaction().replace(R.id.content_frame,fragment, fragmentTags).commit();
        }
    }


    @Override
    public void onListFragmentInteraction(Article item) {
        Toast.makeText(this, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WebViewScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("uri", item.getLink());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
