package Math;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import Math.Vector3f;
/**
 * Created by Paolo on 19/06/2018.
 */

public class Vertex {

    public static final int SIZE = 14;
    public static final int BYTES = SIZE * Float.BYTES;

    private Vector3f position;
    private Vector2f textureCoords;
    private Vector3f normal;
    private Vector3f tangent;
    private Vector3f biTangent;

    public Vertex()
    {
        InitializeAll();

    }

    public Vertex(float x, float y, float z)
    {
        InitializeAll();
       position = new Vector3f(x,y,z);

    }

    public Vertex(float x, float y, float z, float xc, float yc)
    {
        InitializeAll();
        position = new Vector3f(x,y,z);
        textureCoords = new Vector2f(xc,yc);

    }


    public Vertex(float x, float y, float z, float xc, float yc, float nx, float ny, float nz)
    {
        InitializeAll();
        position = new Vector3f(x,y,z);
        textureCoords = new Vector2f(xc,yc);
        normal = new Vector3f(nx,ny,nz);
    }

    private void InitializeAll()
    {
        position = new Vector3f();
        normal = new Vector3f();
        textureCoords = new Vector2f();
        tangent = new Vector3f();
        biTangent = new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }

    public void setTextureCoords(Vector2f textureCoords) {
        this.textureCoords = textureCoords;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public void setTangent(Vector3f tangent) {
        this.tangent = tangent;
    }

    public void setBiTangent(Vector3f biTangent) {
        this.biTangent = biTangent;
    }

    public Vector3f getTangent() {
        return tangent;
    }

    public Vector3f getBiTangent() {
        return biTangent;
    }
}
