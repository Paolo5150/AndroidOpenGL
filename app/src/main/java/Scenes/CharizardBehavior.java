package Scenes;

import com.blogspot.androidcanteen.androidopengl.R;

import Components.Behavior;
import Components.MeshRenderer;
import Components.SphereCollider;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import Physics.RayCast;
import Rendering.LightShader;
import Rendering.Material;
import Rendering.Texture;
import Math.*;

public class CharizardBehavior extends Behavior {

    GameObject obj;

    public CharizardBehavior(GameObject o)
    {
        super("CharizardBehavior",o);


        obj = getGameObject();

        obj.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Charizard"),new Material(new LightShader(),new Vector3f(1,1,1),
                new Texture(R.drawable.wall),
                new Vector3f(1,1,1),128),obj));

        obj.addComponent(new SphereCollider(obj));
        //((SphereCollider)obj.getComponent("SphereCollider")).setRenderable(true);
        ((SphereCollider)obj.getComponent("SphereCollider")).transform.scale = new Vector3f(0.5f,0.5f,0.5f);
    }

    @Override
    public void Update()
    {


    }
}
