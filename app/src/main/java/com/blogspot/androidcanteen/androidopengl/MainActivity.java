package com.blogspot.androidcanteen.androidopengl;

import android.app.ActivityManager;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import Engine.Input;
import Engine.PreMadeMeshes;
import Engine.Utils;
import Application.Application;
public class MainActivity extends AppCompatActivity {


    GLSurfaceView surview;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        boolean isOpenGL3 = checkOpenGL3();

        if (!isOpenGL3) {
            Toast.makeText(MainActivity.this, "No support for openGL 3 :(. Bye!", Toast.LENGTH_LONG).show();
            finish();
        }

        //Initialize utils first!
        Utils.Initialize(getResources(),this);
       surview = (GLSurfaceView) findViewById(R.id.surview);
       surview.setEGLContextClientVersion(3);

        surview.setRenderer(MainRenderer.getInstance());
        surview.setPreserveEGLContextOnPause(true);


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Input.getInstance().OnTouch(x,y);


            case MotionEvent.ACTION_MOVE:
                Input.getInstance().OnDrag(x,y);


            case MotionEvent.ACTION_UP:
                Input.getInstance().OnRelease(x,y);

        }
        return false;
    }


    boolean checkOpenGL3() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        boolean support = am.getDeviceConfigurationInfo().reqGlEsVersion >= 0x30000;

        return support;


    }

    @Override
    protected void onPause() {
        super.onPause();

        surview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        surview.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //   surview.onResume();

      /*  for(String s : PreMadeMeshes.allMeshes.keySet())
        {
            PreMadeMeshes.allMeshes.get(s).cleanMemory();
        }*/
    }
}
