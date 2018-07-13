package Engine;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.StringTokenizer;

public abstract class Component {

    public String name;
    public GameObject gameObject; //Added by gameobject on attach
    protected boolean active;
    public Component(String componentName, GameObject objectBelongingTo)
    {
        name = componentName;
        active = true;
        gameObject = objectBelongingTo;
    }

    public void Start()
    {}
    public void Update()
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
}
