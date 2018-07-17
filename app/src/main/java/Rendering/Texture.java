package Rendering;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import Engine.AssetLoader;
import Engine.Entity;

/**
 * Created by Paolo on 25/06/2018.
 */

public class Texture extends Entity {


    private int[] textureID;

    public int getTextureID() {
        return textureID[0];
    }



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    private int width;
    private int height;
    private String uniformName;



    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public Texture(String textureFileName, String uniformName)
    {
        super(textureFileName,"Texture");
        Bitmap bitmap = AssetLoader.getInstance().getTextureBmp(textureFileName);


        this.uniformName = uniformName;
        generateTexture(bitmap);

    }



    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void generateTexture(Bitmap bitmap)
    {
        textureID = new int[1];

        GLES20.glGenTextures(1,textureID,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureID[0]);


        GLES30.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT,1);


        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR); //Linear or nearest
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);


        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        bitmap.recycle();

    }
    public void bind()
    {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);

    }


    public String getUniformName() {
        return uniformName;
    }
}
