package Physics;

import android.opengl.Matrix;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Rendering.Camera;
import Engine.Input;
import Math.*;
import Rendering.RenderUtils;

public class RayCast {

    private static Ray ray = new Ray();

    public static Ray rayCastFromCamera(Camera cam, float distance)
    {
        Vector3f rayDir = RenderUtils.unprojectDirection(Input.getInstance().getTouchPosition());


       ray.origin = new Vector3f(cam.getPosition().x, cam.getPosition().y, cam.getPosition().z);
       ray.direction = new Vector3f(rayDir.x, rayDir.y, rayDir.z);
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
