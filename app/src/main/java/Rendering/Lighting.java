package Rendering;
import Math.*;
public class Lighting {

    public static void Initialize()
    {
        directionalLight = new DirectionalLight(new Vector3f(1,1,1),
                                                new Vector3f(0,0,0),
                                                new Vector3f(0,-1,-1));

        ambientLight = new Vector3f(0.2f,0.2f,0.2f);
       clipPlanePosition = new Vector3f();
        activateClipPlane = 0;
        planeSide = -1;

        fogColor = new Vector3f(0.250f, 0.251f,0.2502f);
        fogDensity = 0.0018f;
        fogGradient = 5.0f;


    }

    public static DirectionalLight directionalLight;
    public static Vector3f ambientLight;
    public static Vector3f clipPlanePosition;
    public static int activateClipPlane;
    public static int planeSide;
    public static Vector3f fogColor;
    public static float fogDensity;
    public static float fogGradient;


}
