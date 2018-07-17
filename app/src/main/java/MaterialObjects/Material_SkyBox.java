package MaterialObjects;

import android.opengl.Matrix;

import Rendering.CubeMap;
import Rendering.Material;
import Rendering.Shader;
import ShaderObjects.ShaderManager;
import ShaderObjects.SkyBoxShader;

public class Material_SkyBox extends Material {


    public Material_SkyBox() {
        super("Material_SkyBox", new SkyBoxShader());




    }
}
