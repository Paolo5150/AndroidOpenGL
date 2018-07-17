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
import Rendering.Mesh;

public abstract class Collider extends Component{

    public Transform transform;
    public MeshRenderer meshRenderer;

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
        meshRenderer = new MeshRenderer(null,material,o);
        meshRenderer.transform = transform;
        meshRenderer.renderMode = Renderer.RENDER_MODE.LINES;
        meshRenderer.setGameObject(getGameObject());
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
        if(renderable)
        RenderingEngine.getInstance().addToBatch(meshRenderer);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void Render()
    {

        if(renderable)
        meshRenderer.Render();

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
