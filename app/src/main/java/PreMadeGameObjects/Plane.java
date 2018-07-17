package PreMadeGameObjects;

import Engine.GameObject;
import Math.*;

public class Plane extends GameObject {

    Vector3f normal;

    public Plane(Vector3f normal) {
        super("Plane");

        this.normal = normal;


    }

    public Vector3f getNormal() {
        return normal;
    }
}
