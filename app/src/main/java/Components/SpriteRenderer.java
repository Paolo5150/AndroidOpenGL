package Components;

import Engine.GameObject;
import Engine.PreMadeMeshes;
import Rendering.Material;
import Rendering.Mesh;

public class SpriteRenderer extends Renderer {
    public SpriteRenderer( Material mat, GameObject o) {
        super("SpriteRenderer", PreMadeMeshes.getMeshByName("Quad"), mat, o);
    }
}
