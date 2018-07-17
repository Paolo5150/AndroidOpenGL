package Components;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Rendering.Camera;
import Engine.Component;
import Engine.GameObject;
import Engine.Transform;
import Rendering.Layer;
import Rendering.Material;
import Rendering.Mesh;
import Rendering.RenderingEngine;
import Math.*;

public abstract class Renderer extends Component{

    public enum RENDER_MODE
    {
        FILL,
        LINES
    }

    protected Mesh mesh;
    protected Material material;
    public Transform transform;
    protected Camera renderingCamera;
    public Vector3f colorOverride;
    public RENDER_MODE renderMode;


    public Renderer(String name,Mesh mesh, Material mat, GameObject o)
    {
        super(name,o);
        colorOverride = new Vector3f(1,1,1);
        this.material = mat;

        this.mesh = mesh;
        this.gameObject = o;
        this.transform = o.transform;
        renderMode = RENDER_MODE.FILL;

        //Default camera
        renderingCamera = Camera.activeCamera;

    }

    @Override
    protected void setComponentType()
    {
        componentType = "Renderer";
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Render()
    {
        //renderingCamera = Camera.activeCamera;


        //Check Layer
        for(Integer i : renderingCamera.cullingMask)
        {
            if(i == getGameObject().getLayer()) {
               // GlobalVariables.logWithTag("Rendering " + getGameObject().getName());
                prepareShaderAndMAterial();
                if(renderMode == RENDER_MODE.FILL)
                    mesh.render();
                else if (renderMode == RENDER_MODE.LINES)
                    mesh.renderLines();
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void RenderUnactivated()
    {
       // renderingCamera = Camera.activeCamera;
        //Check Layer
        for(Integer i : renderingCamera.cullingMask)
        {
            if(i == getGameObject().getLayer()) {

                if(renderMode == RENDER_MODE.FILL)
                mesh.render();
                else if (renderMode == RENDER_MODE.LINES)
                        mesh.renderLines();

            }
        }
    }



 //Called only by normal rendering
    protected void prepareShaderAndMAterial()
    {

             material.updateVec3("color",colorOverride); //Also called in RenderingEngine - batch rendering
            material.ActivateMaterial(transform.getTransformation(),renderingCamera);
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

    public Camera getRenderingCamera() {
        return renderingCamera;
    }

    public void setRenderingCamera(Camera renderingCamera) {
        this.renderingCamera = renderingCamera;
    }
}
