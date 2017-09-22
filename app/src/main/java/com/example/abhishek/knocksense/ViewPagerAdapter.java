package com.example.abhishek.knocksense;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Abhishek on 19-08-2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static String cities;

    public static String getCities() {
        return cities;
    }

    public static void setCities(String cities) {
        ViewPagerAdapter.cities = cities;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = HomeFragment.newInstance(1);
        }
        else if (position == 1)
        {
            fragment = CityFragment.newInstance(1);

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Home";
        }
        else if (position == 1)
        {
            title =getCities();
            Log.e("adscasv","svdssdfbsd" +getCities());
        }
        return title;
    }
    public static void getCity(String city)
    {
        setCities(city);

    }
}
