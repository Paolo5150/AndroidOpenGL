package GUI;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.MainRenderer;

import java.util.ArrayList;
import java.util.HashMap;

import Components.CameraOrtho;
import Engine.Component;
import Engine.GameObject;
import Listeners.IScreenChangeListener;
import Rendering.Camera;
import Rendering.Screen;
import Math.*;

public class GUICanvas extends Component implements IScreenChangeListener {

    public float canvasWidth;
    public float canvasHeight;
    GameObject guiCam;

    //Plain color to visualize the canvas
    GUIPlainColor backgroundColor;

    private ArrayList<GUIObject> guiObjects;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public GUICanvas(GameObject objectBelongingTo) {
        super("GUICanvas", objectBelongingTo);

        guiObjects = new ArrayList<>();
        canvasWidth = Screen.SCREEN_WIDTH;
        canvasHeight = Screen.SCREEN_HEIGHT;
        MainRenderer.getInstance().registerScreenChangeListener(this);

        //Create gui camera
        guiCam = new GameObject("Camera_GUI");
        guiCam.addComponent(new CameraOrtho("Camera_GUI",guiCam,-(float)Screen.SCREEN_WIDTH/2,(float)Screen.SCREEN_WIDTH/2,-(float)Screen.SCREEN_HEIGHT/2,(float)Screen.SCREEN_HEIGHT/2,0.001f,1000.0f));
        guiCam.getComponentByType("Camera", Camera.class).depth = 1000;

        backgroundColor = new GUIPlainColor(new Vector3f(1,0,0),0.0f,this);
        backgroundColor.setWidth(canvasWidth);
        backgroundColor.setHeight(canvasHeight);
       // addGUIObject(backgroundColor);

    }


    @Override
    public void Update()
    {
        guiCam.update();

        for(GUIObject s : guiObjects)
        {
           s.update();
        }
    }

    public void addGUIObject(GUIObject obj)
    {
        guiObjects.add(obj);

        obj.guiCanvasParent = this;

    }
    @Override
    protected void setComponentType() {
        componentType = "GUICanvas";
    }

    public float getCanvasWidth() {
        return canvasWidth;
    }

    public void setCanvasWidth(float canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public float getCanvasHeight() {
        return canvasHeight;
    }

    public void setCanvasHeight(float canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    @Override
    public void OnScreenChanged() {
        float previousHeight = canvasHeight;
        float previousWidth = canvasWidth;


        canvasWidth = Screen.SCREEN_WIDTH;
        canvasHeight = Screen.SCREEN_HEIGHT;

        backgroundColor.setWidth((canvasWidth * backgroundColor.getWidth())/ previousWidth);
        backgroundColor.setHeight((canvasHeight * backgroundColor.getHeight())/ previousHeight);

       /* for(String s : guiObjects.keySet())
        {
            GUIObject obj = guiObjects.get(s);

        }*/

    }

    public float getFarLeft()
    {
        return -canvasWidth/2.0f;
    }

    public float getFarRight()
    {
        return canvasWidth/2.0f;
    }

    public float getTop()
    {
        return canvasHeight/2.0f;
    }

    public float getBottom()
    {
        return -canvasHeight/2.0f;
    }

    public Camera getRenderingCamera()
    {
        return guiCam.getComponentByType("Camera",Camera.class);
    }
}
