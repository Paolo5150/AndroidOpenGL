package Application;

import android.app.Activity;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.R;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL;

import Rendering.Screen;
import Rendering.Shader;
import Math.Vector3f;
import Math.Vertex;
import Rendering.ShaderManager;

/**
 * Created by Paolo on 25/06/2018.
 */

public class Application {

    private Activity activity;


    Camera cam;
    Shader testShader;
    IntBuffer VAO;
    IntBuffer VBO;

    float[] model;
    float angle;

    public Application(Activity a)
    {

        activity = a;


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Start()
    {

        cam = new Camera();
        cam.setPerspective(60,(float) Screen.SCREEN_WIDTH / Screen.SCREEN_HEIGHT,0.1f,1000.0f);
        cam.position.z = 5;




        Vertex[] vertices = {new Vertex(-0.5f,-0.5f,0.f),
                new Vertex(0.5f,-0.5f,0.0f),new Vertex(0.0f, 0.5f, 0.0f)};

        float[] verts = {-0.5f,-0.5f,0.0f,
                0.5f,-0.5f,0.0f,
                0.0f, 0.5f, 0.0f};
        model = new float[16];
        Matrix.setIdentityM(model,0);



        VAO = IntBuffer.allocate(1);
        VBO = IntBuffer.allocate(1);


        GLES30.glGenVertexArrays(1,VAO);
        GLES30.glGenBuffers(1,VBO);


        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO.get(0));

        FloatBuffer point = FloatBuffer.allocate(9);

        point.put( vertices[0].toFloatBuffer());
        point.put( vertices[1].toFloatBuffer());
        point.put( vertices[2].toFloatBuffer());
        point.position(0);


        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,9 * Float.BYTES,point,GLES30.GL_STATIC_DRAW );



        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,3 * Float.BYTES,0);
    }

    public void Update()
    {
        cam.Update();

        Matrix.setIdentityM(model,0);


        Matrix.rotateM(model,0,angle,0,1,0);

        float[] mvp = new float[16];


        Matrix.multiplyMM(mvp,0,cam.getProj(),0,cam.getViewM(),0);
        Matrix.multiplyMM(mvp,0,mvp,0,model,0);

        angle += 1f;
        ShaderManager.GetShaderByName("Basic").setMat4("matrix",mvp);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Render()
    {

        GLES30.glBindVertexArray(VAO.get(0));
        ShaderManager.GetShaderByName("Basic").activateShader();

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,3);







    }
}
