package MaterialObjects;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.AssetLoader;
import Rendering.Material;
import Rendering.Shader;
import Rendering.Texture;
import ShaderObjects.LightShader;
import ShaderObjects.ShaderManager;

public class Material_BumpyRock extends Material{

    public Material_BumpyRock() {
        super("Material_BumpyRock", ShaderManager.getShaderByName("LightShader"));

        Texture diffuse0 = new Texture("rock_diffuse.jpg","texture_diffuse0");
        Texture diffuse1 = new Texture("wall.jpg","texture_diffuse1");
        Texture normal = new Texture("rock_normal.png","texture_normal0");

        addTexture(diffuse1);
       addTexture(diffuse0);

       addTexture(normal);


       addVec3("color",1,1,1);
       addVec3("specularColor",1,1,1);
       addFloat("shininess",5);




    }
}
