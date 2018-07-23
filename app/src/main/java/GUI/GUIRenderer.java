package GUI;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Components.Renderer;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import MaterialObjects.Material_GUI;
import Rendering.Camera;
import Rendering.Material;
import Rendering.Mesh;
import Rendering.RenderingEngine;
import Rendering.Texture;

public class GUIRenderer extends Renderer {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public GUIRenderer(GameObject o) {
        super("GUIRenderer", PreMadeMeshes.getMeshByName("Quad"), new Material_GUI(), o);

        renderingCamera = Camera.getCameraByName("Camera_GUI");


    }

    @Override
    public void Update()
    {

        RenderingEngine.getInstance().addToBatch(this);
    }

    @Override
    public void activateGLSpecials() {
        super.activateGLSpecials();
        GLES30.glEnable(GLES20.GL_BLEND);
        GLES30.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void deactivateGLSpecials() {
        super.deactivateGLSpecials();
        GLES30.glDisable(GLES20.GL_BLEND);
    }
}
