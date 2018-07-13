package Engine;

import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;

import Application.Application;


public class GameObject {



    public Transform transform;
    HashMap<String, Component> components;
    public ArrayList<GameObject> children;
    String name;
    String ID;


    boolean active;

    public GameObject(String name)
    {
        this.name = name;
        transform = new Transform(this);

        active = true;
        GenerateID();

        components = new HashMap<>();
        children = new ArrayList<>();

    }

    private void GenerateID()
    {
        ID = "Game_Object_" + name + System.currentTimeMillis() + Utils.GetRandomFloat();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isGameObjectYourChild(String id)
    {
    return isGameObjectYourChild(id,this);
    }

    private boolean isGameObjectYourChild(String id, GameObject obj)
    {


        for(GameObject g : obj.children)
        {
            if(g.getID().equals(id))
                return true;
            else {
                if (g.children.size() != 0)
                    return isGameObjectYourChild(id, g);
                else
                    return false;
            }
        }
        return false;

    }

    public void start()
    {
        for(String key : components.keySet())
            if(components.get(key).isActive())
                 components.get(key).Start();

        for(GameObject go : children)
            go.start();
    }
    public void update()
    {
        for(String key : components.keySet())
            if(components.get(key).isActive())
            components.get(key).Update();

        for(GameObject go : children)
            go.update();
    }
    public void render()
    {
        for(String key : components.keySet())
            if(components.get(key).isActive())
             components.get(key).Render();

        for(GameObject go : children)
            go.render();
    }

    public void addComponent(Component comp)
    {
        if(!components.containsKey(comp.getName()))
        {

            components.put(comp.getName(),comp);

        }
    }

    public void addChild(GameObject go)
    {
        boolean alreadythere = false;

        GameObject current = this;
        if(getID().equals(go.getID()))
            alreadythere = true;


        while(current.transform.getParent() != null && !alreadythere) {

            current = current.transform.getParent().gameObject;
            if(current.getID().equals(go.getID()))
                alreadythere = true;
        }

        if(!alreadythere)
             alreadythere = current.isGameObjectYourChild(go.getID());

        if(!alreadythere) {
            go.transform.setParent(this.transform);
            children.add(go);
        }

        Application.scene.rearrangeSceneHierarchy();

    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;

        for(GameObject go : children)
            go.setActive(active);
    }


    public <T extends Component> T getComponent(String name)
    {
        T res = null;
        if(components.containsKey(name))
        {
            res = (T)components.get(name);
        }

        return res;
    }

    public void printHierarchy()
    {
        StringBuilder output = new StringBuilder("");

        output.append("\n" + getName());
        output.append("\n");

      int currentLevel = 0;
        printHierarchy(this, output,currentLevel);
        Log.d(GlobalVariables.TAG,"\n\n" + output.toString());

    }



    private void printHierarchy(GameObject g, StringBuilder output, int level)
    {
        output.append("\t");
        for(GameObject c : g.children)
        {
            for(int i=0; i< level; i++)
                output.append("\t");

            output.append(" - " + c.getName() + "\n");

            printHierarchy(c,output,level+1);
        }

    }
}
