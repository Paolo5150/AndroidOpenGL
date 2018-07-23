package Scenes;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Application.AppCanvas;
import Components.CameraPerspective;
import Components.MeshRenderer;
import Engine.Input;
import Engine.PreMadeMeshes;
import GUI.GUICanvas;
import GUI.GUITexture;
import Listeners.ITouchListener;
import PreMadeGameObjects.Terrain;
import Engine.GameObject;
import Engine.Scene;
import Rendering.CubeMap;
import Rendering.MaterialManager;
import Rendering.Screen;
import PreMadeGameObjects.SkyBox;
import Rendering.Texture;

public class TestScene extends Scene implements ITouchListener {



    GameObject sphere;
    GameObject quad;
    Terrain terrain;


    GameObject cam;


    GUITexture guiObj;
    GameObject can;



    public TestScene() {
        super("Test_Scene");


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void start()
    {

        super.start();
        cam = new GameObject("Camera_Main");
        cam.addComponent(new CameraPerspective("Camera_Main",cam,60,(float) Screen.SCREEN_WIDTH / Screen.SCREEN_HEIGHT,0.1f,1000.0f));

        cam.transform.position.z = 8;








        terrain = new Terrain();

        sphere = new GameObject("Sphere");
        sphere.addComponent(new CharizardBehavior(sphere));
        sphere.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Sphere"), MaterialManager.getMaterialByName("Material_BumpyWall"), sphere));

        can = new AppCanvas();


        quad = new GameObject("Quad");
        quad.addComponent(new CharizardBehavior(quad));
        quad.addComponent(new MeshRenderer(PreMadeMeshes.getMeshByName("Sphere"),MaterialManager.getMaterialByName("Material_BumpyWall"), quad));





      // addChild(quad);
       addChild(cam);
      addChild(can);

      addChild(sphere);
       addChild(terrain);

        //printHierarchy();

        Input.getInstance().addListener(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void update()
    {


        super.update();


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void render()
    {

        super.render();








    }

    @Override
    public void assignOptionalSkyBox() {
        CubeMap c = new CubeMap("ClearSky","sky");
        skyBox = new SkyBox(c);
    }


    @Override
    public void OnTouch(int x, int y, int id) {



    }

    @Override
    public void OnDrag(int x, int y, int id) {

    }

    @Override
    public void OnRelease(int x, int y, int id) {

    }
}
