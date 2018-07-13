package Rendering;

import android.content.Context;

import com.blogspot.androidcanteen.androidopengl.R;

import Application.Camera;

public class LightShader extends Shader {

    public LightShader() {
        super("light_vertex.txt","light_fragment.txt");
    }

    @Override
    public void updateUniforms(float[] modelMatrix, Camera renderingCamera) {
        super.updateUniforms(modelMatrix, renderingCamera);


        setVec3("ambientLight",Lighting.ambientLight.x,Lighting.ambientLight.y,Lighting.ambientLight.z);
        setVec3("dirLight.color",Lighting.directionalLight.color);
        setVec3("dirLight.specularColor",Lighting.directionalLight.color);
        setVec3("dirLight.direction",Lighting.directionalLight.rotation);

    }
}
