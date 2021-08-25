package com.mycomet.cc916647.mycomet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class CometActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to find the width and of the ui screen
        //DisplayMatrix instance is used to get general information
        //about screen size and so on.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // next create a instance of window manager to communicate with it.
        WindowManager windowManager =
                (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        // tell this window manager to create a new window for drawing our objects
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;


        setContentView( new CometAnimation(this));
    }
}
