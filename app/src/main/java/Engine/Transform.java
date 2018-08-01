package Engine;

import android.opengl.Matrix;

import java.util.ArrayList;

import Listeners.ITransformChangeListener;
import Rendering.Camera;
import Math.Vector3f;

public class Transform implements ITransformChangeListener {

    GameObject gameObject;
    Transform parent;
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    private Vector3f worldScale;
    private Vector3f worldPosition;

    private ArrayList<ITransformChangeListener> childrenListeners;

    public Transform()
    {
        initialize();
        gameObject = null;


    }

    public Transform(GameObject g)
    {
        initialize();
        gameObject = g;

    }

    private void initialize()
    {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1,1,1);
        worldScale = getActualScale();
        worldPosition = getWorldLocation();
        parent = null;
        childrenListeners = new ArrayList<>();
    }

    public float[] getInverseTransformation()
    {
        float[] res = new float[16];


        Matrix.invertM(res,0,getTransformation(),0);
        return res;
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
        parent.childrenListeners.add(this);

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

    private Vector3f getWorldLocation()
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


    public void setPosition(Vector3f position) {
        this.position = position;
        worldPosition = getWorldLocation();
        for(ITransformChangeListener l : childrenListeners)
            l.OnPositionChange();
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        for(ITransformChangeListener l : childrenListeners)
            l.OnRotationChange();
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        worldScale = getActualScale();

        for(ITransformChangeListener l : childrenListeners)
            l.OnScaleChange();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getWorldScale() {
        return worldScale;
    }

    public Vector3f getWorldPosition() {
        return getWorldLocation(); //Need to find a better way
    }

    @Override
    public void OnPositionChange() {
        worldPosition = getWorldLocation();

    }

    @Override
    public void OnScaleChange() {
        worldScale = getActualScale();

    }

    @Override
    public void OnRotationChange() {

    }
}
