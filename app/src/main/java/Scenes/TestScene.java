package Scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.R;

import java.io.IOException;

import Application.Camera;
import Application.IInteractionListener;
import Components.MeshRenderer;
import Components.SphereCollider;
import Engine.AssetLoader;
import Engine.GameObject;
import Engine.Input;
import Engine.PreMadeMeshes;
import Engine.Scene;
import Engine.Utils;
import Physics.Ray;
import Physics.RayCast;
import Rendering.LightShader;
import Rendering.Material;
import Rendering.Screen;
import Rendering.Shader;
import Rendering.Texture;
import Math.*;

public class TestScene extends Scene implements IInteractionListener {

    Shader shader;

    GameObject chariz;
    GameObject quad;
    GameObject sphere;
    GameObject cube;
    Camera cam;

    Material mat;

    public TestScene() {
        super("Test_Scene");

        Input.getInstance().addInteractionListener(this);
    }


    public void start()
    {
        shader = new LightShader();

        cam = new Camera("Main_Camera");
        cam.setPerspective(60,(float) Screen.SCREEN_WIDTH / Screen.SCREEN_HEIGHT,0.1f,128);
        cam.position.z = 5;

        Bitmap    wall= AssetLoader.getInstance().getTextureBmp("wall.jpg");

        chariz = new GameObject("Chariz");
        quad = new GameObject("Quad");
        quad.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Quad"),new Material(new LightShader(),new Vector3f(1,1,1),
                new Texture(wall),
                new Vector3f(1,1,1),128),quad));

        quad.transform.scale = new Vector3f(0.5f,0.5f,0.5f);
        quad.transform.position.x = 1.0f;


        chariz.addChild(quad);


        chariz.addComponent(new CharizardBehavior(chariz));

        addChild(chariz);


        printHierarchy();

       super.start();
    }


    public void update()
    {
        cam.Update();
        chariz.transform.rotation.y += 1.0f ;
        chariz.transform.position.y += 0.001f ;




        super.update();
    }


    public void render()
    {

        super.render();
    }

    @Override
    public void OnInteract() {

        if(Input.getInstance().isTouch())
        {
        Ray r =   RayCast.rayCastFromCamera(Camera.getCameraByName("Main_Camera"),100);

            if(((SphereCollider)chariz.getComponent("SphereCollider")).isCollidingWithRay(r))
            {
                chariz.transform.position = r.pointOnRay(5);

            }


        }
    }
}
