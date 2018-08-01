package GUI;

import android.os.Build;
import android.support.annotation.RequiresApi;

import Engine.GameObject;
import Rendering.Texture;

public class GUITexture extends GUIObject {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public GUITexture(String name, Texture texture, GUICanvas c)
    {
        super(name,c);


        renderer.getMaterial().addTexture(texture);

        addComponent(new GUIQuadCollider(this));

    }

    public void assignTexture(Texture texture)
    {
        renderer.getMaterial().addTexture(texture);
    }
}
