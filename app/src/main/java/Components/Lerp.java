package Components;

import com.blogspot.androidcanteen.androidopengl.GlobalVariables;

import Engine.Component;
import Engine.EngineTime;
import Engine.GameObject;
import Math.*;

public class Lerp extends Component {

    private Vector3f target;

    private float speed;

    public Lerp(GameObject objectBelongingTo) {
        super("Lerp", objectBelongingTo);


        target = null;
        speed = 3;
    }

    public void setTarget(Vector3f t)
    {
        setActive(true);
        target = t;
    }

    @Override
    public void Update()
    {

        if(target == null)
        {
            setActive(false);
            return;
        }

        float dist = Vector3f.subtract(getGameObject().transform.position,target).length();
       // GlobalVariables.logWithTag("Distance " + dist);

        if(dist <= 2)
        {
            getGameObject().transform.position.x = target.x;
            getGameObject().transform.position.y = target.y;
            getGameObject().transform.position.z = target.z;
            setActive(false);
            //GlobalVariables.logWithTag("ARRIVED!");

        }
        else
        {
           // GlobalVariables.logWithTag("GETTING THERE!");
            getGameObject().transform.position = Vector3f.lerp(getGameObject().transform.position,target,speed * EngineTime.getDeltaTimeSeconds());
        }
    }

    @Override
    protected void setComponentType() {
        componentType = "Lerp";
    }
}
