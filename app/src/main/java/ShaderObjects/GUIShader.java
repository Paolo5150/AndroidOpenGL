package ShaderObjects;

import Rendering.Shader;

public class GUIShader extends Shader {
    public GUIShader() {
        super("GUIShader", "gui_vertex.txt", "gui_fragment.txt");
    }
}
