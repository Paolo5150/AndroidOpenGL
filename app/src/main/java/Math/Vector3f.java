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

    public static Vector3f toDifferentCoordinate(Vector3f v,float[] transformationMatrix, float w)
    {
        Vector3f res = new Vector3f(v);
        float[] sl  =new float[4];
        android.opengl.Matrix.multiplyMV(sl,0,transformationMatrix,0,res.toFloat4f(w),0);
        res.wrap(sl);
        return res;
    }

    public static Vector3f lerp(Vector3f start, Vector3f end, float t)
    {
        Vector3f s = Vector3f.multiply(start, 1.0f - t);
        Vector3f e = Vector3f.multiply(end,t);

        return Vector3f.add(s,e);
    }

    public static Vector3f subtract(Vector3f v1, Vector3f v2)
    {
        Vector3f res = new Vector3f();
        res.x = v1.x - v2.x;
        res.y = v1.y - v2.y;
        res.z = v1.z - v2.z;
        return res;

    }

    public static Vector3f negate(Vector3f v)
    {
        return multiply(v,-1);
    }

    public static Vector3f normalize(Vector3f v)
    {
        Vector3f res = new Vector3f();
        float l = v.length();
        res.x = v.x/ l;
        res.y = v.y / l;
        res.z = v.z / l;
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

    public static Vector3f reflect(Vector3f direction, Vector3f normal)
    {
        Vector3f r;

        Vector3f normD = normalize(direction);
        Vector3f normN = normalize(normal);

        float dot2 = normD.dot(normN) * 2;

        Vector3f temp = multiply(normN,dot2);

        r = Vector3f.subtract(normD,temp);
        r.normalizeThis();

        return r;

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

    public Vector3f(Vector3f other)
    {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
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
        r.z = (x * v.y) - (y * v.x);
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



    public void wrap(float[] array)
    {
        x = array[0];
        y = array[1];
        z = array[2];
    }

    public float[] toFloat4f(float w)
    {
        float[] res = new float[4];
        res[0] = x;
        res[1] = y;
        res[2] = z;
        res[3] = w;
        return res;
    }
}
