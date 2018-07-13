package Rendering;

import Math.Vector3f;
public class Light {

    public Vector3f color;
    public Vector3f position;
    public Vector3f rotation;
    String type;

    public Light()
    {
        color = new Vector3f(1,1,1);
        position = new Vector3f();
        rotation = new Vector3f();

    }

    public Light(Vector3f col, Vector3f pos, Vector3f rot)
    {
        color = col;
        position = pos;
        rotation = rot;

    }
}
