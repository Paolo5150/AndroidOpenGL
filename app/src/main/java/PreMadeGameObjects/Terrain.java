package PreMadeGameObjects;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Components.MeshRenderer;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import MaterialObjects.Material_BumpyRock;
import Math.Vector3f;
import Rendering.Material;
import Rendering.Mesh;
import Rendering.Texture;
import Rendering.Triangle;
import ShaderObjects.ShaderManager;
import Math.*;

public class Terrain extends GameObject {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

    private Plane plane;
    int sizeX;
    int sizeZ;
    Material mat;
    public Mesh mesh;
    public PlaneQuadtree quadTree;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Terrain(int vertsX, int vertsY) {
        super("Terrain");

        sizeX = vertsX;
        sizeZ = vertsY;




        plane = new Plane(transform.position,new Vector3f(0,1,0));
        plane.transform.position = transform.position;

        mat = new Material("Terrain",ShaderManager.getShaderByName("TerrainShader"));
        Texture sand = new Texture("sand.jpg","texture_sand");
        Texture rock = new Texture("rock_diffuse.jpg","texture_rock");
        Texture grass = new Texture("grass.jpg","texture_grass");
        Texture blend = new Texture("blend.png","texture_blend");
        Texture normalMap = new Texture("rock_normal.png","texture_normal0");
        mat.addTexture(sand);
        mat.addTexture(blend);
        mat.addTexture(grass);
        mat.addTexture(rock);
        mat.addTexture(normalMap);

        mat.addFloat("shininess",5);
        mat.addVec3("specularColor",1,1,1);


        addComponent(new MeshRenderer(PreMadeMeshes.getGridMesh(vertsX,vertsY),mat,this));
        getComponent("MeshRenderer",MeshRenderer.class).getMaterial().updateFloat("UVScale",10);
        mesh = getComponent("MeshRenderer",MeshRenderer.class).getMesh();


        quadTree = new PlaneQuadtree(vertsX,vertsY);

        for(Triangle t : mesh.getTriangles())
        {
            quadTree.AddTriangle(t);
        }

       transform.position.y = -10.0f;
     //   transform.position.x = -40;
      //  transform.position.z = 10;



    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Plane getPlane() {
        return plane;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void loadHeightMap(String fileName, float maxHeight)
    {
        Texture hm = new Texture(fileName,"texture_diffuse0");




        float heighestPoint = -1;
        float lowest = Float.MAX_VALUE;
        for(int y=0; y < hm.getBitmap().getHeight(); y++)
        {
            for(int x=0; x < hm.getBitmap().getWidth(); x++)
            {
                int p=hm.getBitmap().getPixel(x,y);

                int R = (p >> 16) & 0xff;
                int G = (p >> 8) & 0xff;
                int B = p & 0xff;
                float h = (R + G + B) / 3.0f;

                if(h > heighestPoint)
                    heighestPoint = h;
                if(h < lowest)
                    lowest = h;


            }
        }


        for(Vertex v : mesh.getVertices())
        {
            int pixelX = (int) ((v.getPosition().x * hm.getBitmap().getWidth()) / sizeX);
            int pixelY = (int) ((-v.getPosition().z * hm.getBitmap().getHeight()) / sizeZ);

           // GlobalVariables.logWithTag("Pixel Y " + pixelY);

            int p=hm.getBitmap().getPixel(pixelX ,pixelY );

            int R = (p >> 16) & 0xff;
            int G = (p >> 8) & 0xff;
            int B = p & 0xff;
            float h = (R + G + B) / 3.0f;
            h /= heighestPoint;
            h*= maxHeight;

            v.setPosition(new Vector3f(v.getPosition().x,h,v.getPosition().z));


        }


        mesh.UpdateVertices(mesh.getVertices(),mesh.getIndices());








    }
}
