package MaterialObjects;

import Rendering.Material;
import Rendering.Shader;
import ShaderObjects.ShaderManager;

public class Material_GUI extends Material {
    public Material_GUI() {

        super("Material_GUI", ShaderManager.getShaderByName("GUIShader"));
        addFloat("alpha",1.0f);
    }
}
