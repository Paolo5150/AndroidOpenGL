package Application;

import android.util.Log;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import Math.Vector3f;
/**
 * Created by Paolo on 24/06/2018.
 */

public class GameObject {

    public Vector3f position;
    public Vector3f scale;
    public Vector3f rotation;

    private String uniqueID;
    private String name;


    public GameObject()
    {
    generateID();
    }

    public GameObject(String name)
    {
    this.name = name;
    generateID();
    }

    private void generateID()
    {

        Date t = Calendar.getInstance().getTime();
        uniqueID = name +( t.getTime() + GlobalVariables.GetRandom());


    }
}
