package PreMadeGameObjects;

import Components.SkyBoxRenderer;
import Engine.GameObject;
import Rendering.CubeMap;
import Rendering.Layer;

public class SkyBox extends GameObject {
    public SkyBox(CubeMap cubeMap) {
        super("SkyBox");


        setLayer(Layer.SKYBOX);
        addComponent(new SkyBoxRenderer(this));
        getComponent("SkyBoxRenderer", SkyBoxRenderer.class).getMaterial().addCubeMap(cubeMap);

        CubeMap additionalCubeMap = new CubeMap("SunSet","sky2");
     //   getComponent("SkyBoxRenderer", SkyBoxRenderer.class).getMaterial().addCubeMap(additionalCubeMap);

    }
}
