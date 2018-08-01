package Math;

import java.util.ArrayList;

public class QuadNode<T> {

    ArrayList<T> items;
    float width;
    float height;
    float posX;
    float posY;
    int level;
    boolean isSplit;


    QuadNode<T> tl;
    QuadNode<T> tr;
    QuadNode<T> bl;
    QuadNode<T> br;

    public QuadNode(int l, float x, float y, float w, float h)
    {
       level = l;
       posX = x;
       posY = y;
       width = w;
       height = h;
       isSplit = false;
       items = new ArrayList<>();

       tl = null;
       tr = null;
       br = null;
       bl = null;
    }
}
