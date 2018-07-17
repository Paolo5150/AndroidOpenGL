package ShaderObjects;

import Rendering.Camera;
import Rendering.Shader;

public class BasicShader extends Shader {
    public BasicShader() {
        super("BasicShader","basic_vertex.txt", "basic_fragment.txt");
    }

    @Override
    public void updateMatrices(float[] modelMatrix, Camera renderingCamera) {
        super.updateMatrices(modelMatrix,renderingCamera);
    }


}
