package Rendering;
import Math.*;
import Engine.Entity;
import PreMadeGameObjects.Plane;

public class Triangle  {

    public Vertex v1;
    public Vertex v2;
    public Vertex v3;
    private Vector3f centroid;



    public Triangle(Vertex v1, Vertex v2, Vertex v3) {

        this.v1  = v1;
        this.v2 = v2;
        this.v3 = v3;
        centroid = new Vector3f();
        centroid.x = (v1.getPosition().x + v2.getPosition().x + v3.getPosition().x)/3.0f;
        centroid.y = (v1.getPosition().y + v2.getPosition().y + v3.getPosition().y)/3.0f;
        centroid.z = (v1.getPosition().z + v2.getPosition().z + v3.getPosition().z)/3.0f;




    }


    public boolean isPointInTriangle(Vector3f v)
    {
        Vector3f ab = Vector3f.subtract(v2.getPosition(), v1.getPosition());
        Vector3f ac = Vector3f.subtract(v3.getPosition(), v1.getPosition());
        float areaABC = ((ab.cross(ac)).length())/2.0f;

        Vector3f pb = Vector3f.subtract(v2.getPosition(), v);
        Vector3f pc = Vector3f.subtract(v3.getPosition(), v);
        Vector3f pa = Vector3f.subtract(v1.getPosition(), v);

        float alpha = (pb.cross(pc).length()) / (2.0f * areaABC);

        float beta = (pc.cross(pa).length()) / (2.0f * areaABC);

        float gamma = 1.0f - alpha - beta;

        return 0 <= alpha && alpha <=1 && 0 <= beta && beta <=1 && 0 <= gamma && gamma <= 1;

    }



    public Vector3f getCentroid() {
        return centroid;
    }
}
