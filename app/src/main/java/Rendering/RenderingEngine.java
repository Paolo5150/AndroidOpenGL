package Rendering;

public class RenderingEngine {

    private static RenderingEngine instance;

    public static RenderingEngine getInstance()
    {

        if(instance == null)
            instance = new RenderingEngine();

        return  instance;
    }

    public void Render()
    {

    }
}
