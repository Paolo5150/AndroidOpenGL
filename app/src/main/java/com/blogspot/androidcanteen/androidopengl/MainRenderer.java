package com.blogspot.androidcanteen.androidopengl;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Application.Application;
import Engine.EngineTime;
import Engine.PreMadeMeshes;
import Engine.Utils;
import Rendering.Layer;
import Rendering.Lighting;
import Rendering.MaterialManager;
import Rendering.RenderingEngine;
import Rendering.Screen;
import Rendering.Camera;
import Scenes.TestScene;
import ShaderObjects.ShaderManager;

/**
 * Created by Paolo on 16/06/2018.
 */

public class MainRenderer implements GLSurfaceView.Renderer {


    float angle = 0;

    private static MainRenderer instance;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static MainRenderer getInstance()
    {
        if(instance == null)
            instance = new MainRenderer();

        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private MainRenderer()
    {}


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {


            GlobalVariables.logWithTag("Surface created");
            Screen.Initialize(Utils.activity);
            ShaderManager.Initialize(); //Call before MaterialManagerInitialize
            MaterialManager.Initialize();
            Layer.Initialize();
            PreMadeMeshes.Initialize();
            EngineTime.Initialize();
            Lighting.Initialize();

            RenderingEngine.getInstance().Initialize();

            Application.setCurrentScene(new TestScene());



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

        GlobalVariables.logWithTag("Surface changed");
        GLES20.glViewport(0,0,i,i1);

        Screen.Initialize(Utils.activity);

        //Update camera aspect ratio
       for(String s : Camera.getAllCameras().keySet())
       {
          if(Camera.getAllCameras().get(s).getCameraType().equals("Perspective"))
          {
              Camera.getAllCameras().get(s).setAspectRatio((float)Screen.SCREEN_WIDTH / (float)Screen.SCREEN_HEIGHT);
              Camera.getAllCameras().get(s).updateProjectionPerspective();
          }

          else
          {
              //Update orthographic
          }
       }




    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDrawFrame(GL10 gl10) {
        //Log.d(GlobalVariables.TAG,"Draw frame");

        Application.getInstance().Update();

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
       //


        RenderingEngine.getInstance().renderSceneUsingBatch(Application.getCurrentScene());

        EngineTime.Update();



    }
}
