package Physics;

import android.opengl.Matrix;

import Rendering.Camera;
import Engine.Input;
import Math.*;
import Rendering.RenderUtils;

public class RayCast {

    private static Ray ray = new Ray();

    public static Ray rayCastFromCamera(Camera cam, float distance)
    {
        Vector3f rayDir = RenderUtils.unprojectDirection(Input.getInstance().getTouchPosition());


       ray.origin = cam.getPosition();
       ray.direction = rayDir;
       ray.distance = distance;
        return ray;
    }

    public static Ray rayCastFromActiveCamera(float distance)
    {
        Vector3f rayDir = RenderUtils.unprojectDirection(Input.getInstance().getTouchPosition());

        ray.origin = Camera.activeCamera.getPosition();
        ray.direction = rayDir;
        ray.distance = distance;
        return ray;
    }

}
