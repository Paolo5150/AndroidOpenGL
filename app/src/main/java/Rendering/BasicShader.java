package Rendering;

import android.content.Context;

import com.blogspot.androidcanteen.androidopengl.R;

import Application.Camera;

public class BasicShader extends Shader {
    public BasicShader() {
        super("basic_vertex.txt", "basic_fragment.txt");
    }

    @Override
    public void updateUniforms(float[] modelMatrix, Camera renderingCamera) {
        super.updateUniforms(modelMatrix,renderingCamera);
    }


}
