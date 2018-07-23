package Components;

import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;

import Engine.GameObject;
import Rendering.Camera;
import Rendering.Screen;

public class CameraPerspective extends Camera {

    private float FOV;
    private float aspectRatio;
    private float near;
    private float far;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public CameraPerspective(String name, GameObject o, float FOV, float ratio, float near, float far) {
        super(name, o);
        setPerspective(FOV,ratio,near,far);
        cameraType = "Perspective";
    }

    @Override
    public void updateProjection() {
        Matrix.perspectiveM(projM,0,FOV,aspectRatio,near,far);
    }

    public void setPerspective(float FOV, float ratio, float near, float far)
    {
        this.FOV = FOV;
        this.aspectRatio = ratio;
        this.near = near;
        this.far = far;
        updateProjection();



    }


    @Override
    public void OnScreenChanged() {
        aspectRatio = (float)Screen.SCREEN_WIDTH /(float) Screen.SCREEN_HEIGHT;
        updateProjection();

    }
}
