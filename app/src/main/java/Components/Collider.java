package Components;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.Component;
import Engine.GameObject;
import Engine.Transform;
import Physics.Ray;
import PreMadeGameObjects.Plane;
import Rendering.RenderingEngine;
import ShaderObjects.BasicShader;
import Rendering.Material;

public abstract class Collider extends Component{

    public Transform transform;
    public Renderer renderer;

    public Material material;
    private boolean renderable;

    public Collider(String n, GameObject o) {
        super(n,o);

        //Create transform
        transform = new Transform(o);
        transform.setParent(o.transform);

        allowMultiple = true;

        //Material
        material = new Material("Mesh_Material", new BasicShader());
        material.addVec3("color",1,1,1);
        material.addVec3("specularcolor",1,1,1);


        //Mesh renderer
        renderer = new MeshRenderer(null,material,o);
        renderer.transform = transform;
        renderer.renderMode = Renderer.RENDER_MODE.LINES;
        renderer.setGameObject(getGameObject());
        renderable = false;



    }

    @Override
    protected void setComponentType()
    {
        componentType = "Collider";
    }

    @Override
    public void Update()
    {
        if(renderable) {
           // GlobalVariables.logWithTag("Added collider " + getName());
            RenderingEngine.getInstance().addToBatch(renderer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void Render()
    {

        if(renderable)
        renderer.Render();

    }


    public boolean isRenderable() {
        return renderable;
    }


    public void setRenderable(boolean renderable) {
        this.renderable = renderable;
    }

    public abstract boolean isCollidingWithRay(Ray r);
    public abstract boolean isCollidingWithPlane(Plane p);




}
