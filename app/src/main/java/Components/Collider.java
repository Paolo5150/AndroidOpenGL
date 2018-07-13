package Components;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.Component;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import Engine.Transform;
import Math.Vector3f;
import Physics.Ray;
import Rendering.BasicShader;
import Rendering.LightShader;
import Rendering.Material;
import Rendering.Mesh;

public abstract class Collider extends Component{

    public Transform transform;
    public MeshRenderer meshRenderer;
    public Mesh mesh;
    public Material material;
    private boolean renderable;

    public Collider(String n, GameObject o) {
        super(n,o);

        //Create transform
        transform = new Transform(o);
        transform.setParent(o.transform);

        //Material
        material = new Material();
        material.color = new Vector3f(0,1,0);
        material.specularColor = new Vector3f(1,1,1);
        material.shader = new BasicShader();

        //Mesh renderer
        meshRenderer = new MeshRenderer(null,material,o);
        meshRenderer.transform = transform;
        meshRenderer.setGameObject(getGameObject());
        renderable = false;


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void Render()
    {
        if(renderable)
        meshRenderer.RenderTriangles();
    }

    public boolean isRenderable() {
        return renderable;
    }

    public void setRenderable(boolean renderable) {
        this.renderable = renderable;
    }

    public boolean isCollidingWithRay(Ray r)
    {
        return false;
    }
}
