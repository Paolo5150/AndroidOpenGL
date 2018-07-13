package com.blogspot.androidcanteen.androidopengl;


import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Application.Application;
import Engine.EngineTime;
import Engine.PreMadeMeshes;
import Engine.Utils;
import Rendering.Lighting;
import Rendering.Screen;
import Application.Camera;

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
    private void InitializeGL() {

        GLES20.glClearColor(0.5f,0.5f,0.5f,1);
        GLES30.glEnable(GLES20.GL_DEPTH_TEST);
       GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glFrontFace(GLES20.GL_CCW);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        if(!Application.running) {
            GlobalVariables.logWithTag("Surface created");
            Screen.Initialize(Utils.activity);

            PreMadeMeshes.Initialize();
            EngineTime.Initialize();
            Lighting.Initialize();
            InitializeGL();
            Application.getInstance().Start();
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

        GlobalVariables.logWithTag("Surface changed");
        GLES20.glViewport(0,0,i,i1);
        InitializeGL();
        Screen.Initialize(Utils.activity);

        //Update camera aspect ratio
       for(String s : Camera.getAllCameras().keySet())
       {
          if(Camera.getAllCameras().get(s).getType().equals("Perspective"))
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
      //  Log.d(GlobalVariables.TAG,"Draw frame");

        Application.getInstance().Update();

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Application.getInstance().Render();

        EngineTime.Update();



    }
}
