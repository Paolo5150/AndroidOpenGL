package PreMadeGameObjects;

import Engine.GameObject;
import Math.*;

public class Plane extends GameObject {

    Vector3f normal;
    Vector3f position;

    public Plane(Vector3f position, Vector3f normal) {
        super("Plane");

        this.normal = normal;
        this.position = position;


    }

    public void setNormal(Vector3f n)
    {
        this.normal = n;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setPosition(Vector3f pos)
    {
        position = pos;
    }

    public Vector3f getPosition()
    {
        return position;
    }
}
