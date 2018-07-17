package Scenes;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.support.annotation.RequiresApi;

import Components.Collider;
import Components.MeshRenderer;
import Components.PhysicsBody;
import Components.Renderer;
import Engine.PreMadeMeshes;
import Physics.Ray;
import Physics.RayCast;
import PreMadeGameObjects.Terrain;
import Rendering.Camera;
import Application.IInteractionListener;
import Engine.GameObject;
import Engine.Input;
import Engine.Scene;
import Rendering.CubeMap;
import Rendering.Lighting;
import Rendering.Material;
import Rendering.MaterialManager;
import Rendering.Screen;
import Math.*;
import PreMadeGameObjects.SkyBox;

public class TestScene extends Scene implements IInteractionListener {



    GameObject sphere;
    GameObject quad;
    Terrain terrain;


    GameObject cam;
    GameObject cam2;

    Material mat;

    public TestScene() {
        super("Test_Scene");

        Input.getInstance().addInteractionListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void start()
    {

        cam = new GameObject("Camera_Main");
        cam.addComponent(new Camera("Camera_Main",cam));
        cam.getComponentByType("Camera",Camera.class).setPerspective(60,(float) Screen.SCREEN_WIDTH / Screen.SCREEN_HEIGHT,0.1f,1000.0f);
        cam.transform.position.z = 8;


        cam2 = new GameObject("Camera_Second");
        cam2.addComponent(new Camera("Camera_Second",cam2));
        cam2.getComponentByType("Camera",Camera.class).setPerspective(60,(float) Screen.SCREEN_WIDTH / Screen.SCREEN_HEIGHT,0.1f,1000.0f);
        cam2.transform.position.z = 4;
      




        terrain = new Terrain();

        sphere = new GameObject("Sphere");
        //sphere.addComponent(new CharizardBehavior(sphere));
        sphere.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Sphere"), MaterialManager.getMaterialByName("Material_BumpyRock"), sphere));

        sphere.transform.scale = new Vector3f(0.3f,0.3f,0.3f);




        quad = new GameObject("Quad");
       // quad.addComponent(new CharizardBehavior(quad));
        quad.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Quad"),MaterialManager.getMaterialByName("Material_BumpyWall"), quad));
        quad.getComponentByType("Renderer", Renderer.class).setRenderingCamera(cam2.getComponentByType("Camera",Camera.class));




       addChild(quad);
       addChild(cam);
       addChild(cam2);
        addChild(sphere);
       addChild(terrain);

        //printHierarchy();


       super.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void update()
    {


        super.update();


        sphere.transform.position.z +=0.05f;
       // quad.transform.position.y -=0.01f;


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void render()
    {

        //super.render();








    }

    @Override
    public void assignOptionalSkyBox() {
        CubeMap c = new CubeMap("ClearSky","sky");
        skyBox = new SkyBox(c);
    }

    @Override
    public void OnInteract() {



        if(Input.getInstance().isTouch())
        {
        Ray r = RayCast.rayCastFromActiveCamera(100);
        float distToCam = Vector3f.subtract(sphere.transform.position,cam.transform.position).length();

            if(    sphere.getComponentByType("Collider",Collider.class).isCollidingWithRay(r)  )
            {
              sphere.getComponentByType("PhysicsBody",PhysicsBody.class).setGravityMultiplier(0);
                sphere.getComponentByType("PhysicsBody",PhysicsBody.class).velocity.y = 0;
                sphere.transform.position.x = r.pointOnRay(distToCam).x;
                sphere.transform.position.y = r.pointOnRay(distToCam).y;


            }




        Lighting.directionalLight.rotation.x = -r.pointOnRay(5).x;
            Lighting.directionalLight.rotation.y = -1;
            Lighting.directionalLight.rotation.z = -1;
        }
        else
        {
           sphere.getComponentByType("PhysicsBody",PhysicsBody.class).setGravityMultiplier(1);
        }


    }
}
