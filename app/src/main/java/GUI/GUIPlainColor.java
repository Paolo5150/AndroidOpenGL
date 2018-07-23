package GUI;

import android.os.Build;
import android.support.annotation.RequiresApi;
import Math.*;
import ShaderObjects.GUIBasicShader;

public class GUIPlainColor extends GUIObject {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public GUIPlainColor(Vector3f color,float alpha,GUICanvas canvas) {
        super("GUIPlainColor", canvas);
        renderer.getMaterial().setShader(new GUIBasicShader());
        renderer.colorOverride = color;
        renderer.alphaOverride = alpha;
    }
}
