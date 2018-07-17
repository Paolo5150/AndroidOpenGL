package Components;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.Component;
import Engine.EngineTime;
import Engine.GameObject;
import Math.*;
import Physics.PhysicsGlobals;
import Physics.PhysicsMaterial;

public class PhysicsBody extends Component {

    public Vector3f velocity;
    private float gravityMultiplier;
    private float bounceFactor;
    private PhysicsMaterial physicsMaterial;
    private boolean isKinematic;

    GameObject obj;

    public PhysicsBody( GameObject objectBelongingTo) {

        super("PhysicsBody", objectBelongingTo);

        //Initialize parameters
        velocity = new Vector3f();
        gravityMultiplier = 1.0f;
        physicsMaterial = new PhysicsMaterial();
        isKinematic = false;

        //Check for collider
        Collider collider = getGameObject().getComponentByType("Collider",Collider.class);
        obj = getGameObject();

        if(collider == null)
        {
            GlobalVariables.logWithTag("No collider attached to " + getGameObject().getName()+", new sphere collider created");
            getGameObject().addComponent(new SphereCollider(getGameObject()));
            collider = getGameObject().getComponentByType("Collider",Collider.class);;
        }
    }

    @Override
    public void Update()
    {
        if(isKinematic)
            return;

        //Apply gravity
        Vector3f gravityFactor = Vector3f.multiply(PhysicsGlobals.gravity,EngineTime.getDeltaTimeSeconds() * EngineTime.getDeltaTimeSeconds() * gravityMultiplier);
        gravityFactor = Vector3f.multiply(gravityFactor,0.5f);


        Vector3f velocityDelta = Vector3f.multiply(velocity,EngineTime.getDeltaTimeSeconds());

        Vector3f nexTPos = Vector3f.add(obj.transform.position,velocityDelta);

        Vector3f toAddNextVel = Vector3f.multiply(PhysicsGlobals.gravity ,EngineTime.getDeltaTimeSeconds() * gravityMultiplier);

        obj.transform.position = Vector3f.add(nexTPos,gravityFactor);
        velocity = Vector3f.add(velocity,toAddNextVel);
    }

    public float getGravityMultiplier() {
        return gravityMultiplier;
    }

    public void setGravityMultiplier(float gravityMultiplier) {
        this.gravityMultiplier = gravityMultiplier;
    }

    public float getBounceFactor() {
        return bounceFactor;
    }

    public void setBounceFactor(float bounceFactor) {
        this.bounceFactor = bounceFactor;
    }

    @Override
    protected void setComponentType()
    {
        componentType = "PhysicsBody";
    }

    public PhysicsMaterial getPhysicsMaterial() {
        return physicsMaterial;
    }

    public void setPhysicsMaterial(PhysicsMaterial physicsMaterial) {
        this.physicsMaterial = physicsMaterial;
    }

    public boolean isKinematic() {
        return isKinematic;
    }

    public void setKinematic(boolean kinematic) {
        isKinematic = kinematic;
    }
}
