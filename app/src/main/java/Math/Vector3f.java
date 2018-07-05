package Math;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f()
    {
        x = y = z = 0;
    }

    public void add(Vector3f v)
    {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void substract(Vector3f v)
    {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public float dor(Vector3f v)
    {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    public Vector3f cross(Vector3f v)
    {
        Vector3f r = new Vector3f();
        r.x = (y * v.z) - (z * v.y);
        r.y = (z * v.x) - (x * v.z);
        r.z = (x * v.y) - (y * v.z);
        return r;
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
    }

    public void divide(float f)
    {
        x /=f;
        y /=f;
        z /=f;
    }
}
