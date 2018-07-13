package Application;

import android.opengl.Matrix;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.HashMap;

import Math.Vector3f;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Camera {


    private static HashMap<String, Camera> allCameras = new HashMap<String,Camera>();


    public Vector3f position;
    public Vector3f target;
    public Vector3f up;

    private float[] viewM;
    private float[] projM;

    private float FOV;
    private float aspectRatio;
    private float near;
    private float far;
    private String type;

    public Camera(String name)
    {
     viewM = new float[16];
     projM = new float[16];
     position = new Vector3f();
     target = new Vector3f();
     up = new Vector3f(0,1,0);

    allCameras.put(name,this);
    }

    public static HashMap<String, Camera> getAllCameras() {
        return allCameras;
    }

    public void setPerspective(float FOV, float ratio, float near, float far)
    {
        this.FOV = FOV;
        this.aspectRatio = ratio;
        this.near = near;
        this.far = far;
        type = "Perspective";
        updateProjectionPerspective();

    }

    public void setOrthographic(float left,float right, float bottom, float top, float near, float far)
    {
        Matrix.orthoM(projM,0,left,right,bottom,top,near,far);
        type = "Orthographic";
    }

    public void updateProjectionPerspective()
    {
        Matrix.perspectiveM(projM,0,FOV,aspectRatio,near,far);
    }

    private void UpdateMat()
    {
      Matrix.setLookAtM(viewM,0,position.x, position.y, position.z, target.x, target.y, target.z, up.x, up.y, up.z);
    }



    public float[] getProjectViewMatrix()
    {
        float[] pv = new float[16];
        Matrix.multiplyMM(pv,0,getProj(),0,getViewM(),0);
        return pv;

    }
    public void Update()
    {
        UpdateMat();
    }

    public float[] getProj()
    {
        return  projM;
    }

    public float[] getViewM()
    {
        return viewM;
    }

    public static Camera getCameraByName(String name)
    {
        Camera c = null;
        if(allCameras.containsKey(name))
            c =  allCameras.get(name);
        else
            Log.d(GlobalVariables.TAG, "Camera " + name + " does not exist.");

        return c;
    }

    public String getType() {
        return type;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
}
