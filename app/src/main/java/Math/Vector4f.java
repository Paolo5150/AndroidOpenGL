package Math;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Vector4f {

    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector3f v)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = 0;
    }

    public Vector4f(Vector3f v, float wv)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = wv;
    }

    public Vector4f()
    {
        x = y = z = w = 0;
    }

    public void add(Vector4f v)
    {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
    }

    public void substract(Vector4f v)
    {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
    }



    public double length()
    {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public void multiply(float f)
    {
        x *=f;
        y *=f;
        z *=f;
        w *=f;
    }

    public void divide(float f)
    {
        x /=f;
        y /=f;
        z /=f;
        z /=f;
    }
}
