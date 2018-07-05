package Application;

import android.opengl.Matrix;

import Math.Vector3f;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Camera {

    public Vector3f position;
    public Vector3f target;
    public Vector3f up;

    private float[] viewM;
    private float[] projM;

    public Camera()
    {
     viewM = new float[16];
     projM = new float[16];
     position = new Vector3f();
     target = new Vector3f();
     up = new Vector3f(0,1,0);

    }

    public void setPerspective(float FOV, float ratio, float near, float far)
    {

        Matrix.perspectiveM(projM,0,FOV,ratio,near,far);
    }

    private void UpdateMat()
    {
      Matrix.setLookAtM(viewM,0,position.x, position.y, position.z, target.x, target.y, target.z, up.x, up.y, up.z);
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





}
