package Engine;

import java.util.ArrayList;

import Listeners.ITouchListener;
import Math.*;
import Rendering.Screen;

public class Input implements ITouchListener {

    private static Input instance;
    private static ArrayList<ITouchListener> listeners;
    public static boolean GUITouched;

    private Vector2f touchPosition;
    private Vector2f touchPositionNormalized;
    private Vector2f previousTeouchPosition;
    private Vector2f deltaTouch;
    private boolean isTouch;

    public static Input getInstance()
    {
        if(instance == null)

            instance = new Input();

        return instance;
    }

    private Input()
    {
        touchPosition = new Vector2f();
        deltaTouch = new Vector2f();
        previousTeouchPosition = new Vector2f();
        touchPositionNormalized = new Vector2f();
        listeners = new ArrayList<>();
        isTouch = false;
    }





    private void updateTouch(int x, int y)
    {
        touchPosition.x = x;
        touchPosition.y = y;
        touchPositionNormalized.x = ((x*2.0f) / Screen.SCREEN_WIDTH)-1;
        touchPositionNormalized.y = -(((y*2.0f) / Screen.SCREEN_HEIGHT)-1);
        deltaTouch.x = touchPosition.x - previousTeouchPosition.x;
        deltaTouch.y = touchPosition.y - previousTeouchPosition.y;
        previousTeouchPosition.x = touchPosition.x;
        previousTeouchPosition.y = touchPosition.y;
    }

    public void addListener(ITouchListener l)
    {
        listeners.add(l);
    }
    @Override
    public void OnTouch(int x, int y, int id) {

        updateTouch(x,y);
        isTouch = true;
        for(ITouchListener l : listeners)
            l.OnTouch(x,y, id);


    }

    @Override
    public void OnDrag(int x, int y, int id) {

        updateTouch(x,y);
        isTouch = true;
        for(ITouchListener l : listeners)
            l.OnDrag(x,y, id);


    }

    @Override
    public void OnRelease(int x, int y, int id) {



        isTouch = false;
        for(ITouchListener l : listeners)
            l.OnRelease(x,y, id);

    }

    public Vector2f getTouchPosition() {
        return touchPosition;
    }

    public Vector2f getPreviousTeouchPosition() {
        return previousTeouchPosition;
    }

    public Vector2f getDeltaTouch() {
        return deltaTouch;
    }

    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }


    public Vector2f getTouchPositionNormalized() {
        return touchPositionNormalized;
    }
}
