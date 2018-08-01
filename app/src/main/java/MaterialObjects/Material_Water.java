package MaterialObjects;

import Application.AppCanvas;
import Rendering.Material;
import Rendering.Shader;
import Rendering.Texture;
import ShaderObjects.WaterShader;

public class Material_Water extends Material {


    public Texture dudv;
    Texture normalMap;
    Texture normalMap2;


    public Material_Water() {

        super("Material_Water", new WaterShader());

        updateFloat("UVScale", 10);



        dudv = new Texture("water_dudv.jpg","texture_dudv");
        normalMap = new Texture("water_normal.jpg","texture_normal0");
        normalMap2 = new Texture("water_normal2.jpg","texture_normal1");


        addTexture(normalMap);
       addTexture(normalMap2);
        addTexture(dudv);



        addVec3("specularColor",1,1,1);
        addFloat("shininess",8);
    }
}
