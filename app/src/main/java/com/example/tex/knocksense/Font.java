package com.example.tex.knocksense;

/**
 * Created by anuragdwivedi on 07/09/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font {

    public void setFont(Context _context, TextView textView) {
        Typeface poppinsBold = Typeface.createFromAsset(_context.getAssets(), "fonts/Poppins-Bold.ttf");
        textView.setTypeface(poppinsBold);
    }
    public void setFont1(Context _context, TextView textView) {
        Typeface poppinsReg = Typeface.createFromAsset(_context.getAssets(), "fonts/Poppins-Regular.ttf");
        textView.setTypeface(poppinsReg);
    }
    public void setFont2(Context _context, TextView textView) {
        Typeface roboto = Typeface.createFromAsset(_context.getAssets(), "fonts/RobotoLight.ttf");
        textView.setTypeface(roboto);
    }
    public  void setFont3(Context _context,TextView textView)
    {
        Typeface robotobold=Typeface.createFromAsset(_context.getAssets(),"fonts/Roboto-Bold.ttf");
        textView.setTypeface(robotobold);/*
        android:shadowColor="#000000"
        android:shadowDx="6"
        android:shadowDy="6"
        android:shadowRadius="6"
        for shadow in xmllayout*/

/*
        app:layout_behavior="@string/appbar_scrolling_view_behavior"

        this is removed from city fragment layout file hope it solves the scrolling problem
*/

    }
}
