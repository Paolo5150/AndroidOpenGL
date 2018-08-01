package PreMadeGameObjects;

import android.os.Build;
import android.support.annotation.RequiresApi;

import Application.AppCanvas;
import Application.Application;
import Components.MeshRenderer;
import Engine.EngineTime;
import Engine.GameObject;
import Engine.PreMadeMeshes;
import MaterialObjects.Material_BumpyRock;
import MaterialObjects.Material_Water;
import Math.*;
import Rendering.Camera;
import Rendering.FrameBuffer;
import Rendering.Layer;
import Rendering.Lighting;
import Rendering.Material;
import Rendering.RenderingEngine;

public class Water extends GameObject {

    private Plane plane;
    int sizeX;
    int sizeZ;
    FrameBuffer refractionBuffer;
    FrameBuffer reflectionBuffer;
    Material_Water material;
    MeshRenderer renderer;
    Vector2f uvOffset;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Water(int vertsX, int vertsY) {
        super("Water");

        sizeX = vertsX;
        sizeZ = vertsY;
        setLayer(Layer.WATER);

        refractionBuffer = new FrameBuffer();
        reflectionBuffer = new FrameBuffer();
        updateReflectionRefractionTextures();
        uvOffset = new Vector2f();

        plane = new Plane(transform.position,new Vector3f(0,1,0));
        plane.transform.position = transform.position;

        material = new Material_Water();
        material.addTexture(refractionBuffer.depthThing[0], "texture_depth");
       material.addTexture(refractionBuffer.colorAttachment,"texture_refraction");
        material.addTexture(reflectionBuffer.colorAttachment,"texture_reflection");


        renderer = new MeshRenderer(PreMadeMeshes.getGridMesh(vertsX,vertsY),material,this);
        renderer.colorOverride = new Vector3f(0.9f,1.0f,1.0f);
        addComponent(renderer);








        AppCanvas.topLeft.assignTexture(reflectionBuffer.colorAttachment);
    }

    @Override
    public void update()
    {
        super.update();
        uvOffset.x += EngineTime.getDeltaTimeSeconds()/500.0f ;
        uvOffset.y += EngineTime.getDeltaTimeSeconds()/500.0f ;
        material.updateVec2("UVOffset",uvOffset);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void lateUpdate()
    {
       updateReflectionRefractionTextures();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void updateReflectionRefractionTextures() {
        //Refraction
        refractionBuffer.bind();
        Camera.getCameraByName("Camera_GUI").setActive(false);
        Camera.activeCamera.removeLayer(Layer.WATER);
        Camera.activeCamera.removeLayer(Layer.SKYBOX);
        RenderingEngine.getInstance().renderSceneUsingBatch(Application.getInstance().getCurrentScene());
        refractionBuffer.unbind();

        Camera.activeCamera.addLayer(Layer.SKYBOX);



        //Reflection

        reflectionBuffer.bind();
        Lighting.clipPlanePosition.y = transform.position.y;
        Lighting.activateClipPlane = 1;
        Lighting.planeSide = -1;
        float camTOWater = transform.position.y - Camera.activeCamera.getGameObject().transform.position.y ;
        Camera.activeCamera.setPosition(Vector3f.add(Camera.activeCamera.getPosition(),new Vector3f(0,camTOWater * 2,0)));
        Camera.activeCamera.setPitch(Camera.activeCamera.getPitch() * -1);

        RenderingEngine.getInstance().renderSceneUsingBatch(Application.getInstance().getCurrentScene());
        reflectionBuffer.unbind();

        Camera.activeCamera.setPosition(Vector3f.add(Camera.activeCamera.getPosition(),new Vector3f(0,-camTOWater * 2,0)));
        Camera.activeCamera.setPitch(Camera.activeCamera.getPitch() * -1);
        Camera.activeCamera.addLayer(Layer.WATER);
        Camera.getCameraByName("Camera_GUI").setActive(true);
        Lighting.activateClipPlane = 0;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Plane getPlane() {
        return plane;
    }
}
