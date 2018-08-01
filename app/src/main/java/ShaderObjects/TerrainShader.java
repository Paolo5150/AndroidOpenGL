package ShaderObjects;

import Rendering.Camera;
import Rendering.Lighting;
import Rendering.Shader;

public class TerrainShader extends LightNormalFogShader {
    public TerrainShader() {
        super("TerrainShader", "terrain_vertex.txt", "terrain_fragment.txt");
    }

}

