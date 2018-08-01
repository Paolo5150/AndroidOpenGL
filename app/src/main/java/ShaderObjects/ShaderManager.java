package ShaderObjects;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.HashMap;

import Rendering.Shader;

public class ShaderManager {

    private static HashMap<String, Shader> allShaders;

    public static  void Initialize()
    {
        allShaders = new HashMap<>();

        //Create shader
        LightShader lightShader = new LightShader();
        BasicShader basicShader = new BasicShader();
        SkyBoxShader skyBoxShader = new SkyBoxShader();
        GUIShader guiShader = new GUIShader();
        LightNoNormalShader lightNoNormalShader = new LightNoNormalShader();
        TerrainShader terrainShader = new TerrainShader();



        allShaders.put(lightNoNormalShader.getName(),lightNoNormalShader);
        allShaders.put(lightShader.getName(),lightShader);
        allShaders.put(basicShader.getName(),basicShader);
        allShaders.put(skyBoxShader.getName(),skyBoxShader);
        allShaders.put(guiShader.getName(),guiShader);
        allShaders.put(terrainShader.getName(),terrainShader);


    }

    public static Shader getShaderByName(String name)
    {
        if(allShaders.containsKey(name))
        {

           // GlobalVariables.logWithTag("Shader returned " + allShaders.get(name).getName());
            return allShaders.get(name);
        }

        else return null;
    }
}
