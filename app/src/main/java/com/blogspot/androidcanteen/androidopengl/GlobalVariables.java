package com.blogspot.androidcanteen.androidopengl;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 * Created by Paolo on 16/06/2018.
 */

public class GlobalVariables
{

    public static final String TAG = "AndroidOpenGL";
    private static Random rand = new Random();

    public static long GetRandom()
    {
        return  Math.abs(rand.nextLong());
    }

    public static void logWithTag(String mesg)
    {
        Log.d(GlobalVariables.TAG,mesg);
    }
    public static final int sizeOf(Object object) throws IOException {

        if (object == null)
            return -1;

        // Special output stream use to write the content
        // of an output stream to an internal byte array.
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();

        // Output stream that can write object
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(byteArrayOutputStream);

        // Write object and close the output stream
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();

        // Get the byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // TODO can the toByteArray() method return a
        // null array ?
        return byteArray == null ? 0 : byteArray.length;


    }
}