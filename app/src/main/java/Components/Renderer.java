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
    public float alphaOverride;
    public RENDER_MODE renderMode;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Renderer(String name, Mesh m, Material mat, GameObject o)
    {
        super(name,o);
        colorOverride = new Vector3f(1,1,1);
        alphaOverride = 1.0f;
        this.material = mat;

        if(m!=null)
        setMesh(m);

        this.gameObject = o;
        this.transform = o.transform;
        renderMode = RENDER_MODE.FILL;

        //Default camera
        renderingCamera = Camera.activeCamera;

    }

    public void activateGLSpecials()
    {}

    public void deactivateGLSpecials()
    {}


    @Override
    protected void setComponentType()
    {
        componentType = "Renderer";
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Render()
    {
        //renderingCamera = Camera.activeCamera;
        if(renderingCamera.isActive()) {
            activateGLSpecials();
            //Check Layer
            for (Integer i : renderingCamera.cullingMask) {
                if (i == getGameObject().getLayer()) {
                    // GlobalVariables.logWithTag("Rendering " + getGameObject().getName());
                    prepareShaderAndMAterial();
                    if (renderMode == RENDER_MODE.FILL)
                        mesh.render();
                    else if (renderMode == RENDER_MODE.LINES)
                        mesh.renderLines();
                }
            }
            deactivateGLSpecials();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void RenderUnactivated()
    {
        if(renderingCamera.isActive()) {
            activateGLSpecials();
            for (Integer i : renderingCamera.cullingMask) {
                if (i == getGameObject().getLayer()) {

                    if (renderMode == RENDER_MODE.FILL)
                        mesh.render();
                    else if (renderMode == RENDER_MODE.LINES)
                        mesh.renderLines();

                }
            }
            deactivateGLSpecials();
        }
    }



 //Called only by normal rendering
    protected void prepareShaderAndMAterial()
    {

             material.updateVec3("color",colorOverride); //Also called in RenderingEngine - batch rendering
            material.updateFloat("alpha",alphaOverride); //Also called in RenderingEngine - batch rendering

            material.ActivateMaterial(transform.getTransformation(),renderingCamera);
    }



    public Mesh getMesh() {
        return mesh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setMesh(Mesh m) {

        this.mesh = new Mesh(m.getVertices(),m.getIndices(),false);
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
