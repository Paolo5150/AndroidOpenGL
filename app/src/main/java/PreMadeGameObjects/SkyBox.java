package PreMadeGameObjects;

import Components.SkyBoxRenderer;
import Engine.GameObject;
import Rendering.CubeMap;

public class SkyBox extends GameObject {
    public SkyBox(CubeMap cubeMap) {
        super("SkyBox");



        addComponent(new SkyBoxRenderer(this));
        getComponent("SkyBoxRenderer", SkyBoxRenderer.class).getMaterial().addCubeMap(cubeMap);

        CubeMap additionalCubeMap = new CubeMap("SunSet","sky2");
        getComponent("SkyBoxRenderer", SkyBoxRenderer.class).getMaterial().addCubeMap(additionalCubeMap);

    }
}
