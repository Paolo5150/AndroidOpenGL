package ShaderObjects;

import Rendering.Shader;

public class SkyBoxShader extends Shader {
    public SkyBoxShader() {
        super("SkyBoxShader", "skybox_vertex.txt", "skybox_fragment.txt");
    }
}
