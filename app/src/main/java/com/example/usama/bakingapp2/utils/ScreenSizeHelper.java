package com.example.usama.bakingapp2.utils;


import android.content.Context;
import android.content.res.Configuration;

public class ScreenSizeHelper {
    public static final int NORMAL = 1;
    public static final int LARGE = 2;

    public static int screenSize(Context context) {
        int screenSize = 0;
        if ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {

            screenSize = LARGE;
        } else if ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {

            screenSize = NORMAL;
        }
        return screenSize;
    }
}
