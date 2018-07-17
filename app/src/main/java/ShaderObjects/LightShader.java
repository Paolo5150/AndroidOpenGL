package ShaderObjects;

import Rendering.Camera;
import Rendering.Lighting;
import Rendering.Shader;

public class LightShader extends Shader {

    public LightShader() {

        super("LightShader","light_vertex.txt","light_fragment.txt");
    }

    @Override
    public void updateMatrices(float[] modelMatrix, Camera renderingCamera) {
        super.updateMatrices(modelMatrix, renderingCamera);


        setVec3("cameraPosition",Camera.activeCamera.getPosition());
        setVec3("ambientLight", Lighting.ambientLight.x,Lighting.ambientLight.y,Lighting.ambientLight.z);
        setVec3("dirLight.color",Lighting.directionalLight.color);
        setVec3("dirLight.specularColor",Lighting.directionalLight.color);
        setVec3("dirLight.direction",Lighting.directionalLight.rotation);

    }
}
