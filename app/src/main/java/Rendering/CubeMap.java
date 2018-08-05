package Rendering;

import android.graphics.Bitmap;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import Engine.AssetLoader;
import Engine.Entity;
import Engine.Utils;

public class CubeMap extends Entity {

   String[] textureNames = {"right","left","top","bottom","back","front"};

    private int[] cubeMapID;
    private Bitmap[] textures;
    private String uniformName;

    public CubeMap(String folderName, String uniformName) {
        super("CubeMap_" + folderName, "CubeMap");

        textures = new Bitmap[6];
        this.uniformName = uniformName;


        for(int i=0; i< textureNames.length; i++)
            {
            textures[i] = AssetLoader.getInstance().getTextureCubeMapBmp(folderName,textureNames[i] + ".jpg");

            if(textures[i] == null)
                textures[i] = AssetLoader.getInstance().getTextureCubeMapBmp(folderName,textureNames[i] + ".png");
            }

        Initialize();
    }

    public CubeMap(int size, String uniformName)
    {
        super("CubeMap_Empty", "CubeMap");
        this.uniformName = uniformName;
        InitializeEmpty(size);
    }

    private void InitializeEmpty(int size)
    {
        cubeMapID = new int[1];

        GLES30.glGenTextures(1,cubeMapID,0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,cubeMapID[0]);

        for(int i=0; i< 6; i++)
        {
            GLES30.glTexImage2D(GLES30.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i,0,GLES30.GL_RGB,size,size,0,GLES30.GL_RGB,GLES20.GL_UNSIGNED_BYTE,null);
        }

        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);

    }
    private void Initialize() {

        cubeMapID = new int[1];


        GLES10.glGenTextures(1,cubeMapID,0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,cubeMapID[0]);
        GLES30.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT,1);


        for(int i=0; i< textures.length;i++)
        {


             GLUtils.texImage2D(GLES30.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, textures[i], 0);

        }

        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);

        for(int i=0; i< textures.length;i++)
        {
           // textures[i].recycle();
        }

    }


    public String getUniformName() {
        return uniformName;
    }

    public int getCubeMapID()
    {
        return cubeMapID[0];
    }
    public void bind()
    {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, cubeMapID[0]);

    }
}
