package Application;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.R;

import Components.MeshRenderer;
import Engine.Input;
import Engine.PreMadeMeshes;
import Engine.Scene;
import Engine.Utils;
import Math.Vector2f;
import Math.Vector3f;
import Rendering.LightShader;
import Rendering.Lighting;
import Rendering.Material;
import Rendering.Screen;
import Rendering.Shader;
import Rendering.Texture;
import Engine.GameObject;
import Scenes.TestScene;

/**
 * Created by Paolo on 25/06/2018.
 */

public class Application implements IInteractionListener{

    private Activity activity;
    public static boolean running;



    public static Scene scene = new TestScene();


    private static Application instance;

    public static Application getInstance()
    {
        if(instance == null)
            instance = new Application();

        running = true;

        return  instance;
    }
    private Application()
    {
        Input.getInstance().addInteractionListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Start()
    {


        scene.start();


    }

    public void Update()
    {

        scene.update();





    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void Render()
    {

    scene.render();

    }


    @Override
    public void OnInteract() {


        float centerX =  Screen.SCREEN_WIDTH/2.0f;
        float centerY = Screen.SCREEN_HEIGHT/2.0f;

        Vector2f toCenter = new Vector2f();
        toCenter.x = centerX - Input.getInstance().getTouchPosition().x;
        toCenter.y = centerY - Input.getInstance().getTouchPosition().y;

        toCenter.normalizeThis();

        Lighting.directionalLight.rotation.x = toCenter.x;
        Lighting.directionalLight.rotation.y = -toCenter.y;


    }
}
