package ShaderObjects;

import Rendering.Camera;
import Rendering.Lighting;
import Rendering.Shader;

public class WaterShader extends LightNormalFogShader {
    public WaterShader() {
        super("WaterShader", "water_vertex.txt", "water_fragment.txt");
    }


}
