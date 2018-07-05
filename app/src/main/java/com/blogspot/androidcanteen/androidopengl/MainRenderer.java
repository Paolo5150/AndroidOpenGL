package com.blogspot.androidcanteen.androidopengl;


import android.app.Activity;
import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;
import android.renderscript.Matrix4f;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import Application.Application;
import Application.Camera;
import Rendering.Screen;
import Rendering.Shader;
import Math.Vertex;
import Rendering.ShaderManager;

/**
 * Created by Paolo on 16/06/2018.
 */

public class MainRenderer implements GLSurfaceView.Renderer {


    private Activity activity;
    private Context context;




    Application app;

    float angle = 0;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public MainRenderer(Context context, Activity act)
    {
        this.context = context;
        this.activity = act;
        Screen.Initialize(activity);



        app = new Application(act);





    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

       // Log.d(GlobalVariables.TAG,"Surface created");
        GLES20.glClearColor(0,0,0,1);

        ShaderManager.Initialize(activity);
        ShaderManager.CreateShader(activity,"Basic", R.raw.basic_vertex, R.raw.basic_fragment);

        app.Start();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
       // Log.d(GlobalVariables.TAG,"Surface changed");
        GLES20.glViewport(0,0,i,i1);

        Screen.Initialize(activity);



    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onDrawFrame(GL10 gl10) {
      //  Log.d(GlobalVariables.TAG,"Draw frame");

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);



        app.Update();
        app.Render();



    }
}
