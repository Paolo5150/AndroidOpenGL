package Rendering;

import android.opengl.GLES20;
import android.opengl.GLES30;

import Engine.Entity;

public class FrameBuffer extends Entity {

    Texture colorAttachment;
    Texture depthAttachment;
    private int[] frameBufferID;

    public FrameBuffer() {
        super("FrameBuffer", "FrameBuffer");
        Initialize();


    }

    private void Initialize() {





    }


}
