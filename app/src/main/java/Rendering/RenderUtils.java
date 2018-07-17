package Rendering;
import android.opengl.Matrix;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Math.*;
public class RenderUtils {

    public static Vector3f unprojectDirection(Camera cam,float x, float y)
    {

        Vector2f normalized = toScreenNormalizedCoords(x,y);

        Camera current = cam;

        float[] invProj = new float[16];
        float[] invView = new float[16];

        Matrix.invertM(invProj,0,current.getProj(),0);
        Matrix.invertM(invView,0,current.getViewM(),0);

        float[] normalPos = {normalized.x,normalized.y,-1,1};

        float[] posVec4 = new float[4];

        Matrix.multiplyMV(posVec4,0,invProj,0,normalPos,0);
        posVec4[2] = -1;
        posVec4[3] = 0;

        Matrix.multiplyMV(posVec4,0,invView,0,posVec4,0);

        Vector3f direction = new Vector3f(posVec4[0], posVec4[1], posVec4[2]);
        direction.normalizeThis();

        //GlobalVariables.logWithTag("Unprojected dir X: " + direction.x + " Y: " + direction.y + " Z: " + direction.z);

        return direction;
    }



    public static Vector3f unprojectDirection(Vector2f v)
    {

        return unprojectDirection(Camera.activeCamera,v.x,v.y);
    }

    public static Vector2f toScreenNormalizedCoords(float x, float y)
    {
        Vector2f res = new Vector2f();
        res.x = ((x*2.0f) / Screen.SCREEN_WIDTH)-1;
        res.y = -(((y*2.0f) / Screen.SCREEN_HEIGHT)-1);
        return res;
    }
}
