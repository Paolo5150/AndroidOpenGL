package Listeners;

public interface ITouchListener {

    public void OnTouch(int x, int y, int id);
    public void OnDrag(int x, int y, int id);
    public void OnRelease(int x, int y, int id);
}
