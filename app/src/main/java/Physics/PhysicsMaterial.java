package Physics;

import Engine.Entity;

public class PhysicsMaterial extends Entity {

    private float bounceFactor;

    public PhysicsMaterial(String name) {
        super(name, "PhysicsMaterial");

        bounceFactor = 1;

    }

    public PhysicsMaterial() {
        super("DefaultPhysicsMaterial", "PhysicsMaterial");

        bounceFactor = 1;

    }

    public float getBounceFactor() {
        return bounceFactor;
    }

    public void setBounceFactor(float bounceFactor) {
        this.bounceFactor = bounceFactor;
    }
}
