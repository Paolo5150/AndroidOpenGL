package Scenes;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.opengl.GLES30;
import android.os.Build;
import android.renderscript.Matrix4f;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Application.AppCanvas;
import Components.CameraPerspective;
import Components.Lerp;
import Components.MeshRenderer;
import Components.PhysicsBody;
import Components.SphereCollider;
import Engine.EngineTime;
import Engine.Input;
import Engine.PreMadeMeshes;
import GUI.GUICanvas;
import GUI.GUITexture;
import Listeners.ITouchListener;
import Physics.PhysicsGlobals;
import Physics.Ray;
import Physics.RayCast;
import PreMadeGameObjects.Terrain;
import Engine.GameObject;
import Engine.Scene;
import PreMadeGameObjects.Water;
import Rendering.Camera;
import Rendering.CubeMap;
import Rendering.Layer;
import Rendering.MaterialManager;
import Rendering.Mesh;
import Rendering.Screen;
import PreMadeGameObjects.SkyBox;
import Rendering.Texture;
import Math.*;
import Rendering.Triangle;

public class TestScene extends Scene implements ITouchListener {



    GameObject sphere;
    GameObject quad;
    Terrain terrain;
    Water water;


    GameObject cam;



    GameObject can;



    public TestScene() {
        super("Test_Scene");


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void loadScene()
    {
        super.loadScene();
        cam = new GameObject("Camera_Main");
        cam.addComponent(new CameraPerspective("Camera_Main",cam,60,(float) Screen.SCREEN_WIDTH / Screen.SCREEN_HEIGHT,0.1f,2000.0f));
        cam.getComponent("Camera_Main",Camera.class).setAsActive();

        cam.transform.position.z = 8;
        cam.transform.position.y = 30;

        can = new AppCanvas();

        terrain = new Terrain(50,50);
        terrain.loadHeightMap("hm.png",80);

        water = new Water(50,50);

        terrain.transform.setScale(new Vector3f(10,1,10));
        water.transform.scale = new Vector3f(10,1,10);

        sphere = new GameObject("Sphere");
        sphere.addComponent(new CharizardBehavior(sphere));
        sphere.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Sphere"), MaterialManager.getMaterialByName("Material_BumpyWall"), sphere));
       // sphere.addComponent(new PhysicsBody(sphere));


        sphere.transform.setScale( new Vector3f(5,5,5));
        sphere.transform.position = new Vector3f(10,20,-10);



        quad = new GameObject("Quad");
        //quad.addComponent(new CharizardBehavior(quad));
        quad.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Quad"),MaterialManager.getMaterialByName("Material_BumpyWall"), quad));
        quad.transform.scale = new Vector3f(5,5,1);




       //  addChild(quad);
        addChild(cam);
        addChild(can);

        addChild(sphere);
       addChild(terrain);
       addChild(water);

        //printHierarchy();

        Input.getInstance().addListener(this); //Add last so we can check if gui has been touched first.
                                                    //Not a very good approach, will need change

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void start()
    {

        super.start();

      /*  ArrayList<Triangle> ts = terrain.quadTree.FindQuadrant(0,0);

        for(Triangle t : ts)
        {
            t.v1.setColor(new Vector3f(1,0,0));
            t.v2.setColor(new Vector3f(1,0,0));
            t.v3.setColor(new Vector3f(1,0,0));
        }

        terrain.mesh.UpdateVertices(terrain.mesh.getVertices(), terrain.mesh.getIndices());*/



    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void update()
    {

        super.update();

       // sphere.getComponent("SphereCollider",SphereCollider.class).collideWithTerrain(terrain);
        //terrain.mesh.UpdateVertices(terrain.mesh.getVertices(), terrain.mesh.getIndices());




    }




    @Override
    public void assignOptionalSkyBox() {
        CubeMap c = new CubeMap("ClearSky","sky");
        skyBox = new SkyBox(c);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void OnTouch(int x, int y, int id) {

        if(!Input.GUITouched)
        {

            Ray r = RayCast.rayCastFromActiveCamera(1000);
            Ray localR = new Ray();

            float[] localOrig = new float[4];
            float[] localDir = new float[4];
            float[] trans = new float[16];

            android.opengl.Matrix.transposeM(trans,0,terrain.transform.getInverseTransformation(),0);

            android.opengl.Matrix.multiplyMV(localOrig,0,terrain.transform.getInverseTransformation(),0,r.origin.toFloat4f(1.0f),0);
            android.opengl.Matrix.multiplyMV(localDir,0,terrain.transform.getInverseTransformation(),0,r.direction.toFloat4f(0.0f),0);

            localR.origin.wrap(localOrig);
            localR.direction.wrap(localDir);
            localR.direction.normalizeThis();
            localR.distance = r.distance;

            float distance = 0.0f;


        boolean hasHit = false;

            while(distance < localR.distance && !hasHit) {

                Vector3f pointOnRay = Vector3f.add(localR.origin,Vector3f.multiply(localR.direction,distance));
                pointOnRay = localR.pointOnRay(distance);


                for (Triangle tr : terrain.quadTree.FindQuadrant(pointOnRay.x,-pointOnRay.z)) {
                  //  GlobalVariables.logWithTag("Quadrant found");


                    Vector3f hit = PhysicsGlobals.rayIntersectTriangle(localR, tr);

                    if (hit != null) {
                        float[] hitWorld = new float[4];

                        android.opengl.Matrix.multiplyMV(hitWorld, 0, terrain.transform.getTransformation(), 0, hit.toFloat4f(1.0f), 0);
                        Vector3f hitW = new Vector3f();
                        hitW.wrap(hitWorld);



                        sphere.transform.position = hitW;
                        hasHit = true;
                        break;
                        // GlobalVariables.logWithTag("Hit at " + hit.getInfo());
                    }
                }

                distance += 0.5f;

            }

        }




    }


    @Override
    public void OnDrag(int x, int y, int id) {

    }

    @Override
    public void OnRelease(int x, int y, int id) {

    }
}
