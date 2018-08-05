package Rendering;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES31;
import android.opengl.GLES31Ext;
import android.opengl.GLES32;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;

import Application.Application;
import Components.Renderer;
import Engine.GameObject;
import Engine.Scene;

public class RenderingEngine {

    private static RenderingEngine instance;
    public static int currentShaderID;
    private static  HashMap<Integer, HashMap<Material,ArrayList<Renderer>>> allRenderers;

    public static RenderingEngine getInstance()
    {

        if(instance == null)
            instance = new RenderingEngine();

        return  instance;
    }

    private RenderingEngine()
    {
        allRenderers = new HashMap<>();
    }

    public void Render(GameObject g)

    {
    g.render();
    }




    public void addToBatch(Renderer rend)
    {

       Camera c = rend.getRenderingCamera();
       if(allRenderers.containsKey(c.depth))
       {
            HashMap<Material, ArrayList<Renderer>> map = allRenderers.get(c.depth);

             if(map.containsKey(rend.getMaterial()))
            {
                map.get(rend.getMaterial()).add(rend);
              //  GlobalVariables.logWithTag("Added " + rend.getGameObject().getName());
            }
            else
            {
                ArrayList<Renderer> l = new ArrayList<>();
                l.add(rend);
                map.put(rend.getMaterial(),l);
             //   GlobalVariables.logWithTag("Created new list and Added " + rend.getGameObject().getName());
            }
       }
       else
       {
           HashMap<Material, ArrayList<Renderer>> map = new HashMap<>();
           ArrayList<Renderer> l = new ArrayList<>();
           l.add(rend);
           map.put(rend.getMaterial(),l);
           allRenderers.put(c.depth,map);
         //  GlobalVariables.logWithTag("Created new map and Added " + rend.getGameObject().getName());
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void renderBatch() {

        for (Integer i : allRenderers.keySet()) {
            GLES30.glClear(GLES20.GL_DEPTH_BUFFER_BIT);

            HashMap<Material, ArrayList<Renderer>> map = allRenderers.get(i);

            for (Material m : map.keySet()) {
                m.getShader().activateShader();
                m.activateTextures();


                for (Renderer r : map.get(m)) {
                    if (r.isActive()) {
                        m.getShader().updateMatrices(r.transform.getTransformation(), r.getRenderingCamera());
                        m.updateVec3("color", r.colorOverride); //I have to change this somehow
                        m.updateFloat("alpha", r.alphaOverride); //I have to change this somehow
                        m.updateMaterialPropertiesToShader();
                        r.activateGLSpecials();
                        r.RenderUnactivated();
                        r.deactivateGLSpecials();
                        // GlobalVariables.logWithTag("Rendered " + r.getGameObject().getName());
                    }
                }


            }


        }

    }

    public void clearMap()
    {
        ArrayList<Renderer> list;
        for (Integer i : allRenderers.keySet()) {

            HashMap<Material, ArrayList<Renderer>> map = allRenderers.get(i);
            for (Material m : map.keySet()) {
                list = map.get(m);
                list.clear();

            }
        }
    }

    public void Initialize()
    {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1);
        GLES30.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glFrontFace(GLES20.GL_CCW);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void renderSceneUsingBatch(Scene currentScene) {

        if(currentScene.skyBox!=null)
            currentScene.skyBox.render();


       //Render(Application.getCurrentScene());
       RenderingEngine.getInstance().renderBatch();


    }


}
