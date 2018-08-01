package GUI;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.Component;
import Engine.Entity;
import Engine.GameObject;
import Math.*;
import Rendering.Layer;
import Rendering.RenderingEngine;

public class GUIObject extends GameObject {

    GUIRenderer renderer;
    private float width;
    private float height;
    GUICanvas guiCanvasParent;
    public boolean moveable;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public GUIObject(String name, GUICanvas canvas) {
        super(name);

        renderer = new GUIRenderer(this);
        moveable = true;

        guiCanvasParent = canvas;

        transform.setParent(guiCanvasParent.getGameObject().transform);
        setLayer(Layer.GUI);

        addComponent(renderer);


        setWidth(300);
        setHeight(300);

    }

    @Override
    public void update()
    {


        RenderingEngine.getInstance().addToBatch(renderer);
        for(String key : components.keySet())
        {
            for(Component c : components.get(key))
                c.Update();
        }


    }
    public void setWidth(float w)
    {
        width = w;


      updateVertices();
    }

    public void setWidthHeight(float w, float h)
    {
        width = w;
        height = h;
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
