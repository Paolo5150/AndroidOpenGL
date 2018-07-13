package Engine;

import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;

import Application.IInteractionListener;

public abstract class Scene extends GameObject {


   // HashMap<String, GameObject> objectsInScene;
    private String name;

    public Scene(String name)
    {
        super(name);
      //  objectsInScene = new HashMap<>();
        this.name = name;

    }


    public void start()
    {

        for(GameObject g : children)
            if(g.isActive())
                g.start();
    }


    public void update()
    {
        /*for(String s : objectsInScene.keySet())
        {
            GameObject o = objectsInScene.get(s);
            if(o.isActive())
                o.update();
        }*/

        for(GameObject g : children)
            if(g.isActive())
                g.update();

    }


    public void render()
    {
       /* for(String s : objectsInScene.keySet())
        {
            GameObject o = objectsInScene.get(s);
            if(o.isActive())
                o.render();
        }*/

        for(GameObject g : children)
            if(g.isActive())
                g.render();
    }

    public void rearrangeSceneHierarchy()
    {
      /*  HashMap<String, GameObject> copy = new HashMap<>();
        for(String s : objectsInScene.keySet())
        {
            copy.put(s,objectsInScene.get(s));
        }

        objectsInScene.clear();

        for(String s : copy.keySet())
        {
           addGameObjectToScene(copy.get(s));
        }*/

    }
  /*  public void addGameObjectToScene(GameObject o)
    {
        boolean objectInScene = false;
        boolean isNewOneChildInScene = false;
        String toRemove = "";
       for(String s : objectsInScene.keySet())
       {
           //Don't add object if already there
           if(s.equals(o.getID()))
               objectInScene = true;
           else //Check if the new object is a child of one of the objects in hierarchy
           {
               boolean isNewOneYourChild = objectsInScene.get(s).isGameObjectYourChild(o.getID());

               if(isNewOneYourChild)
               {
                   Log.d(GlobalVariables.TAG,"Object " + o.getName() + " is child of " + objectsInScene.get(s).getName() + ", will not be added");
                   return;
               }
               else
               {
                   isNewOneChildInScene = o.isGameObjectYourChild(objectsInScene.get(s).getID());

                   if(isNewOneChildInScene) {
                       toRemove = objectsInScene.get(s).getID();
                       break;
                   }
               }
           }
       }

       if(isNewOneChildInScene)
       {
          // Log.d(GlobalVariables.TAG,"Object " + objectsInScene.get(toRemove).getName() + " is child of " + o.getName() + ", will be deleted");
           objectsInScene.remove(objectsInScene.get(toRemove).getID());
       }

       if(!objectInScene)
       {
           objectsInScene.put(o.getID(),o);
          // Log.d(GlobalVariables.TAG,"I added to scene: " + o.name);
       }


    }

*/
}
