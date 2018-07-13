package Physics;

import android.opengl.Matrix;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Application.Camera;
import Application.IInteractionListener;
import Engine.Input;
import Math.*;

public class RayCast {

    private static Ray ray = new Ray();

    public static Ray rayCastFromCamera(Camera cam, float distance)
    {
        Vector3f dir = new Vector3f();

        float[] invProj = new float[16];
        float[] invView = new float[16];

        Matrix.invertM(invProj,0,cam.getProj(),0);
        Matrix.invertM(invView,0,cam.getViewM(),0);

        float[] normalPos = {Input.getInstance().getTouchPositionNormalized().x,Input.getInstance().getTouchPositionNormalized().y,-1,1};

        float[] pos = new float[4];

        Matrix.multiplyMV(pos,0,invProj,0,normalPos,0);
        pos[2] = -1;
        pos[3] = 0;

        Matrix.multiplyMV(pos,0,invView,0,pos,0);

        Vector3f rayDir = new Vector3f(pos[0], pos[1], pos[2]);
        rayDir.normalizeThis();
      // GlobalVariables.logWithTag("Raycasting: X" + rayDir.x + " Y " + rayDir.y + " Z " + rayDir.z);

       ray.origin = cam.position;
       ray.direction = rayDir;
       ray.distance = distance;
        return ray;
    }


}
