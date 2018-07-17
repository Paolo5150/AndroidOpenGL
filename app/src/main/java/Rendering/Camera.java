package Rendering;

import android.opengl.Matrix;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Engine.Component;
import Engine.GameObject;
import Math.Vector3f;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Camera extends Component {


    public static Camera activeCamera;
    private static HashMap<String, Camera> allCameras = new HashMap<String,Camera>();

    public static float currentDepth = 0;
    public float depth;

    public Vector3f target;
    public Vector3f up;

    float yaw;
    float pitch;
    float roll;
    private float[] viewM;
    private float[] projM;

    private float FOV;
    private float aspectRatio;
    private float near;
    private float far;
    private String cameraType;
    public Set<Integer> cullingMask;

    private Vector3f front;
    private Vector3f right;

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

        updateVectors();
        updateView();

    allCameras.put(name,this);

    if(activeCamera == null)
        activeCamera = this;
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

    public void setPerspective(float FOV, float ratio, float near, float far)
    {
        this.FOV = FOV;
        this.aspectRatio = ratio;
        this.near = near;
        this.far = far;
        cameraType = "Perspective";
        updateProjectionPerspective();

    }

    public void setOrthographic(float left,float right, float bottom, float top, float near, float far)
    {
        Matrix.orthoM(projM,0,left,right,bottom,top,near,far);
        cameraType = "Orthographic";
    }

    public void addLayer(String name)
    {

       GlobalVariables.logWithTag("Layer added to camera " + cullingMask.size());
        cullingMask.add(Layer.getLayer(name));
    }

    public void removeLayer(String name)
    {

       cullingMask.remove((Integer)Layer.getLayer(name));
       GlobalVariables.logWithTag("Layer removed from camera " + cullingMask.size());

    }

    public void updateProjectionPerspective()
    {
        Matrix.perspectiveM(projM,0,FOV,aspectRatio,near,far);
    }

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

    private void updateVectors()
    { front.x = (float) Math.cos(Math.toRadians(pitch)) * (float) Math.cos(Math.toRadians(yaw)) ;
        front.y =(float) Math.sin(Math.toRadians(pitch));
        front.z =(float) Math.sin(Math.toRadians(yaw)) * (float) Math.cos(Math.toRadians(pitch));
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

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
}
