package Math;

import java.util.ArrayList;

import Rendering.Triangle;

public class PlaneQuadtree {

    final int MAX_LEVELS = 3;
    QuadNode<Triangle> root;

    public PlaneQuadtree(float w, float h)
    {
        root = new QuadNode<Triangle>(0, (w-1)/2, (h-1)/2, w, h);

        CreateTree(root);
    }



    public ArrayList<Triangle> FindQuadrant(float x, float y)
    {
        return FindQuadrant(null,root, x, y);
    }


    void SplitNode(QuadNode<Triangle>  node)
    {
        if (node != null)
        {



            node.bl = new QuadNode<Triangle>(node.level + 1,  node.posX - (node.width)/4.0f, node.posY -  (node.height) /4.0f ,  node.width/2.0f, node.height/2.0f );

            node.br = new QuadNode<Triangle>(node.level + 1, node.posX + (node.width ) / 4, node.posY - (node.height ) /4, node.width / 2, node.height / 2);



            node.tl = new QuadNode<Triangle>(node.level + 1, node.posX - (node.width ) / 4, node.posY + (node.height ) /4, node.width / 2, node.height / 2);




            node.tr = new QuadNode<Triangle>(node.level + 1, node.posX + (node.width ) / 4, node.posY + (node.height) / 4, node.width / 2, node.height / 2);



            node.isSplit = true;

        }

    }

    public void CreateTree(QuadNode<Triangle> node)
    {
        if (node.level < MAX_LEVELS)
        {
            SplitNode(node);
            //DebugLog::Log("I split the node at level ", node->level);

            CreateTree(node.tr);
            CreateTree(node.tl);
            CreateTree(node.br);
            CreateTree(node.bl);
        }

    }

    ArrayList<Triangle> FindQuadrant(QuadNode<Triangle> parentNode, QuadNode<Triangle> node, float x, float y) {
        if (node.isSplit) {
            if (x <= node.posX && y >= node.posY)
                return FindQuadrant(node, node.tl, x, y);
            if (x >= node.posX && y >= node.posY)
                return FindQuadrant(node, node.tr, x, y);
            if (x >= node.posX && y <= node.posY)
                return FindQuadrant(node, node.br, x, y);
            if (x <= node.posX && y <= node.posY)
                return FindQuadrant(node, node.bl, x, y);
        } else {
            //DebugLog::Log("Quadrant ID: " , node->id);
            return node.items;


        }
        return null;

    }

    void Clear()
    {
        Clear(root);
    }

    void Clear(QuadNode<Triangle> node)
    {
        if (node != null)
        {
            if (node.isSplit)
            {
                Clear(node.tr);
                Clear(node.tl);
                Clear(node.br);
                Clear(node.bl);
            }

            node.items.clear();

        }
    }

    public void AddTriangle( Triangle f)
    {
        Add(f, root);
    }

    void Add(Triangle f, QuadNode<Triangle> node)
    {
        if (node.isSplit)
        {

            //	DebugLog::Log("Face is X: ", f.v1Pos.x, " Y: ", f.v1Pos.z);
            if (f.getCentroid().x <= node.posX && -f.getCentroid().z >= node.posY)
            {
                //	DebugLog::Log("Added TOP LEFT");
                Add(f, node.tl);
            }
            if (f.getCentroid().x <= node.posX && -f.getCentroid().z <= node.posY)
            {
                //	DebugLog::Log("Added BOT LEFT");
                Add(f, node.bl);
            }

            if (f.getCentroid().x >= node.posX && -f.getCentroid().z >= node.posY)
            {
                //	DebugLog::Log("Added TOP RIGHT");
                Add(f, node.tr);
            }
            if (f.getCentroid().x >= node.posX && -f.getCentroid().z <= node.posY)
            {
                //	DebugLog::Log("Added BOT RIGHT");
                Add(f, node.br);
            }




        }
        else
            node.items.add(f);
    }
}
