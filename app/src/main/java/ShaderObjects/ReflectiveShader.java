package ShaderObjects;

public class ReflectiveShader extends LightNormalFogShader {
    public ReflectiveShader() {
        super("ReflectiveShader", "reflective_vertex.txt", "reflective_fragment.txt");
    }
}
