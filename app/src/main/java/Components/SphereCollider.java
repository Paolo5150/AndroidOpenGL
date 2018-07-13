package Components;



import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.GameObject;
import Engine.PreMadeMeshes;
import Physics.Ray;
import Math.*;

public class SphereCollider extends Collider {

    public SphereCollider(GameObject o)
    {

        super("SphereCollider",o);



        //Assign sphere mesh
        meshRenderer.setMesh(PreMadeMeshes.getMeshByName("Sphere"));

    }

    @Override
    public void Update()
    {
        //GlobalVariables.logWithTag("Scale X " + transform.getActualScale().x);
    }

    @Override
    public boolean isCollidingWithRay(Ray r)
    {
        Vector3f rTos = Vector3f.subtract(transform.getWorldLocation(), r.origin);
        Vector3f proj = Vector3f.project(rTos,r.direction);
        Vector3f pointOnRay = Vector3f.add(r.origin,proj);
        Vector3f SphereToPoint = Vector3f.subtract(pointOnRay,transform.getWorldLocation());

        if(SphereToPoint.length() < transform.getActualScale().x)
       return true;
        else
        return false;
    }




}