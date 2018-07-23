package Rendering;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import Engine.AssetLoader;
import Engine.Entity;
import Engine.Utils;
import Math.*;
/**
 * Created by Paolo on 16/06/2018.
 */

public class Shader extends Entity{

    private int shaderID;

    private String vertexSource;
    private String fragmentSource;

    public Shader(String shaderName,String vertex, String fragment){
        super(shaderName,"Shader");


        vertexSource = getSourceFromRaw(vertex);
        fragmentSource = getSourceFromRaw(fragment);

       // Log.d(GlobalVariables.TAG,"Vertex source:\n" + vertexSource);

        int vs = compileVertexShader();
        int fs = compileFragmentShader();

        shaderID = GLES30.glCreateProgram();
        GLES30.glAttachShader(shaderID,vs);
        GLES30.glAttachShader(shaderID,fs);

        int[] buf = new int[1];

        GLES30.glLinkProgram(shaderID);
        GLES30.glGetProgramiv(shaderID, GLES30.GL_LINK_STATUS,buf,0);
        if(buf[0] == 0)
        {
            String msg = GLES30.glGetProgramInfoLog(shaderID);
            Log.d(GlobalVariables.TAG,"Error while linking shader " + shaderName + ":\n" + msg);
        }

       // Log.d(GlobalVariables.TAG,"Shader id generated" + getShaderID());

    }

    public int getShaderID()
    {
        return shaderID;
    }

    public void activateShader()
    {

        if(RenderingEngine.currentShaderID != getShaderID()) {
            //GlobalVariables.logWithTag("Activated shader " + getName());
            RenderingEngine.currentShaderID = shaderID;
            GLES30.glUseProgram(shaderID);
        }
    }



    public void updateMatrices(float[] modelMatrix, Camera renderingCamera)
    {
        float[] mvp = new float[16];

        Matrix.multiplyMM(mvp,0,renderingCamera.getProj(),0,renderingCamera.getViewM(),0);
        Matrix.multiplyMM(mvp,0,mvp,0,modelMatrix,0);

        setMat4("mvp",mvp);
        setMat4("model",modelMatrix);
        setMat4("view",renderingCamera.getViewM());
        setMat4("projection",renderingCamera.getProj());


    }

    public void setMat4(String uniformName, float[] mat)
    {
        GLES30.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(), uniformName);
        GLES20.glUniformMatrix4fv(location, 1, false, mat, 0);
    }

    public void setInt(String uniformName, int value)
    {
        GLES30.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(), uniformName);
        GLES20.glUniform1i(location,value);
    }

    public void setFloat(String uniformName, float value)
    {
        GLES30.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(), uniformName);
        GLES20.glUniform1f(location,value);
    }

    public void setVec4(String uniformName, Vector4f v)
    {
        GLES20.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(),uniformName);
        GLES20.glUniform4f(location,v.x,v.y,v.z,v.w);

    }

    public void setVec3(String uniformName, Vector3f v)
    {
        GLES30.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(),uniformName);
        GLES30.glUniform3f(location,v.x,v.y,v.z);

    }

    public void setVec4(String uniformName, float x, float y, float z, float w)
    {
        GLES20.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(),uniformName);
        GLES20.glUniform4f(location,x,y,z,w);

    }


    public void setVec3(String uniformName, float x, float y ,float z)
    {
        GLES20.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(),uniformName);
        GLES20.glUniform3f(location,x,y,z);

    }

    public void setVec2(String uniformName, float x, float y)
    {
        GLES20.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(),uniformName);
        GLES20.glUniform2f(location,x,y);
    }

    public void setVec2(String uniformName, Vector2f v)
    {
        GLES20.glUseProgram(getShaderID());
        int location = GLES20.glGetUniformLocation(getShaderID(),uniformName);
        GLES20.glUniform2f(location,v.x,v.y);
    }

    private String getSourceFromRaw(String shaderName)
    {

        InputStream is = AssetLoader.getInstance().getShaderInputStream(shaderName);

        ByteArrayOutputStream baos = null;

        try
        {

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            baos = new ByteArrayOutputStream();
            baos.write(buffer);
            baos.close();
            is.close();

        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d(GlobalVariables.TAG, "Shader problem, could not get the input stream");
        }

        return baos.toString();


    }



    private int compileVertexShader()
    {

        int id = GLES20.glCreateShader(GLES30.GL_VERTEX_SHADER);
        GLES30.glShaderSource(id,vertexSource);
        GLES30.glCompileShader(id);

        int[] buf = new int[1];
        GLES30.glGetShaderiv(id,GLES30.GL_COMPILE_STATUS,buf,0);

        if(buf[0] == 0)
        {
            String msg = GLES30.glGetShaderInfoLog(id);
            Log.d(GlobalVariables.TAG,"Error while compiling vertex shader (" +getName() + "):\n" + msg);
        }
        return id;
    }

    private int compileFragmentShader()
    {

        int id = GLES20.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(id,fragmentSource);
        GLES30.glCompileShader(id);

        int[] buf = new int[1];
        GLES30.glGetShaderiv(id,GLES30.GL_COMPILE_STATUS,buf,0);

        if(buf[0] == 0)
        {
            String msg = GLES30.glGetShaderInfoLog(id);
            Log.d(GlobalVariables.TAG,"Error while compiling fragment shader (" + getName() + "):\n" + msg);
        }
        return id;
    }


    @Override
    protected void GenerateID() {
        ID = "Shader" + name + System.currentTimeMillis() + Utils.GetRandomFloat();
    }




}
