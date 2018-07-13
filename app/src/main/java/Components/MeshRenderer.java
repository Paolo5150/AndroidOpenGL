package Components;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Application.Camera;
import Engine.Component;
import Engine.GameObject;
import Engine.Transform;
import Rendering.Material;
import Rendering.Mesh;

public class MeshRenderer extends Component {

    private Mesh mesh;
    private Material material;
    public Transform transform;

    public MeshRenderer(Mesh mesh, Material material, GameObject o) {
        super("MeshRenderer",o);

        this.mesh = mesh;
        this.material = material;
        this.transform = o.transform;
    }

    @Override
    public void Start()
    {

    }
    @Override
    public void Update()
    {}
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void Render()
    {
        prepareShaderAndMAterial();
        mesh.render();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

    public void RenderTriangles()
    {
        prepareShaderAndMAterial();
        mesh.renderTriangles();

        //Log.d(GlobalVariables.TAG,"I rendered " + getGameObject().getName());
    }

    private void prepareShaderAndMAterial()
    {
        material.shader.updateUniforms(transform.getTransformation(),Camera.getCameraByName("Main_Camera"));

        //TODO: move line below, group objects by material and activate material once
        material.ActivateMaterial(getGameObject().transform.getTransformation(), Camera.getCameraByName("Main_Camera"));
    }
    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

}
