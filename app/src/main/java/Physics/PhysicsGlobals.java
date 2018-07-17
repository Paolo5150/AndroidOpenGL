package Physics;
import Engine.GameObject;
import Math.*;
import PreMadeGameObjects.Plane;

public class PhysicsGlobals {

    public static Vector3f gravity = new Vector3f(0,-8,0);

    public static float distanceFromPlane(Vector3f point, Plane plane)
    {
        Vector3f pointToPlane = Vector3f.subtract(plane.transform.position,point);
        Vector3f shortest = Vector3f.project(pointToPlane,plane.getNormal());
        return shortest.length();
    }


}
