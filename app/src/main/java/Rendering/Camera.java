package Rendering;

import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.MainRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Engine.Component;
import Engine.GameObject;
import Listeners.IScreenChangeListener;
import Math.Vector3f;

/**
 * Created by Paolo on 24/06/2018.
 */

public abstract class Camera extends Component implements IScreenChangeListener{


    public static Camera activeCamera;
    protected static HashMap<String, Camera> allCameras = new HashMap<String,Camera>();


    public int depth;
    protected float near;
    protected float far;

    public Vector3f target;
    public Vector3f up;

    float yaw;
    float pitch;
    float roll;
    protected float[] viewM;
    protected float[] projM;


    protected String cameraType;
    public Set<Integer> cullingMask;

    public Vector3f front;
    public Vector3f right;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Camera(String name, GameObject o)
    {
        super(name,o);
        viewM = new float[16];
     projM = new float[16];


     target = new Vector3f();
     up = new Vector3f(0,1,0);
     front = new Vector3f();

     pitch = 0;
     yaw = -90;
     roll = 0;
     depth = 0;

     cullingMask = new HashSet<>();

     //On creation, get all the layers available and add them to culling mask
        for(String s : Layer.getLayersNames())
        {
            addLayer(s);
        }



    allCameras.put(name,this);

    if(activeCamera == null)
        activeCamera = this;

    //Register to screen change listener
        MainRenderer.getInstance().registerScreenChangeListener(this);
    }

    @Override
    protected void setComponentType() {
        componentType = "Camera";
    }

    public static HashMap<String, Camera> getAllCameras() {
        return allCameras;
    }
    public static void setActiveCamera(Camera c)
    {
        activeCamera = c;
    }



    public void setOrthographic(float left,float right, float bottom, float top, float near, float far)
    {
        Matrix.orthoM(projM,0,left,right,bottom,top,near,far);
        cameraType = "Orthographic";
    }

    public void addLayer(String name)
    {

      //GlobalVariables.logWithTag("Layer added to camera " + name);
        cullingMask.add(Layer.getLayer(name));
    }

    public void removeLayer(String name)
    {

       cullingMask.remove((Integer)Layer.getLayer(name));
      // GlobalVariables.logWithTag("Layer removed from camera " + cullingMask.size());

    }

    public abstract void updateProjection();


    public void updateView()
    {
        Matrix.setLookAtM(viewM,0,getGameObject().transform.position.x, getGameObject().transform.position.y, getGameObject().transform.position.z, target.x, target.y, target.z, up.x, up.y, up.z);

    }

    public void setAsActive()
    {
        activeCamera = this;
    }



    public float[] getProjectViewMatrix()
    {
        float[] pv = new float[16];
        Matrix.multiplyMM(pv,0,getProj(),0,getViewM(),0);
        return pv;

    }
    @Override
    public void Update(){

       updateVectors();
       updateView();


    }

    public void updateVectors()
    { front.x = (float) Math.cos(Math.toRadians(pitch)) * (float) Math.cos(Math.toRadians(yaw)) ;
        front.y =(float) Math.sin(Math.toRadians(pitch));
        front.z =(float) Math.sin(Math.toRadians(yaw)) * (float) Math.cos(Math.toRadians(pitch));

        right = front.cross(new Vector3f(0,1,0));
        up = right.cross(front);
        target = Vector3f.add(getGameObject().transform.position, front);}


    public float[] getProj()
    {
        return  projM;
    }

    public float[] getViewM()
    {
        return viewM;
    }

    public Vector3f getPosition() {
        return getGameObject().transform.position;
    }

    public void setPosition(Vector3f pos)
    {
        getGameObject().transform.position = new Vector3f(pos.x, pos.y, pos.z);
        updateVectors();
        updateView();
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


    public String getCameraType() {
        return cameraType;
    }

    public void setPitchYawRoll(float p, float y, float r)
    {
        pitch = p;
        yaw = y;
        roll = r;
        updateVectors();
        updateView();
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        updateVectors();
        updateView();

    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        updateVectors();
        updateView();
    }

    public void setRoll(float roll) {
        this.roll = roll;
        updateVectors();
        updateView();
    }

    public float getYaw() {
        return yaw;
    }



    public float getPitch() {
        return pitch;
    }



    public float getRoll() {
        return roll;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }
}
