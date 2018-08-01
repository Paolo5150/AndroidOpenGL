package Application;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import Engine.Scene;
import Engine.Utils;

/**
 * Created by Paolo on 25/06/2018.
 */

public class Application{




    private  Scene currentScene = null;



    private static Application instance;

    public static Application getInstance()
    {
        if(instance == null)
            instance = new Application();



        return  instance;
    }
    private Application()
    {

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

    public  Scene getCurrentScene() {
        return currentScene;
    }

    public  void setCurrentScene(Scene newScene) {
        if(currentScene!=null)
         currentScene.stop();

        currentScene = newScene;

        Utils.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Utils.activity,"Loading scene...",Toast.LENGTH_LONG).show();
            }
        });

        currentScene.loadScene();

       currentScene.start();
    }

}
