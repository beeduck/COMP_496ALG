package com.comp496.p3;

import java.util.ArrayList;

public class Main {

//    public static void main(String[] args) {
//        Graph g = new Graph( "graph1.txt");
////        Graph g = new Graph(3);
////        g.addEdge(3, 2, 4);
////        g.addEdge(1, 2, 3);
////        g.addEdge(1, 3, 5);
////        g.addEdge(2, 1, 4);
//        g.printGraph();
//
//        Graph dfsGraph = g.dfsTraversal(1);
//        if(dfsGraph != null)
//            dfsGraph.printGraph();
//
//        g.bfsTraversal(1);
//
//        int x = 0;
//        int y = 5;
//        ArrayList<Integer> path = g.getShortestEdgePath(x, y);
//        if(path != null)
//            System.out.println("Shortest path " + x + " to " + y + '\t' + path.toString());
//
//        g.KruskalMST();
//
////        int  x,y,z;
////        //Set values of x, y and z to vertices in your graph
////        Graph tree  = g.dfsTraversal(x);
////        tree.printGraph();
////
////        g.bfsTraversal(y);
////
////        System.out.println( g.getShortestEdgePath(y,z) );
////
////        g.dijkstraShortestPaths(z);
////
////        Graph mstTree = g.KruskalMST();
////        mstTree.printGraph();
//    }

    public static void main (String[] args)
    {


//        //------------TestCase A ------------------------
//        System.out.println ("Project 3 TestCase A:" );
//        Graph g1 = new Graph("inputA.txt");
//        System.out.println("Print Graph");
//        g1.printGraph();
//        System.out.println("\nNumber of Vertices = "  +
//                g1.get_nVertices() +
//                "\tNumber of Edges = " + g1.get_nEdges() +
//                "\tTotal Edge Weight = " + g1.get_TotalWeightOfEdges() );
//
//
//        //-----------------------------------------------
//
//        int v = 0;
//        System.out.println("\nDFS Traversal starting at " + v);
//        Graph tree = g1.dfsTraversal(v);
//        if( tree != null)
//        {
//            System.out.println("\nDFS Spanning Tree ");
//            tree.printGraph();
//        }
//
//
//
//        //-----------------------------------------------
//        System.out.println("\nDijkstra SP");
//        int start = 3;
//        g1.dijkstraShortestPaths(start);
//
//
//        //-----------------------------------------------------
//        System.out.println("\nKruskal's MST ");
//        Graph treeKruskal = g1.KruskalMST();
//        if(treeKruskal != null)
//        {
//            System.out.println("\nMST Tree");
//            treeKruskal.printGraph();
//        }



        //------------TestCase B ------------------------
        System.out.println("\n\nProject 3 TestCase B:");
        Graph g2 = new Graph("inputB.txt");
        System.out.println("Print Graph");
        g2.printGraph();
        //-----------------------------------------------

        int v2 = 3;
        System.out.println("\nDFS Traversal starting at " + v2);
        Graph tree2 = g2.dfsTraversal(v2);
        if( tree2 != null)
        {
            System.out.println("\nDFS Spanning Tree ");
            tree2.printGraph();
        }



        //-----------------------------------------------
        System.out.println("\nDijkstra SP");
        int start2 = 2;
        g2.dijkstraShortestPaths(start2);


    }
}
