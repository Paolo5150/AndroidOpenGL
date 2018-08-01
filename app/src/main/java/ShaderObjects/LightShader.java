package ShaderObjects;

import Rendering.Camera;
import Rendering.Lighting;
import Rendering.Shader;

public class LightShader extends LightNormalFogShader {

    public LightShader() {

        super("LightShader","light_vertex.txt","light_fragment.txt");
    }


}
