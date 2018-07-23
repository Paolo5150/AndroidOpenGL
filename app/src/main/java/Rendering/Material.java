package Rendering;

import android.opengl.GLES10;
import android.opengl.GLES30;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;

import Engine.Entity;
import Math.*;

public class Material extends Entity {

    protected HashMap<String, Vector3f> vec3s;
    protected HashMap<String, Vector2f> vec2s;
    protected HashMap<String, Float> floats;
    protected HashMap<String, Texture> textures;
    protected HashMap<String, CubeMap> cubeMaps;
    protected Shader shader;



    public Material(String name, Shader s)
    {
        super(name, "Material");

        setShader(s);


        Initialize();

        //Load default values
        addFloat("UVScale",3);
        addVec2("UVOffset",new Vector2f());
        addVec3("color",1,1,1);
        addFloat("alpha",1.0f);

    }

    public Material(String name, Shader s, Texture text)
    {
        super(name,"Material");
        Initialize();
        setShader(s);



        //Load default values
        addFloat("alpha",1.0f);
        addFloat("UVScale",3);
        addVec2("UVOffset",new Vector2f());
        addTexture(text);

    }



    private void Initialize()
    {
        vec3s = new HashMap<>();
        floats = new HashMap<>();
        textures = new HashMap<>();
        vec2s = new HashMap<>();
        cubeMaps = new HashMap<>();
    }


    //Used in normal rendering only (not in batching)
    public void ActivateMaterial(float[] modelMatrix, Camera renderingCamera)
    {
         shader.activateShader();

         shader.updateMatrices(modelMatrix,renderingCamera);

        updateMaterialPropertiesToShader();

        activateTextures();

    }

    public void updateMaterialPropertiesToShader()
    {   //Updating uniforms

        for(String s : vec3s.keySet())
        {

            shader.setVec3("material." + s,vec3s.get(s));
        }
        for(String s : vec2s.keySet())
        {
            shader.setVec2("material." + s,vec2s.get(s));
        }
        for(String s : floats.keySet())
        {
            shader.setFloat("material." +s,floats.get(s));
        }


    }

    public void activateTextures()
    {
        int counter = 0;

        //BIND TEXTURES
        for(String s : textures.keySet())
        {

                shader.setInt(s ,counter);
                GLES10.glActiveTexture(GLES10.GL_TEXTURE0 + counter);
                GLES10.glBindTexture(GLES10.GL_TEXTURE_2D,textures.get(s).getTextureID());
               // GlobalVariables.logWithTag("\t\tIn material (activate textures), Activated " + textures.get(s).getUniformName() + " Name: " + textures.get(s).getName());
               counter++;

        }

        counter  = 0;
        //BIND Cubemaps
        for(String s : cubeMaps.keySet())
        {

            shader.setInt(s,counter);
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + counter);
            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,cubeMaps.get(s).getCubeMapID());
            //  GlobalVariables.logWithTag("In material (activate textures), Activated " + s);
            counter++;
        }
    }





    public void addFloat(String name, float f)
    {
        floats.put(name,f);
    }

    public void updateFloat(String name, float f)
    {
        if(floats.containsKey(name))
            floats.put(name,f);
    }

    public float getFloat(String name)
    {
        if(floats.containsKey(name))
            return floats.get(name);
        else
            return -1; //mmm, not really good
    }

    public void addVec2(String name, Vector2f v)
    {
        vec2s.put(name,v);
    }

    public void updateVec2(String name, Vector2f v)
    {
        if(vec2s.containsKey(name)) {
            vec2s.put(name,v);

        }
    }

    public void updateVec3(String name, Vector3f v)
    {
        if(vec3s.containsKey(name)) {
            vec3s.put(name,v);

        }
    }
    public void addVec2(String name, float x, float y)
    {
        vec2s.put(name,new Vector2f(x,y));
    }

    public void addVec3(String name, Vector3f v)
    {
        vec3s.put(name,v);
    }

    public void addVec3(String name, float x, float y, float z)
    {
        vec3s.put(name,new Vector3f(x,y,z));
    }
    public  Vector3f getVec3(String name)
    {
        if(floats.containsKey(name))
            return vec3s.get(name);
        else
            return null;
    }

    public void addTexture(Texture t)
    {
       textures.put(t.getUniformName(),t);


    }



    public void addCubeMap( CubeMap c)
    {
        cubeMaps.put(c.getUniformName(),c);
    }





    public Shader getShader() {
        return shader;
    }



    public String getName() {
        return name;
    }

    public String getId() {
        return ID;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
