package Engine;

import PreMadeGameObjects.SkyBox;

public abstract class Scene extends GameObject {

    public SkyBox skyBox;

    private String name;

    public Scene(String name)
    {
        super(name);

        this.name = name;

    }

    public void loadScene()
    {
        assignOptionalSkyBox();
    }


    public void start()
    {


        for(GameObject g : children)
            if(g.isActive())
                g.start();
    }


    public void update()
    {


        for(GameObject g : children)
            if(g.isActive())
                g.update();

    }

    public void stop()
    {

    }


    public void render()
    {

      //  GlobalVariables.logWithTag("Rendering scene, num of objecs: " + children.size());

        for(GameObject g : children)
        {
            if(g.isActive())
                g.render();
        }

    }

    public abstract void assignOptionalSkyBox();

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
