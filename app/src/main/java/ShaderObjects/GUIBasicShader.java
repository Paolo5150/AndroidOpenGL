package ShaderObjects;

import Rendering.Shader;

public class GUIBasicShader extends Shader {
    public GUIBasicShader() {
        super("GUIBasicShader", "gui_basic_vertex.txt", "gui_basic_fragment.txt");
    }
}
