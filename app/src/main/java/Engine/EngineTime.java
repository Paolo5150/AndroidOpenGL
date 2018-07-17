package Engine;

import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import javax.microedition.khronos.opengles.GL;

public class EngineTime {

    public static void Initialize()
    {
        currentTime = System.currentTimeMillis();
        previousTime = System.currentTimeMillis();
        FPS = 0;
        timer = 0;

    }

    public static void Update()
    {

        currentTime = System.currentTimeMillis();
        deltaTimeSeconds = (float)(currentTime - previousTime);
        deltaTimeSeconds/=1000.0f;
        float left = deltaTimeSeconds / 1000.0f;
        previousTime = currentTime;

        timer += deltaTimeSeconds;
        FPS++;
        if(timer>=1.0f)
        {
          // Log.d(GlobalVariables.TAG,"FPS: " + FPS);
            timer = 0;
            FPS = 0;
        }


    }

    public static float getDeltaTimeSeconds() {
        return deltaTimeSeconds;
    }

    private static long currentTime;
    private static long previousTime;
    private static float deltaTimeSeconds;
    private static int FPS;
    private static float timer;
}
