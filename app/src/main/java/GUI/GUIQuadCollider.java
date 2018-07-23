package GUI;

import android.os.Build;
import android.support.annotation.RequiresApi;

import Listeners.ITouchListener;
import Components.Collider;
import Components.Renderer;
import Engine.Input;
import Engine.PreMadeMeshes;
import Math.Vector3f;
import Physics.Ray;
import PreMadeGameObjects.Plane;
import Rendering.Material;
import Math.*;
import Rendering.Screen;
import ShaderObjects.GUIBasicShader;

public class GUIQuadCollider extends Collider {

    private float width;
    private float height;




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public GUIQuadCollider(GUIObject objectBelongingTo) {
        super("GUIQuadCollider", objectBelongingTo);



        renderer = new GUIRenderer(objectBelongingTo);

        renderer.setMesh(PreMadeMeshes.getMeshByName("Quad"));

        renderer.renderMode = Renderer.RENDER_MODE.FILL;
        renderer.setRenderingCamera(objectBelongingTo.guiCanvasParent.getRenderingCamera());
        setWidth(objectBelongingTo.getWidth());
        setHeight(objectBelongingTo.getHeight());

        Material m = new Material("Basic_GUI",new GUIBasicShader());

        renderer.setMaterial(m);
         renderer.setGameObject(objectBelongingTo);

        renderer.colorOverride = new Vector3f(0,1,0);
        renderer.alphaOverride = 0.50f;
       // setRenderable(true);


    }


    @Override
    public void lateUpdate()
    {
        isCollidingWithRay(null);
    }


    public boolean hitByTouch(float x, float y)
    {
        if (    x > transform.getWorldLocation().x - (width  / 2.0f) * transform.scale.x &&
                x < transform.getWorldLocation().x + (width  / 2.0f) * transform.scale.x &&
                y > transform.getWorldLocation().y - (height / 2.0f) * transform.scale.y &&
                y < transform.getWorldLocation().y + (height / 2.0f) * transform.scale.y) {
            Input.GUITouched = true;
            return true;
        }
            else
                return false;
    }
    @Override
    public boolean isCollidingWithRay(Ray r) {

    return true;
    }

    @Override
    public boolean isCollidingWithPlane(Plane p) {
        return false;
    }

    public void setWidth(float w)
    {
        width = w;


        updateVertices();
    }

    public void setHeight(float h)
    {
        height = h;
        updateVertices();
    }

    private void updateVertices()
    {
        Vertex[] vertices = {new Vertex(-width/2.0f,-height/2.0f,0.0f,0.0f,0.0f),
                new Vertex(width/2.0f,-height/2.0f,0.0f,1.0f,0.0f),
                new Vertex(width/2.0f,height/2.0f,0.0f,1.0f,1.0f),
                new Vertex(-width/2.0f,height/2.0f,0.0f,0.0f,1.0f)};

        renderer.getMesh().UpdateVertices(vertices,renderer.getMesh().getIndices());
    }




}
