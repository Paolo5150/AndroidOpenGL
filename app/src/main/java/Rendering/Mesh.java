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

    private Vertex[] vertices;
    private int[] indices;

    private IntBuffer VBO;
    private IntBuffer IBO;
    private IntBuffer VAO;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Mesh(Vertex[] vertices, int[] indices, boolean normalize)
    {
        VAO = IntBuffer.allocate(1);
        VBO = IntBuffer.allocate(1);
        IBO = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays(1,VAO);
        GLES30.glGenBuffers(1,VBO);
        GLES30.glGenBuffers(1,IBO);
        Initialize(vertices, indices,normalize);

    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public IntBuffer getIBO() {
        return IBO;
    }

    public void setIBO(IntBuffer IBO) {
        this.IBO = IBO;
    }

    @Override
    public void finalize()
    {
     cleanMemory();
    }

    public void cleanMemory()
    {
        GLES20.glDeleteBuffers(1,VBO);
        GLES20.glDeleteBuffers(1,IBO);
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

    public void CalculateNormals() {


        //Normals
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

            //Tangent
            //Tangent
            Vector3f deltaPos1 = Vector3f.subtract(vertices[i1].getPosition(),vertices[i0].getPosition());
            Vector3f deltaPos2 = Vector3f.subtract(vertices[i2].getPosition(),vertices[i0].getPosition());


            Vector2f deltaUV1 = Vector2f.subtract(vertices[i1].getTextureCoords(),vertices[i0].getTextureCoords());
            Vector2f deltaUV2 = Vector2f.subtract(vertices[i2].getTextureCoords(),vertices[i0].getTextureCoords());

            float r = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);

            vertices[i0].setTangent(  Vector3f.multiply(Vector3f.subtract(Vector3f.multiply(deltaPos1,deltaUV2.y),Vector3f.multiply(deltaPos2,deltaUV1.y)),r  ));// = glm::normalize((deltaPos1 * deltaUV2.y - deltaPos2 * deltaUV1.y)*r);
            vertices[i1].setTangent(  Vector3f.multiply(Vector3f.subtract(Vector3f.multiply(deltaPos1,deltaUV2.y),Vector3f.multiply(deltaPos2,deltaUV1.y)),r  ));
            vertices[i2].setTangent(  Vector3f.multiply(Vector3f.subtract(Vector3f.multiply(deltaPos1,deltaUV2.y),Vector3f.multiply(deltaPos2,deltaUV1.y)),r  ));

            vertices[i0].setBiTangent(  Vector3f.multiply(Vector3f.subtract(Vector3f.multiply(deltaPos2,deltaUV1.x),Vector3f.multiply(deltaPos1,deltaUV2.x)),r  ));
            vertices[i1].setBiTangent(  Vector3f.multiply(Vector3f.subtract(Vector3f.multiply(deltaPos2,deltaUV1.x),Vector3f.multiply(deltaPos1,deltaUV2.x)),r  ));
            vertices[i2].setBiTangent(  Vector3f.multiply(Vector3f.subtract(Vector3f.multiply(deltaPos2,deltaUV1.x),Vector3f.multiply(deltaPos1,deltaUV2.x)),r  ));


        }




    }

    public void UpdateVertices(Vertex[] newVertices, int[] indices)
    {
        this.vertices = newVertices;
        this.indices = indices;
        CalculateNormals();

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO.get(0));



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
            buffer.put(vertices[i].getTangent().x);
            buffer.put(vertices[i].getTangent().y);
            buffer.put(vertices[i].getTangent().z);
            buffer.put(vertices[i].getBiTangent().x);
            buffer.put(vertices[i].getBiTangent().y);
            buffer.put(vertices[i].getBiTangent().z);
        }


        buffer.position(0);
        // buffer.flip();

        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,vertices.length * Vertex.BYTES,buffer,GLES30.GL_STATIC_DRAW );


        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        IntBuffer buf = IntBuffer.wrap(indices);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER,indices.length * Integer.BYTES,buf,GLES30.GL_STATIC_DRAW);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
     void Initialize(Vertex[] vertices, int[] indices, boolean normalize)
    {
        this.vertices = vertices;

        this.indices = indices;


        if(normalize)
         NormalizeMesh();

       CalculateNormals();


        GLES30.glBindVertexArray(VAO.get(0));
        UpdateVertices(vertices,indices);


        //Vertex position
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,Vertex.SIZE * Float.BYTES,0);

        //Texture coordinates
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1,2,GLES30.GL_FLOAT,false,Vertex.SIZE  * Float.BYTES,3 * Float.BYTES);

        //Normals
        GLES30.glEnableVertexAttribArray(2);
        GLES30.glVertexAttribPointer(2,3,GLES30.GL_FLOAT,false,Vertex.SIZE  * Float.BYTES,5 * Float.BYTES);

        //Tangent
        GLES30.glEnableVertexAttribArray(3);
        GLES30.glVertexAttribPointer(3,3,GLES30.GL_FLOAT,false,Vertex.SIZE  * Float.BYTES,8 * Float.BYTES);

        //Bitangent
        GLES30.glEnableVertexAttribArray(4);
        GLES30.glVertexAttribPointer(4,3,GLES30.GL_FLOAT,false,Vertex.SIZE  * Float.BYTES,11 * Float.BYTES);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void render()
    {
        //GlobalVariables.logWithTag("In Mesh-render(), VAO is " + VBO.get(0));
        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        GLES30.glDrawElements(GLES20.GL_TRIANGLES,this.indices.length,GLES30.GL_UNSIGNED_INT,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void renderTriangles()
    {
        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        GLES30.glDrawElements(GLES20.GL_LINES,this.indices.length,GLES30.GL_UNSIGNED_INT,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void renderLines()
    {
        GLES30.glBindVertexArray(VAO.get(0));
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER,IBO.get(0));
        GLES30.glDrawElements(GLES20.GL_LINES,this.indices.length,GLES30.GL_UNSIGNED_INT,0);
    }

    public int[] getIndices() {
        return indices;
    }
}
