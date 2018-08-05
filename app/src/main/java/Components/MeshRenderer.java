package Components;

import Rendering.Camera;
import Engine.GameObject;
import Rendering.Material;
import Rendering.Mesh;
import Rendering.RenderingEngine;

public class MeshRenderer extends Renderer {



    public MeshRenderer(Mesh mesh, Material material, GameObject o) {
        super("MeshRenderer",mesh,material,o);

    renderingCamera = Camera.activeCamera;


    }



    @Override
    public void Update()
    {
        //Submit to rendering engine
        RenderingEngine.getInstance().addToBatch(this);
    }




}
