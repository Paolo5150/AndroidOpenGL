package ShaderObjects;

import Rendering.Camera;
import Rendering.Lighting;
import Rendering.Shader;

public abstract class LightNormalFogShader extends Shader {

    public LightNormalFogShader(String shaderName, String vertex, String fragment) {
        super(shaderName, vertex, fragment);
    }


    @Override
    public void updateMatrices(float[] modelMatrix, Camera renderingCamera) {
        super.updateMatrices(modelMatrix, renderingCamera);



        setVec3("cameraPosition",Camera.activeCamera.getPosition());
        setVec3("ambientLight", Lighting.ambientLight.x,Lighting.ambientLight.y,Lighting.ambientLight.z);
        setVec3("dirLight.color",Lighting.directionalLight.color);
        setVec3("dirLight.specularColor",Lighting.directionalLight.color);
        setVec3("dirLight.direction",Lighting.directionalLight.rotation);
        setInt("activateClipPlane",Lighting.activateClipPlane);
        setInt("planeSide",Lighting.planeSide);
        setFloat("planeHeight",Lighting.clipPlanePosition.y);
        setVec3("fogColor",Lighting.fogColor);
        setFloat("fogGradient",Lighting.fogGradient);
        setFloat("fogDensity",Lighting.fogDensity);
        setFloat("farPlane",renderingCamera.getFar());
        setFloat("nearPlane",renderingCamera.getNear());

    }
}
