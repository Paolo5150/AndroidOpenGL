package Rendering;
import Math.*;

public class DirectionalLight extends Light {

    public DirectionalLight()
    {
        super();
        type = "Directional";
    }

    public DirectionalLight(Vector3f col, Vector3f pos, Vector3f rot)
    {
        super(col,pos,rot);
        type = "Directional";
    }
}
