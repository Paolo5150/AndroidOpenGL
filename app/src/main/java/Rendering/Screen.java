package Rendering;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import Engine.Utils;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Screen {

    public static int SCREEN_WIDTH;
    public  static int SCREEN_HEIGHT;
    public static float SCREEN_WIDTH_DP;
    public  static float SCREEN_HEIGHT_DP;
    public static float aspectRatio;

    public static void Initialize(Activity a)
    {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
        aspectRatio = (float)SCREEN_WIDTH / SCREEN_HEIGHT;

        SCREEN_WIDTH_DP = convertPixelsToDp(SCREEN_WIDTH);
        SCREEN_HEIGHT_DP = convertPixelsToDp(SCREEN_HEIGHT);
    }

    public static float convertPixelsToDp(float px){
        Resources resources = Utils.resources;
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
