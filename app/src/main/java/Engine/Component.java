package Engine;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.StringTokenizer;

public abstract class Component extends Entity {


    public GameObject gameObject; //Added by gameobject on attach
    protected boolean active;
    protected boolean allowMultiple;
    protected String componentType;


    public Component(String componentName, GameObject objectBelongingTo)
    {
        super(componentName,"Component");


        setComponentType();

        active = true;
        gameObject = objectBelongingTo;
        allowMultiple = false;
    }

    protected abstract void setComponentType();

    public String getComponentType() {
        return componentType;
    }

    public void Start()
    {}
    public void Update()
    {}


    public void lateUpdate()
    {}
    public void Render()
    {}




    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
       // GlobalVariables.logWithTag("Set GameObject called for " + name);
        this.gameObject = gameObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public boolean isAllowMultiple() {
        return allowMultiple;
    }
}
