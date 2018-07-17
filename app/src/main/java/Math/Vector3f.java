package Math;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public static Vector3f add(Vector3f v1, Vector3f v2)
    {
        Vector3f res = new Vector3f();
        res.x = v1.x + v2.x;
        res.y = v1.y + v2.y;
        res.z = v1.z + v2.z;
        return res;

    }

    public static Vector3f subtract(Vector3f v1, Vector3f v2)
    {
        Vector3f res = new Vector3f();
        res.x = v1.x - v2.x;
        res.y = v1.y - v2.y;
        res.z = v1.z - v2.z;
        return res;

    }

    public static Vector3f multiply(Vector3f v1, float scalar)
    {
        Vector3f res = new Vector3f();
        res.x = v1.x * scalar;
        res.y = v1.y * scalar;
        res.z = v1.z * scalar;
        return res;

    }

    public static Vector3f project(Vector3f v, Vector3f onTo)
    {
        Vector3f res = new Vector3f();

        float dot = v.dot(onTo);
        float ontoLength = onTo.length();
        float div = dot / (ontoLength * ontoLength);
        res = Vector3f.multiply(onTo,div);
        return res;
    }

    public static Vector3f divide(Vector3f v1, float scalar)
    {
        Vector3f res = new Vector3f();
        res.x = v1.x / scalar;
        res.y = v1.y / scalar;
        res.z = v1.z / scalar;
        return res;

    }



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

    public float dot(Vector3f v)
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

    public float length()
    {

        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public void normalizeThis()
    {
        float l = length();
        x /= l;
        y /= l;
        z/=l;


    }

    public Vector3f multiply(float f)
    {
        x *=f;
        y *=f;
        z *=f;
        return this;
    }

    public void divide(float f)
    {
        x /=f;
        y /=f;
        z /=f;
    }

    public String getInfo()
    {
        String s = "";
        s += "X: " + x + ", Y: " + y + ", Z " + z +", M: " + length();
        return s;

    }
}
