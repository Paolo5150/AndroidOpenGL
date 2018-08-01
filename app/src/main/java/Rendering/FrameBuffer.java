package Rendering;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.Entity;

public class FrameBuffer extends Entity {

    public Texture colorAttachment;

    private int[] frameBufferID;

    //Test
    public int[] depthThing;


    public FrameBuffer() {
        super("FrameBuffer", "FrameBuffer");
        Initialize();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void Initialize() {


        frameBufferID = new int[1];
        depthThing = new int[1];

        GLES30.glGenFramebuffers(1,frameBufferID,0);
        GLES30.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBufferID[0]);

        colorAttachment = new Texture("texture_diffuse0");


        GLES30.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER,GLES30.GL_COLOR_ATTACHMENT0,GLES20.GL_TEXTURE_2D,colorAttachment.getTextureID(),0);

        //Depth
        //Generate texture
        GLES30.glGenTextures(1, depthThing,0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, depthThing[0]);


        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30. glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);

        //GL_FLoat doesn t work, use gl unsigned short
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_DEPTH_COMPONENT32F, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT, 0, GLES30.GL_DEPTH_COMPONENT,GLES30.GL_FLOAT, null);

        GLES30.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER,GLES30.GL_DEPTH_ATTACHMENT,GLES20.GL_TEXTURE_2D,depthThing[0],0);

        // now that we actually created the framebuffer and added all attachments we want to check if it is actually complete now
        if (GLES20.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER) != GLES30.GL_FRAMEBUFFER_COMPLETE)
            GlobalVariables.logWithTag("ERROR FRAME BUFFER NOT COMPLETE!");

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        unbind();

    }

    public void bind()
    {

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        GLES30.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBufferID[0]);
        GLES30.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );


        GLES30.glClearColor(0.5f,0.5f,0.5f,1);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

    }

    public void unbind()
    {
        GLES30.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
       // GLES30.glDisable(GLES20.GL_DEPTH_TEST);
    }


}
