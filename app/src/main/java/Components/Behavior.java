package Components;

import Engine.Component;
import Engine.GameObject;

public abstract class Behavior extends Component{
    public Behavior(String name, GameObject o) {
        super(name,o);
    }

    @Override
    public void Update()
    {


    }
}
