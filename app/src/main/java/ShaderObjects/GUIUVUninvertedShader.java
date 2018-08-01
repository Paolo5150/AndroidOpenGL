package ShaderObjects;

import Rendering.Shader;

public class GUIUVUninvertedShader extends Shader {
    public GUIUVUninvertedShader() {
        super("GUIUVUninvertedShader", "gui_vertex_uv_uninverted.txt", "gui_fragment.txt");
    }
}
