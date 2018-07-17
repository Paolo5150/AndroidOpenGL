package MaterialObjects;

import Engine.AssetLoader;
import Rendering.Material;
import Rendering.Shader;
import Rendering.Texture;
import ShaderObjects.ShaderManager;

public class Material_BumpyWall extends Material {
    public Material_BumpyWall() {
        super("Material_BumpyWall", ShaderManager.getShaderByName("LightShader"));


        Texture diffuse0 = new Texture("wall.jpg","texture_diffuse0");

        Texture normal = new Texture("wall_normal.jpg","texture_normal0");

        addTexture(diffuse0);
        addTexture(normal);
        addVec3("color",1,1,1);
        addVec3("specularColor",1,1,1);
        addFloat("shininess",50);
    }
}
