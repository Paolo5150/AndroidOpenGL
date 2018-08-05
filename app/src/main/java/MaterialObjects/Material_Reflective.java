package MaterialObjects;

import Rendering.CubeMap;
import Rendering.Material;
import Rendering.Shader;
import ShaderObjects.ReflectiveShader;
import ShaderObjects.ShaderManager;

public class Material_Reflective extends Material {
    public Material_Reflective() {
        super("Material_Reflective", new ReflectiveShader());

        CubeMap c = new CubeMap("SunSet","env");

        addCubeMap(c);

        addVec3("specularColor",1,1,1);
        addFloat("shininess",8);
    }
}
