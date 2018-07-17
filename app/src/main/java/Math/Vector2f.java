package Math;

/**
 * Created by Paolo on 24/06/2018.
 */

public class Vector2f {

    public float x;
    public float y;



    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;

    }

    public Vector2f()
    {
        x = y = 0;
    }

    public static Vector2f subtract(Vector2f a, Vector2f b)
    {
        Vector2f r = new Vector2f();
        r.x = a.x - b.x;
        r.y = a.y - b.y;
        return r;
    }

    public void add(Vector2f v)
    {
        x += v.x;
        y += v.y;

    }

    public void substract(Vector2f v)
    {
        x -= v.x;
        y -= v.y;

    }

    public float dot(Vector2f v)
    {
        return (x * v.x) + (y * v.y);
    }

    public Vector3f cross(Vector2f v)
    {
        Vector3f r = new Vector3f();
        r.x = 0;
        r.y = 0;
        r.z = (x * v.y);
        return r;
    }

    public double length()
    {
        return Math.sqrt(x*x + y*y);
    }

    public void normalizeThis()
    {
        float  l= (float)length();
        x /= l;
        y /= l;

    }

    public void multiply(float f)
    {
        x *=f;
        y *=f;

    }

    public void divide(float f)
    {
        x /=f;
        y /=f;

    }
}
