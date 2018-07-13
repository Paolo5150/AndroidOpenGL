package Engine;

import android.opengl.Matrix;

import Application.Camera;
import Math.Vector3f;

public class Transform {

    GameObject gameObject;
    Transform parent;

    public Transform()
    {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1,1,1);
        parent = null;
        gameObject = null;

    }

    public Transform(GameObject g)
    {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1,1,1);
        parent = null;
        gameObject = g;

    }

    public float[] getTransformation()
    {
        float[] t = new float[16];
        float[] r= new float[16];
        float[] s= new float[16];
        float[] res = new float[16];

        Matrix.setIdentityM(t,0);
        Matrix.setIdentityM(r,0);
        Matrix.setIdentityM(s,0);

        Matrix.translateM(t,0,position.x,position.y,position.z);

        if(rotation.x!=0)
             Matrix.rotateM(r,0,rotation.x,1,0,0);

        if(rotation.y!=0)
            Matrix.rotateM(r,0,rotation.y,0,1,0);

        if(rotation.z!=0)
            Matrix.rotateM(r,0,rotation.z,0,0,1);

        Matrix.scaleM(s,0,scale.x,scale.y,scale.z);

        Matrix.multiplyMM(res,0,t,0,r,0);
        Matrix.multiplyMM(res,0,res,0,s,0);

        if(parent != null)
        {
            Matrix.multiplyMM(res,0,parent.getTransformation(),0,res,0);
        }
        return res;


    }

    public void setParent(Transform parent)
    {
        this.parent = parent;
    }
    public Transform getParent()
    {
        return parent;
    }

    public float[] getMVP(Camera renderingCamera)
    {
        float[] mvp = new float[16];

        Matrix.multiplyMM(mvp,0,renderingCamera.getProjectViewMatrix(),0,getTransformation(),0);
        return mvp;
    }

    public Vector3f getWorldLocation()
    {
        Vector3f v = new Vector3f();
        float[] t = getTransformation();
        v.x = t[12];
        v.y =  t[13];
        v.z = t[14];
        return v;
    }

    public Vector3f getActualScale()
    {
        float[] t = getTransformation();
        Vector3f v = new Vector3f();
        v.x = new Vector3f(t[0],t[1],t[2]).length();
        v.y = new Vector3f(t[4],t[5],t[6]).length();
        v.z = new Vector3f(t[7],t[8],t[9]).length();


        return v;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;
}
