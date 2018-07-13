package Rendering;
import Math.*;
public class Lighting {

    public static void Initialize()
    {
        directionalLight = new DirectionalLight(new Vector3f(1,1,1),
                                                new Vector3f(0,0,0),
                                                new Vector3f(0,-1,-1));

        ambientLight = new Vector3f(0.0f,0.0f,0.0f);

    }

    public static DirectionalLight directionalLight;
    public static Vector3f ambientLight;
}
