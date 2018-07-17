package Physics;

import java.util.ArrayList;

import Math.*;
import Rendering.Layer;

public class Ray {

    public Vector3f origin;
    public Vector3f direction;
    public float distance;


    public Ray()

    {
        origin = new Vector3f();
        direction = new Vector3f();
        distance = 0;

    }



    public Ray(Vector3f origin, Vector3f direction, float distance) {
        this.origin = origin;
        this.direction = direction;
        this.distance = distance;
    }

    public Vector3f pointOnRay(float distance) {
        Vector3f p;
        Vector3f distDir = Vector3f.multiply(direction,distance);
        p = Vector3f.add(origin,distDir);
        return p;
    }
}
