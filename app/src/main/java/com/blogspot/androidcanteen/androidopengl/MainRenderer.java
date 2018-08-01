package com.blogspot.androidcanteen.androidopengl;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.RequiresApi;


import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Application.Application;
import Components.CameraPerspective;
import Engine.EngineTime;
import Engine.PreMadeMeshes;
import Engine.Utils;
import Listeners.IScreenChangeListener;
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

    private static ArrayList<IScreenChangeListener> screenChangeListeners;


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
    {
        screenChangeListeners = new ArrayList<>();
    }


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


            Application.getInstance().setCurrentScene(new TestScene());



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

        GlobalVariables.logWithTag("Surface changed");
        GLES20.glViewport(0,0,i,i1);

        Screen.Initialize(Utils.activity);

        for(IScreenChangeListener l : screenChangeListeners)
            l.OnScreenChanged();

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDrawFrame(GL10 gl10) {
        //Log.d(GlobalVariables.TAG,"Draw frame");

        Application.getInstance().Update();

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
       //


        RenderingEngine.getInstance().renderSceneUsingBatch(Application.getInstance().getCurrentScene());
        RenderingEngine.getInstance().clearMap(); //IMPORTANT, call here and not in render method. Frame buffer uses render method to render scene, so map cannot be cleared there

        EngineTime.Update();



    }

    public void registerScreenChangeListener(IScreenChangeListener l)
    {
        screenChangeListeners.add(l);
    }
}
