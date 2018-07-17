package Components;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.GameObject;
import Engine.PreMadeMeshes;
import MaterialObjects.Material_SkyBox;
import Rendering.Camera;
import Rendering.Material;
import Rendering.MaterialManager;
import Rendering.Mesh;

public class SkyBoxRenderer extends Renderer {

    public SkyBoxRenderer(GameObject o) {
        super("SkyBoxRenderer", PreMadeMeshes.getMeshByName("Cube"), new Material_SkyBox(), o);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Render()
    {
        renderingCamera = Camera.activeCamera;

        //Check Layer
        for(Integer i : renderingCamera.cullingMask)
        {
            if(i == getGameObject().getLayer()) {
                // GlobalVariables.logWithTag("Rendering " + getGameObject().getName());
                prepareShaderAndMAterial();
                GLES30.glDepthMask(false);
                GLES30.glDisable(GLES20.GL_CULL_FACE);
                mesh.render();
                GLES30.glDepthMask(true);
                GLES30.glEnable(GLES20.GL_CULL_FACE);
            }
        }


    }

}
