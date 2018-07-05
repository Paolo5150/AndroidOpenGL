package Math;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Paolo on 19/06/2018.
 */

public class Vertex {

    private float x;
    private float y;
    private float z;

    public Vertex()
    {
        x = 0;
        y = 0;
        z = 0;

    }

    public Vertex(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ByteBuffer toByteBuffer()
    {

        ByteBuffer bf = ByteBuffer.allocate(4 * 3);
        bf.putFloat(x);
        bf.putFloat(y);
        bf.putFloat(z);
        bf.position(0);
        return bf;
    }

    public FloatBuffer toFloatBuffer()
    {

        FloatBuffer fb = FloatBuffer.allocate(3);
        fb.put(x);
        fb.put(y);
        fb.put(z);
        fb.position(0);
        return fb;
    }
    public float[] toFloatArray()
    {
        float[] ar = new float[3];
        ar[0] = x;
        ar[1] = y;
        ar[2] = z;
        return  ar;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }



}
