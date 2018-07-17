package ShaderObjects;

import Rendering.Camera;
import Rendering.Lighting;
import Rendering.Shader;



public class LightNoNormalShader extends Shader {

    public LightNoNormalShader() {
        super("LightNoNormalShader","light_no_normal_vertex.txt","light_no_normal_fragment.txt");
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