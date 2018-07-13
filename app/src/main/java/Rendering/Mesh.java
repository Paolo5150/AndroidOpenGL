package Rendering;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import Math.Vertex;
import Math.*;

public class Mesh {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Mesh(Vertex[] vertices, int[] indices)
    {

        Initialize(vertices, indices);

    }

    @Override
    public void finalize()
    {
     //  GLES20.glDeleteBuffers(GLES20.GL_ARRAY_BUFFER,VBO);
     //  GLES20.glDeleteBuffers(GLES20.GL_ELEMENT_ARRAY_BUFFER,IBO);
    }

    public void cleanMemory()
    {
        GLES20.glDeleteBuffers(GLES20.GL_ARRAY_BUFFER,VBO);
        GLES20.glDeleteBuffers(GLES20.GL_ELEMENT_ARRAY_BUFFER,IBO);
    }

    public void NormalizeMesh()
    {

        Vector3f center = getCenter();

        Vector3f[] cToV = new Vector3f[vertices.length];
        float longest = -1;
        float length;
        for(int i=0; i< vertices.length; i++)
        {
            cToV[i] = Vector3f.subtract(vertices[i].getPosition(),center);
            length = cToV[i].length();

            if(length > longest)
                longest = length;

        }

        for(int i=0; i< vertices.length; i++)
        {
            cToV[i] = Vector3f.divide(cToV[i],longest);
            this.vertices[i].setPosition(cToV[i]);



        }



    }

    public Vector3f getCenter()
    {

        Vector3f center = new Vector3f();
        for(int i=0; i< vertices.length; i++)
        {
            center.x += vertices[i].getPosition().x;
            center.y += vertices[i].getPosition().y;
            center.z += vertices[i].getPosition().z;

        }

        center = Vector3f.divide(center,(float)vertices.length);
        return center;
    }

    private void CalculateNormals(Vertex[] vertices, int[] indices) {


        for(int i=0; i<indices.length; i+=3)
        {
            int i0 = indices[i];
            int i1 = indices[i+1];
            int i2 = indices[i+2];

            Vector3f v1 = vertices[i0].getPosition();
            Vector3f v2 = vertices[i1].getPosition();
            Vector3f v3 = vertices[i2].getPosition();

            Vector3f v1v2 = Vector3f.subtract(v2,v1);
            Vector3f v1v3 = Vector3f.subtract(v3,v1);
            Vector3f cross = v1v2.cross(v1v3);
            cross.normalizeThis();

            vertices[i0].setNormal(cross);
            vertices[i1].setNormal(cross);
            vertices[i2].setNormal(cross);

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void Initialize(Vertex[] vertices, int[] indices)
    {
        this.vertices = vertices;
        this.indices = IntBuffer.wrap(indices);
        this.indices.position(0);

        NormalizeMesh();
        CalculateNormals(vertices, indices);

        VAO = IntBuffer.allocate(1);
        VBO = IntBuffer.allocate(1);
        IBO = IntBuffer.allocate(1);


        GLES30.glGenVertexArrays(1,VAO);
        GLES30.glGenBuffers(1,VBO);
        GLES30.glGenBuffers(1,IBO);

        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));

        FloatBuffer  buffer = FloatBuffer.allocate(vertices.length * Vertex.BYTES);


        for(int i=0; i< vertices.length; i++)
        {
            buffer.put(vertices[i].getPosition().x);
            buffer.put(vertices[i].getPosition().y);
            buffer.put(vertices[i].getPosition().z);
            buffer.put(vertices[i].getTextureCoords().x);
            buffer.put(vertices[i].getTextureCoords().y);
            buffer.put(vertices[i].getNormal().x);
            buffer.put(vertices[i].getNormal().y);
            buffer.put(vertices[i].getNormal().z);
        }


        buffer.position(0);
       // buffer.flip();

        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,vertices.length * Vertex.BYTES,buffer,GLES30.GL_STATIC_DRAW );
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER,indices.length * Integer.BYTES,this.indices,GLES30.GL_STATIC_DRAW);

        //Vertex position
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,Vertex.SIZE * Float.BYTES,0);

        //Texture coordinates
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1,2,GLES30.GL_FLOAT,false,Vertex.SIZE  * Float.BYTES,3 * Float.BYTES);

        //Normals
        GLES30.glEnableVertexAttribArray(2);
        GLES30.glVertexAttribPointer(2,3,GLES30.GL_FLOAT,false,Vertex.SIZE  * Float.BYTES,5 * Float.BYTES);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void render()
    {
        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        GLES30.glDrawElements(GLES20.GL_TRIANGLES,this.indices.array().length,GLES30.GL_UNSIGNED_INT,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void renderTriangles()
    {
        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        GLES30.glDrawElements(GLES20.GL_LINES,this.indices.array().length,GLES30.GL_UNSIGNED_INT,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void renderLines()
    {
        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        GLES30.glDrawElements(GLES20.GL_LINES,this.indices.array().length,GLES30.GL_UNSIGNED_INT,0);
    }


    private Vertex[] vertices;
    private IntBuffer indices;
    private IntBuffer VBO;
    private IntBuffer IBO;
    private IntBuffer VAO;


}
