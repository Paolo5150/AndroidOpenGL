package Components;



import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.GameObject;
import Engine.PreMadeMeshes;
import Physics.PhysicsGlobals;
import Physics.Ray;
import Math.*;
import PreMadeGameObjects.Plane;
import PreMadeGameObjects.Terrain;
import Rendering.Texture;
import Rendering.Triangle;

public class SphereCollider extends Collider {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public SphereCollider(GameObject o)
    {

        super("SphereCollider",o);



        //Assign sphere mesh
        renderer.setMesh(PreMadeMeshes.getMeshByName("Sphere"));

    }



    @Override
    public boolean isCollidingWithRay(Ray r)
    {

        Vector3f rTos = Vector3f.subtract(transform.getWorldPosition(), r.origin);
        Vector3f proj = Vector3f.project(rTos,r.direction);
        Vector3f pointOnRay = Vector3f.add(r.origin,proj);
        Vector3f SphereToPoint = Vector3f.subtract(pointOnRay,transform.getWorldPosition());

        if(SphereToPoint.length() < transform.getWorldScale().x)
       return true;
        else
        return false;
    }

    @Override
    public boolean isCollidingWithPlane(Plane p)
    {
    if(PhysicsGlobals.distanceFromPlane(transform.getWorldPosition(),p) < transform.getWorldScale().x)
        return true;
    else
        return false;

    }

    public void collideWithTerrain(Terrain terrain)
    {
        Vector3f sphereLocal = Vector3f.toDifferentCoordinate(transform.getWorldPosition(),terrain.transform.getInverseTransformation(),1.0f);
        Vector3f sphereScaleLocal = Vector3f.toDifferentCoordinate(transform.getWorldScale(),terrain.transform.getInverseTransformation(),0.0f);


        for(Triangle t : terrain.quadTree.FindQuadrant(sphereLocal.x,-sphereLocal.z))
        {


            Vector3f shortest = PhysicsGlobals.shortestToPlane(sphereLocal,t.v1.getPosition(),t.v1.getNormal());


            if(shortest.length() <= sphereScaleLocal.x)
            {

                Vector3f collisionPoint = Vector3f.add(sphereLocal,shortest);
                PhysicsBody pb = getGameObject().getComponent("PhysicsBody", PhysicsBody.class);

                if(t.isPointInTriangle(collisionPoint) ) {

                    Vector3f normalToWorld = Vector3f.toDifferentCoordinate(t.v1.getNormal(),terrain.transform.getTransformation(),0.0f);
                    normalToWorld.normalizeThis();

                    Vector3f reflectionDir = Vector3f.reflect(pb.velocity,normalToWorld);

                    Vector3f pointCollisionWorld = Vector3f.toDifferentCoordinate(collisionPoint,terrain.transform.getTransformation(),1.0f);
                    Vector3f negVelocity = Vector3f.negate(pb.velocity);
                    negVelocity.normalizeThis();

                    getGameObject().transform.position = Vector3f.add(pointCollisionWorld,Vector3f.multiply(normalToWorld,transform.getActualScale().x ));


                    float velocityImpact = pb.velocity.length();
                   // GlobalVariables.logWithTag("Normal used " + reflectionDir.getInfo());

                    pb.velocity = Vector3f.multiply(reflectionDir,velocityImpact );


                    break;
                }


            }
        }

    }




}
