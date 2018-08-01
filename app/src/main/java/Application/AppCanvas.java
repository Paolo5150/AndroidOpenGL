package Application;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.HashMap;

import Components.Renderer;
import Engine.EngineTime;
import Engine.Entity;
import Engine.GameObject;
import Engine.Input;
import GUI.GUICanvas;
import GUI.GUIObject;
import GUI.GUIQuadCollider;
import GUI.GUIRenderer;
import GUI.GUITexture;
import Listeners.ITouchListener;
import Rendering.Camera;
import Rendering.FrameBuffer;
import Rendering.Layer;
import Rendering.RenderingEngine;
import Rendering.Screen;
import Rendering.Texture;
import Math.*;
import ShaderObjects.GUIUVUninvertedShader;

public class AppCanvas extends GameObject implements ITouchListener {

    GUITexture leftJ;
    GUITexture rightJ;
    public static GUITexture topLeft;
    GUIQuadCollider lc;
    GUIQuadCollider rc;

    GUICanvas canvas;

    Vector3f leftPosition;
    Vector3f rightPosition;
    Vector3f leftDistanceVector;
    Vector3f rightDistanceVector;
    Vector2f touchOffset;


    float distance = 100;

    private HashMap<Integer, GUITexture> wtfMap;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public AppCanvas() {
        super("Main canvas");



        wtfMap = new HashMap<>();
        canvas = new GUICanvas(this);

        Texture left = new Texture("smiley.png","texture_diffuse0");
        leftJ = new GUITexture("LeftJ",left,canvas);
        rightJ = new GUITexture("RightJ",left,canvas);

        lc = (GUIQuadCollider) leftJ.getComponent("GUIQuadCollider");
        rc = rightJ.getComponentByType("Collider",GUIQuadCollider.class);




        topLeft = new GUITexture("Scene",null,canvas);
        topLeft.setWidth(canvas.getCanvasWidth()/3.0f);
        topLeft.setHeight(canvas.getCanvasHeight() / 3.0f);
        topLeft.transform.position = new Vector3f(canvas.getFarLeft() + topLeft.getWidth()/2.0f,canvas.getTop() - topLeft.getHeight()/2.0f,0);
        topLeft.getComponentByType("Renderer", GUIRenderer.class).getMaterial().setShader(new GUIUVUninvertedShader());



        leftDistanceVector = new Vector3f();
        rightDistanceVector = new Vector3f();

        leftJ.setWidthHeight(120,120);
        rightJ.setWidthHeight(120,120);
        leftJ.getComponentByType("Renderer", Renderer.class).alphaOverride = 0.5f;
        rightJ.getComponentByType("Renderer", Renderer.class).alphaOverride = 0.5f;

        leftPosition = new Vector3f(canvas.getFarLeft() + 180,canvas.getBottom()+180,0);
        rightPosition = new Vector3f(canvas.getFarRight() - 180,canvas.getBottom()+ 180,0);

        leftJ.transform.position = new Vector3f(leftPosition.x, leftPosition.y, 0);
        rightJ.transform.position = new Vector3f(rightPosition.x, rightPosition.y, 0);;

        canvas.addGUIObject(leftJ);
        canvas.addGUIObject(rightJ);
       // canvas.addGUIObject(topLeft);


        Input.getInstance().addListener(this);


       addComponent(canvas);


    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void update()
    {
        super.update();



        leftDistanceVector= Vector3f.subtract(leftJ.transform.position, leftPosition);


        rightDistanceVector = Vector3f.subtract(rightJ.transform.position, rightPosition);


        Vector3f toAddFront = Vector3f.multiply(Camera.activeCamera.front, leftDistanceVector.y/100.0f);
        Vector3f toAddRight = Vector3f.multiply(Camera.activeCamera.right, leftDistanceVector.x/100.0f);
        Vector3f toAdd = Vector3f.add(toAddFront,toAddRight);

        toAdd = Vector3f.multiply(toAdd, 100 * EngineTime.getDeltaTimeSeconds());

        Camera.activeCamera.getGameObject().transform.position = Vector3f.add(Camera.activeCamera.getGameObject().transform.position,toAdd);
        Camera.activeCamera.setYaw(Camera.activeCamera.getYaw() + rightDistanceVector.x/2.0f * EngineTime.getDeltaTimeSeconds());
        Camera.activeCamera.setPitch(Camera.activeCamera.getPitch() + rightDistanceVector.y/ 2.0f* EngineTime.getDeltaTimeSeconds());



    }

    @Override
    public void OnTouch(int ix, int iy, int id) {

        float x = ix - Screen.SCREEN_WIDTH/2.0f;
        float y = -(iy - Screen.SCREEN_HEIGHT/2.0f);


        if(rc.hitByTouch(x,y)) {
            touchOffset = new Vector2f(rightJ.transform.position.x - x, rightJ.transform.position.y - y);
            wtfMap.put(id,rightJ);

        }
        else if (lc.hitByTouch(x,y)) {
            touchOffset = new Vector2f(leftJ.transform.position.x - x, leftJ.transform.position.y - y);
            wtfMap.put(id,leftJ);

        }




    }

    @Override
    public void OnDrag(int ix, int iy, int id) {

        float x = ix - Screen.SCREEN_WIDTH/2.0f;
        float y = -(iy - Screen.SCREEN_HEIGHT/2.0f);


        if(rc.hitByTouch(x,y)) {
            rightJ.transform.position.x = x + touchOffset.x;
            rightJ.transform.position.y = y + touchOffset.y;

            rightDistanceVector = Vector3f.subtract(rightJ.transform.position, rightPosition);

            if(rightDistanceVector.length() > distance) {
                Vector3f dir = Vector3f.normalize(rightDistanceVector);
                dir = Vector3f.multiply(dir,distance);
                rightJ.transform.position = Vector3f.add(rightPosition,dir);

            }


        }

        else if(lc.hitByTouch(x,y))
        {
            leftJ.transform.position.x = x + touchOffset.x;
            leftJ.transform.position.y = y + touchOffset.y;

            leftDistanceVector= Vector3f.subtract(leftJ.transform.position, leftPosition);

            if(leftDistanceVector.length() > distance) {
                Vector3f dir = Vector3f.normalize(leftDistanceVector);
                dir = Vector3f.multiply(dir,distance);
                leftJ.transform.position = Vector3f.add(leftPosition,dir);

            }





        }



    }

    @Override
    public void OnRelease(int x, int y, int id) {


        GUITexture t = wtfMap.get(id);

        if(t!=null) {
            if (t.getName().contains("Left")) {
                t.transform.position = new Vector3f(leftPosition.x, leftPosition.y, 0);



            }  if (t.getName().contains("Right")) {

                t.transform.position = new Vector3f(rightPosition.x, rightPosition.y, 0);

            }

        }
        wtfMap.remove(id);

    }
}
