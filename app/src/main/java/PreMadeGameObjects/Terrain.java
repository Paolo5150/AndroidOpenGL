package PreMadeGameObjects;

import android.os.Build;
import android.support.annotation.RequiresApi;

import Components.MeshRenderer;
import Engine.AssetLoader;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import MaterialObjects.Material_BumpyRock;
import MaterialObjects.Material_SkyBox;
import Math.Vector3f;
import Rendering.Material;
import Rendering.Texture;
import ShaderObjects.ShaderManager;

public class Terrain extends GameObject {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

    private Plane plane;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Terrain() {
        super("Terrain");

       Material mat = new Material("Terrain", ShaderManager.getShaderByName("LightNoNormalShader"));

        Texture diffuse = new Texture("grass.jpg","texture_diffuse0");
        plane = new Plane(new Vector3f(0,1,0));
        plane.transform.position = transform.position;

        mat.addTexture(diffuse);
        mat.updateFloat("UVScale",15);


        addComponent(new MeshRenderer(PreMadeMeshes.getGridMesh(50),mat,this));
        transform.position.y = -12;
        transform.position.x = -40;
        transform.position.z = 10;

        transform.scale = new Vector3f(10,1,10);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Plane getPlane() {
        return plane;
    }
}
