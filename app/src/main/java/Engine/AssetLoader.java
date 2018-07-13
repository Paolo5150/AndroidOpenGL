package Engine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.io.IOException;
import java.io.InputStream;

public class AssetLoader {

    private final String textureFolder = "Textures/";
    private final String shaderFolder = "Shaders/";
    private final String modelsFolder = "Models/";

    private static AssetLoader instance;

    private AssetManager manager;


    public AssetLoader() {

    }

    public static AssetLoader getInstance()
    {
        if(instance == null)
            instance = new AssetLoader();

        instance.manager = Utils.activity.getAssets();

        return instance;
    }

    public InputStream getShaderInputStream(String vertexFragment)
    {
        InputStream is = null;

        try {
            is = manager.open(shaderFolder + vertexFragment);
        } catch (IOException e) {
            GlobalVariables.logWithTag("Could not find shader" + vertexFragment);
        }

        return is;
    }

    public InputStream getModelInputStream(String modelFileName)
    {
        InputStream is = null;

        try {
            is = manager.open(modelsFolder + modelFileName);
        } catch (IOException e) {
            GlobalVariables.logWithTag("Could not find shader" + modelFileName);
        }

        return is;
    }

    public Bitmap getTextureBmp(String textureName)
    {
        String fullPath = getTextureFullPath(textureName);
        Bitmap bmp = null;

        try {
            bmp = BitmapFactory.decodeStream(manager.open(fullPath));
        } catch (IOException e) {
            GlobalVariables.logWithTag("Could not find " + textureName);
        }

        return bmp;


    }
    private String getTextureFullPath(String textureName)
    {
        return textureFolder + textureName;
    }



}
