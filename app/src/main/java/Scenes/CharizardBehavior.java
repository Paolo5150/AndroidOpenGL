package Scenes;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Components.Behavior;
import Components.Collider;
import Components.MeshRenderer;
import Components.SphereCollider;
import Components.SpriteRenderer;
import Engine.AssetLoader;
import Engine.EngineTime;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import Rendering.MaterialManager;
import ShaderObjects.LightNoNormalShader;
import ShaderObjects.LightShader;
import Rendering.Material;
import Math.*;

public class CharizardBehavior extends Behavior {

    GameObject obj;
    //SphereCollider collider;
    Material mat;
    Vector2f offset;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public CharizardBehavior(GameObject o)
    {
        super("CharizardBehavior",o);

        offset = new Vector2f();
        obj = getGameObject();


        obj.addComponent(new SphereCollider(obj));

       // GlobalVariables.logWithTag("In charbehav, collider type " + collider.getType());
      //  collider.setRenderable(true);
       // collider.transform.scale = new Vector3f(0.1f,0.1f,0.1f);

    }

    @Override
    public void Update()
    {
        obj.transform.rotation.y += 0.5f ;



    }
}
