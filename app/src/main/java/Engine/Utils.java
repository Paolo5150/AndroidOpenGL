package Engine;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import Rendering.Mesh;
import Math.Vertex;
import Math.*;
public class Utils {


    public static Activity activity;
    public static Resources resources;
    private static Random rand;




    public static void Initialize(Resources res, Activity act)
    {
        activity = act;
        resources = res;


        rand = new Random();

    }

    public static float GetRandomFloat()
    {
        return rand.nextFloat();
    }

    public static int GetRandomIntRange(int max)
    {
        return rand.nextInt(max);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Mesh loadOBJ(String modelName)  {


        InputStream is = AssetLoader.getInstance().getModelInputStream(modelName);

        BufferedReader bf = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder fileTxt = new StringBuilder();


        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Vector2f> textures = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<Integer> textIndices = new ArrayList<>();


        try
        {
            while ((line = bf.readLine()) != null) {

                String[] tokens = line.split(" ");

                if(tokens[0].equals("#"))
                    continue;

                //Vertices
                if(tokens[0].equals("v"))
                {
                    vertices.add(new Vertex(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2]),Float.parseFloat(tokens[3])));

                }
                //Textures
                if(tokens[0].equals("vt"))
                {
                    textures.add(new Vector2f(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));

                }
                else if (tokens[0].equals("f"))
                {
                    //Check if faces have texture and normal information
                    String[] tok = tokens[1].split("/");

                        //Triangle
                        if (tokens.length == 4) {
                            //If no normal/texture info
                            if(tok.length==0) {
                                indices.add(Integer.parseInt(tokens[1]) - 1);
                                indices.add(Integer.parseInt(tokens[2]) - 1);
                                indices.add(Integer.parseInt(tokens[3]) - 1);
                            }
                            else
                            {
                                int i1 = Integer.parseInt((tokens[1].split("/")[0])) - 1;
                                int i2 = Integer.parseInt((tokens[2].split("/")[0])) - 1;
                                int i3 = Integer.parseInt((tokens[3].split("/")[0])) - 1;


                                //Texture info
                                int it1 = Integer.parseInt((tokens[1].split("/")[1])) - 1;
                                int it2 = Integer.parseInt((tokens[2].split("/")[1])) - 1;
                                int it3 = Integer.parseInt((tokens[3].split("/")[1])) - 1;


                                indices.add(i1);
                                indices.add(i2);
                                indices.add(i3);


                                vertices.get(i1).setTextureCoords(textures.get(it1));
                                vertices.get(i2).setTextureCoords(textures.get(it2));
                                vertices.get(i3).setTextureCoords(textures.get(it3));
                            }

                        }
                        //Quad
                        else if (tokens.length == 5) {
                            //If no normal/texture info
                            if(tok.length==0)
                            {
                            indices.add(Integer.parseInt(tokens[1]) - 1);
                            indices.add(Integer.parseInt(tokens[2]) - 1);
                            indices.add(Integer.parseInt(tokens[3]) - 1);

                            indices.add(Integer.parseInt(tokens[3]) - 1);
                            indices.add(Integer.parseInt(tokens[4]) - 1);
                            indices.add(Integer.parseInt(tokens[1]) - 1);
                             }
                            //If no normal/texture info
                             else
                            {
                                int i1 = Integer.parseInt((tokens[1].split("/")[0])) - 1;
                                int i2 = Integer.parseInt((tokens[2].split("/")[0])) - 1;
                                int i3 = Integer.parseInt((tokens[3].split("/")[0])) - 1;
                                int i4 = Integer.parseInt((tokens[4].split("/")[0])) - 1;

                                //Texture info
                                int it1 = Integer.parseInt((tokens[1].split("/")[1])) - 1;
                                int it2 = Integer.parseInt((tokens[2].split("/")[1])) - 1;
                                int it3 = Integer.parseInt((tokens[3].split("/")[1])) - 1;
                                int it4 = Integer.parseInt((tokens[4].split("/")[1])) - 1;


                                indices.add(i1);
                                indices.add(i2);
                                indices.add(i3);

                                indices.add(i3);
                                indices.add(i4);
                                indices.add(i1);

                               vertices.get(i1).setTextureCoords(textures.get(it1));
                                vertices.get(i2).setTextureCoords(textures.get(it2));
                                vertices.get(i3).setTextureCoords(textures.get(it3));
                                vertices.get(i4).setTextureCoords(textures.get(it4));



                            }
                        }
                    }

                }






            }
        catch(IOException e)
            {}



        Vertex[] vs = new Vertex[vertices.size()];
        int[] ins = new int[indices.size()];

        vertices.toArray(vs);

        for(int i=0; i< indices.size(); i++)
            ins[i] = indices.get(i);


        return new Mesh(vs,ins,true);
    }
}
