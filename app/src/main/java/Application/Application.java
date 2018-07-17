package Application;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import Engine.Input;
import Engine.Scene;
import Math.Vector2f;
import Rendering.Lighting;
import Rendering.Screen;

/**
 * Created by Paolo on 25/06/2018.
 */

public class Application implements IInteractionListener{

    private Activity activity;
    public static boolean running;



    private static Scene currentScene = null;


    private static Application instance;

    public static Application getInstance()
    {
        if(instance == null)
            instance = new Application();

        running = true;

        return  instance;
    }
    private Application()
    {
        Input.getInstance().addInteractionListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Start()
    {


        currentScene.start();


    }

    public void Update()
    {

        currentScene.update();
        currentScene.lateUpdate();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Render()
    {

        currentScene.render();

    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        currentScene.stop();
        Application.currentScene = currentScene;
        currentScene.start();
    }

    @Override
    public void OnInteract() {





    }
}
