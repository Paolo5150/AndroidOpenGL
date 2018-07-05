package Rendering;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Screen {

    public static int SCREEN_WIDTH;
    public  static int SCREEN_HEIGHT;

    public static void Initialize(Activity a)
    {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
    }
}
