package Physics;
import Engine.GameObject;
import Math.*;
import PreMadeGameObjects.Plane;
import Rendering.Triangle;

public class PhysicsGlobals {

    public static Vector3f gravity = new Vector3f(0,-3.0f,0);

    public static Vector3f shortestToPlane(Vector3f point, Vector3f planePos, Vector3f planenorm)
    {

        Vector3f normNeg = Vector3f.multiply(planenorm,-1);
        normNeg.normalizeThis();;
        Vector3f pointToPlane = Vector3f.subtract(planePos,point);

        return Vector3f.project(pointToPlane,normNeg);
    }

    public static Vector3f shortestToPlane(Vector3f point, Plane plane)
    {
        Vector3f pointToPlane = Vector3f.subtract(plane.transform.position,point);
        return Vector3f.project(pointToPlane,plane.getNormal());
    }

    public static float distanceFromPlane(Vector3f point, Plane plane)
    {
      Vector3f shortest = shortestToPlane(point,plane);
        return shortest.length();
    }

    public static Vector3f rayIntersectPlane(Ray r, Plane p)
    {
        Vector3f res = null;

        float dot = r.direction.dot(Vector3f.multiply(p.getNormal(),-1));

        if(dot <= 0)
            return null;

        float cosA = dot / (r.direction.length() * p.getNormal().length());
        float adj = distanceFromPlane(r.origin,p);
        float hLength = adj / cosA;


        res = Vector3f.add(r.origin,Vector3f.multiply(r.direction,hLength));


        return res;
    }


    public static Vector3f rayIntersectTriangle(Ray r, Triangle t)
    {
        Vector3f result = null;



		 double EPSILON = 0.0000001f;

        Vector3f vertex0 = t.v1.getPosition();
        Vector3f vertex1 = t.v2.getPosition();
        Vector3f vertex2 = t.v3.getPosition();

        Vector3f edge1, edge2, h, s, q;
        float dotEdgeH, f, u, v;

        edge1 = Vector3f.subtract(vertex1 ,vertex0);
        edge2 = Vector3f.subtract(vertex2 ,vertex0);
        Vector3f rayVector = r.direction;



        h = rayVector.cross(edge2);


        dotEdgeH = edge1.dot(h);

        if (dotEdgeH > -EPSILON && dotEdgeH < EPSILON)
        {
           result = null;

        }

        f = 1 / dotEdgeH;

        s = Vector3f.subtract(r.origin , vertex0);
        u = f * (s.dot(h));

        if (u < 0.0f || u > 1.0f)
        {

            result =  null;
            return null;
        }


        q = s.cross(edge1);


        v = f * rayVector.dot(q);
        if (v < 0.0f || u + v > 1.0f)
        {
            result = null;
            return null;
        }
        // At this stage we can compute t to find out where the intersection point is on the line.


        float tt = f * edge2.dot(q);
        if (tt > EPSILON) // ray intersection
        {

            result = Vector3f.add(r.origin,Vector3f.multiply(rayVector,tt));

        }
        else // This means that there is a line intersection but not a ray intersection.
            result = null;


        return result;
    }


}
