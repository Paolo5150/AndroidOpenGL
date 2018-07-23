package Components;

import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;

import Engine.GameObject;
import Rendering.Camera;
import Rendering.Screen;

public class CameraOrtho extends Camera {

    float left;
    float right;
    float bottom;
    float top;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public CameraOrtho(String name, GameObject o, float left, float right, float botom, float top, float near, float far) {
        super(name, o);

        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = botom;
        this.near = near;
        this.far = far;

        setOrthographic(left,right,bottom,top,near,far);
        cameraType = "Orthographic";


    }

    @Override
    public void updateProjection() {

        Matrix.orthoM(projM,0,left,right,bottom,top,near,far);
    }

    @Override
    public void OnScreenChanged() {

        left = -(float) Screen.SCREEN_WIDTH/2.0f;
        right = (float) Screen.SCREEN_WIDTH/2.0f;
        bottom = -(float) Screen.SCREEN_HEIGHT/2.0f;
        top = (float) Screen.SCREEN_HEIGHT/2.0f;
        updateProjection();

    }
}
