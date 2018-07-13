package Rendering;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import Engine.Utils;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Paolo on 25/06/2018.
 */

public class Texture {

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

    public Texture(int resourceID)
    {
        Bitmap bmp = BitmapFactory.decodeResource(Utils.resources,resourceID);
        generateTexture(bmp);
    }
    public Texture(Bitmap bitmap)
    {
    generateTexture(bitmap);

    }

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
        //GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        bitmap.recycle();

    }
    public void bind()
    {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);

    }


    
}
