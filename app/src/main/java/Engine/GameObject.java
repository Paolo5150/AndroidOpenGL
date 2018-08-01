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
    protected HashMap<String, ArrayList<Component>> components;
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
        {
            for(Component c : components.get(key))
                 c.Start();
        }



    }
    public void update()
    {
        if(!isActive())
            return;

        for(String key : components.keySet())
        {
            for(Component c : components.get(key)) {
                if (c.isActive())
                    c.Update();
            }
        }

        for(GameObject go : children)
            go.update();


    }


    public void lateUpdate()
    {
        if(!isActive())
            return;

        for(String key : components.keySet())
        {
            for(Component c : components.get(key))
                if(c.isActive())
                c.lateUpdate();
        }

        for(GameObject go : children)
            go.lateUpdate();

    }
    public void render()
    {
        if(!isActive())
            return;

        for(String key : components.keySet())
        {
            for(Component c : components.get(key))
                if(c.isActive())
                c.Render();
        }

        for(GameObject go : children)
            go.render();
    }

    public void addComponent(Component comp)
    {


        if(components.containsKey(comp.getName()))
        {
            if(comp.allowMultiple) {
                ArrayList<Component> list = components.get(comp.getName());
                list.add(comp);
            }
        }
        else
        {
            ArrayList<Component> list = new ArrayList<>();
            list.add(comp);
            components.put(comp.getName(),list);
        }



    }

    public void onAddChild(GameObject child)
    {

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

        onAddChild(go);
        Application.getInstance().getCurrentScene().rearrangeSceneHierarchy();

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
            ArrayList<Component> list = components.get(name);

            res = (T)list.get(0);

        }

        return res;


    }

    public <T extends Component> T getComponent(String name, Class<T> tClass)
    {
        T res = null;

        if(components.containsKey(name))
        {
            ArrayList<Component> list = components.get(name);

            res = tClass.cast(list.get(0));

        }

        return res;


    }



    public <T extends Component> T[] getComponentsByType(String type,Class<T> tClass)
    {

        ArrayList<Component> list = new ArrayList<>();
        ArrayList<T> listToReturn = new ArrayList<>();

        for(String s: components.keySet())
        {
            list = components.get(s);

            if(list.get(0).getComponentType().equals(type))
            {
                listToReturn = (ArrayList<T>) components.get(s);
                break;
            }
        }

        T[] res = (T[]) Array.newInstance(tClass, listToReturn.size());
        list.toArray(res);
        return res;



    }
    public <T extends Component> T getComponentByType(String type,Class<T> tClass)
    {
        T res = null;
        for(String s: components.keySet())
        {
            ArrayList<Component> list = components.get(s);

            if(list.get(0).getComponentType().equals(type))
            {
                res = (T)list.get(0);
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
