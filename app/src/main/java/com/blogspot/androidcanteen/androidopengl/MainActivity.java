package com.blogspot.androidcanteen.androidopengl;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import Application.GameObject;

public class MainActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean isOpenGL3 = checkOpenGL3();

        if (!isOpenGL3) {
            Toast.makeText(MainActivity.this, "No support for openGL 3 :(. Bye!", Toast.LENGTH_LONG).show();
            finish();
        }

        GLSurfaceView surview = (GLSurfaceView) findViewById(R.id.surview);
        surview.setEGLContextClientVersion(3);
        surview.setRenderer(new MainRenderer(MainActivity.this, this));


    }


    boolean checkOpenGL3() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        boolean support = am.getDeviceConfigurationInfo().reqGlEsVersion >= 0x30000;

        return support;


    }

}
