package Rendering;

import android.opengl.Matrix;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Application.Camera;
import Math.*;

public class Material {

    public Material()
    {
        specularColor = new Vector3f(1,1,1);
        color = new Vector3f(1,1,1);
        shininess = 32;
    }


    public Material(Shader shader,Vector3f col, Texture text, Vector3f spec, float sh)
    {
        texture = text;
        color = col;
        specularColor = spec;
        shininess = sh;
        this.shader = shader;
        shader.setMaterial(this);
    }

    public void ActivateMaterial(float[] modelMatrix, Camera renderingCamera)
    {


        shader.activateShader();

        if(texture!=null)
        texture.bind();
    }



    public Vector3f color;
    public Vector3f specularColor;
    public Texture texture;
    public Shader shader;
    float shininess;

}
