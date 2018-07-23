package com.blogspot.androidcanteen.androidopengl;

import android.app.ActivityManager;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PointF;
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

import java.util.ArrayList;
import java.util.HashMap;

import Engine.Input;
import Engine.PreMadeMeshes;
import Engine.Utils;
import Application.Application;
public class MainActivity extends AppCompatActivity {


    GLSurfaceView surview;
    HashMap<Integer, PointF> pointers;


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

        pointers = new HashMap<>();

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

        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);





        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:


                //Reset
                Input.GUITouched = false;


                PointF p = new PointF();
                p.x = event.getX(pointerIndex);
                p.y = event.getY(pointerIndex);
                pointers.put(pointerID,p);



                Input.getInstance().OnTouch((int)p.x,(int)p.y,pointerID);

                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                PointF p2 = new PointF();
                p2.x = event.getX(pointerIndex);
                p2.y = event.getY(pointerIndex);
                pointers.put(pointerID,p2);
                Input.getInstance().OnTouch((int)p2.x,(int)p2.y,pointerID);

                break;



            case MotionEvent.ACTION_MOVE:

                for (int i = 0; i < event.getPointerCount(); i++) {
                 //   GlobalVariables.logWithTag("Getting point with id " + event.getPointerId(i));
                    PointF point = pointers.get(event.getPointerId(event.getActionIndex()));

                    if (point != null) {

                        point.x = event.getX(i);
                        point.y = event.getY(i);
                        Input.getInstance().OnDrag((int)point.x,(int)point.y,pointerID);
                    }
                }


                break;




            case MotionEvent.ACTION_UP:

                Input.getInstance().OnRelease(0,0,pointerID);

                pointers.remove(pointerID);

                break;

            case MotionEvent.ACTION_POINTER_UP:

                Input.getInstance().OnRelease(0,0,pointerID);

                pointers.remove(pointerID);

                break;

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
