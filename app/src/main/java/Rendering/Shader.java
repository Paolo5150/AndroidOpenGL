package Rendering;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.os.Debug;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

/**
 * Created by Paolo on 16/06/2018.
 */

public class Shader {

    public Shader(Context context, int vertexRawID, int fragmentRawID)
    {

        this.context = context;

        vertexSource = getSourceFromRaw(vertexRawID);
        fragmentSource = getSourceFromRaw(fragmentRawID);

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
            Log.d(GlobalVariables.TAG,"Error while linking shader:\n" + msg);
        }

        Log.d(GlobalVariables.TAG,"Shader id generated" + getShaderID());

    }

    public int getShaderID()
    {
        return shaderID;
    }

    public void activateShader()
    {
        GLES30.glUseProgram(shaderID);
    }

    public void setMat4(String uniformName, float[] mat)
    {
        GLES30.glUseProgram(getShaderID());
        int value = GLES20.glGetUniformLocation(getShaderID(), uniformName);
        GLES20.glUniformMatrix4fv(value, 1, false, mat, 0);
    }

    private String getSourceFromRaw(int rawID)
    {

        InputStream is = context.getResources().openRawResource(rawID);

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
            Log.d(GlobalVariables.TAG,"Error while compiling vertex shader:\n" + msg);
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
            Log.d(GlobalVariables.TAG,"Error while compiling vertex shader:\n" + msg);
        }
        return id;
    }

    private int shaderID;

    private String vertexSource;
    private String fragmentSource;
    private Context context;
}
