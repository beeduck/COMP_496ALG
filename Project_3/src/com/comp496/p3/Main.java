package com.comp496.p3;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Graph g = new Graph( "graph1.txt");
//        Graph g = new Graph(3);
//        g.addEdge(3, 2, 4);
//        g.addEdge(1, 2, 3);
//        g.addEdge(1, 3, 5);
//        g.addEdge(2, 1, 4);
        g.printGraph();

        Graph dfsGraph = g.dfsTraversal(1);
        if(dfsGraph != null)
            dfsGraph.printGraph();

        g.bfsTraversal(1);

        int x = 0;
        int y = 4;
        ArrayList<Integer> path = g.getShortestEdgePath(x, y);
        if(path != null)
            System.out.println("Shortest path " + x + " to " + y + '\t' + path.toString());

        g.KruskalMST();

//        int  x,y,z;
//        //Set values of x, y and z to vertices in your graph
//        Graph tree  = g.dfsTraversal(x);
//        tree.printGraph();
//
//        g.bfsTraversal(y);
//
//        System.out.println( g.getShortestEdgePath(y,z) );
//
//        g.dijkstraShortestPaths(z);
//
//        Graph mstTree = g.KruskalMST();
//        mstTree.printGraph();
    }
}
