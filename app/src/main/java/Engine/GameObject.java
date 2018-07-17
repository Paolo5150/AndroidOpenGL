package Engine;

import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import Application.Application;
import Rendering.Layer;


public class GameObject extends Entity{



    public Transform transform;
    HashMap<String, Component> components;
    public ArrayList<GameObject> children;
    private int layer;



    boolean active;

    public GameObject(String name)
    {
        super(name,"GameObject");


        transform = new Transform(this);

        active = true;
        layer = Layer.getLayer(Layer.DEFAULT);

        components = new HashMap<>();
        children = new ArrayList<>();

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
        if(!isActive())
            return;

        for(GameObject go : children)
            go.start();

        for(String key : components.keySet())
                 components.get(key).Start();


    }
    public void update()
    {
        if(!isActive())
            return;

        for(String key : components.keySet())
            components.get(key).Update();

        for(GameObject go : children)
            go.update();


    }


    public void lateUpdate()
    {
        if(!isActive())
            return;

        for(String key : components.keySet())
            components.get(key).lateUpdate();

        for(GameObject go : children)
            go.lateUpdate();

    }
    public void render()
    {
        if(!isActive())
            return;

        for(String key : components.keySet())
             components.get(key).Render();

        for(GameObject go : children)
            go.render();
    }

    public void addComponent(Component comp)
    {
        if(comp.allowMultiple)
        {
            int counter = 0;
            for(String s : components.keySet())
            {
                if(components.get(s).getName().contains(comp.getName()))
                    counter++;
            }
            components.put(comp.getName() + counter,comp);

        }
        else
            components.put(comp.getName() ,comp);

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

        Application.getCurrentScene().rearrangeSceneHierarchy();

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

    public <T extends Component> T getComponent(String name,Class<T> tClass)
    {
        T res = null;
        if(components.containsKey(name))
        {
            res = (T)components.get(name);
        }

        return res;
    }

    public <T extends Component> T[] getComponentsByType(String type,Class<T> tClass)
    {

        ArrayList<T> list = new ArrayList<>();

        for(String s: components.keySet())
        {
            if(components.get(s).getComponentType().contains(type)) {
                list.add((T)components.get(s));

            }

        }

        T[] res = (T[]) Array.newInstance(tClass, list.size());
        list.toArray(res);
        return res;



    }
    public <T extends Component> T getComponentByType(String type,Class<T> tClass)
    {
        T res = null;
        for(String s: components.keySet())
        {
            if(components.get(s).getComponentType().equals(type)) {
                res = (T) components.get(s);
                break;
            }

        }

        // GlobalVariables.logWithTag("Gameobject, returned component by type " + res.getName());
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

    public int getLayer() {
        return layer;
    }

    public void setLayer(String name) {
        this.layer = Layer.getLayer(name);
    }

    public void printComponents()
    {
        for(String s : components.keySet())
            GlobalVariables.logWithTag(s );
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
