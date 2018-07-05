package Rendering;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Paolo on 25/06/2018.
 */

public class ShaderManager {



    public static HashMap<String, Shader> allShaders;

    public  static void Initialize(Context ac)
    {

        allShaders = new HashMap<String,Shader>();
    }

    public static void CreateShader(Context c, String name, int v, int f)
    {

        allShaders.put(name, new Shader(c,v,f));

    }

    public static Shader GetShaderByName(String name)
    {
        Shader s = null;

        if(allShaders.containsKey(name))
            s =  allShaders.get(name);
        else
            Log.d(GlobalVariables.TAG, "Shader " + name + " not available");

        return s;
    }
}
