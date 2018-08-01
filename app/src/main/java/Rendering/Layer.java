package Rendering;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Layer {

    private static HashMap<String,Integer> layers;

    public static final String DEFAULT = "Default";
    public static final String GUI = "GUI";
    public static final String WATER = "Water";
    public static final String SKYBOX = "Skybox";


    public static void Initialize()
    {
        layers = new HashMap<>();
        addLayer(DEFAULT);
        addLayer(WATER);
        addLayer(GUI);
    }

    public static void addLayer(String layerName)
    {
        layers.put(layerName,layers.size());

        //Automatically add to cameras

        for(String s : Camera.getAllCameras().keySet())
        {
            Camera.getAllCameras().get(s).addLayer(layerName);

        }
    }

    public static int getLayer(String name)
    {
        if(layers.containsKey(name))
        return layers.get(name);
        else return -1;
    }

    public static Set<String> getLayersNames()
    {
        return layers.keySet();
    }
}
