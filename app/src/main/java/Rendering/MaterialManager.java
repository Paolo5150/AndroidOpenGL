package Rendering;

import java.util.HashMap;

import Engine.AssetLoader;
import MaterialObjects.Material_BumpyRock;
import MaterialObjects.Material_BumpyWall;
import MaterialObjects.Material_SkyBox;

public class MaterialManager {

    private static HashMap<String,Material> allMaterials;

    public static void Initialize()
    {
        allMaterials = new HashMap<>();

        //Create materials
        Material_BumpyRock bumpy_rock = new Material_BumpyRock();
        Material_BumpyWall bumpy_wall = new Material_BumpyWall();
        Material_SkyBox skyBox = new Material_SkyBox();

        //Add to list
        allMaterials.put(bumpy_rock.getName(),bumpy_rock);
        allMaterials.put(bumpy_wall.getName(),bumpy_wall);
    }

   public static Material getMaterialByName(String name)
   {
       if(allMaterials.containsKey(name))
           return allMaterials.get(name);
       else
           return null;
   }
}
