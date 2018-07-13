package com.blogspot.androidcanteen.androidopengl;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Test extends GLSurfaceView implements Runnable{

    private SurfaceHolder holder;

    public Test(Context context) {
        super(context);

        holder = getHolder();


    }

    @Override
    public void run() {

        Canvas canvas;

        GLES20.glClearColor(1,1,1,1);

        if(holder.getSurface().isValid())
        {
            canvas = holder.lockCanvas();

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            holder.unlockCanvasAndPost(canvas);

        }

    }
}
