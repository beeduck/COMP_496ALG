package com.comp496.p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by duck on 4/24/17.
 */
public class Graph {
    private ArrayList<EdgeNode> adjList;
    private int nVertices;
    private int nEdges;


    public Graph(String inputFileName) {
        //creates Graph from data in file
        System.out.println(new File(inputFileName).getAbsolutePath());
        String line;
        String[] split;
        this.nEdges = 0;
        this.adjList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(inputFileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if( (line = bufferedReader.readLine()) != null)
                this.nVertices = Integer.valueOf(line);

            while( (line = bufferedReader.readLine()) != null) {
                split = line.split(" ");
                this.addEdge(Integer.valueOf(split[0]),
                              Integer.valueOf(split[1]),
                              Integer.valueOf(split[2]));
            }

            bufferedReader.close();

        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public Graph(int n) {
        nVertices = n;
        adjList = new ArrayList();
    }   //Creates a  Graph with n vertices and 0 edges

    public void addEdge(int i, int j, int weight) {
        //adds an edge to the graph
        adjList.add(new EdgeNode(i, j, weight));
        adjList.add(new EdgeNode(j, i, weight));
        nEdges++;
    }

    public void printGraph() {
        //prints nVertices, nEdges, and adjacency lists and total edge weight
        System.out.printf("Graph: nVertices = %d\tnEdges = %d\ttotalEdgeWeight = %d\n", nVertices, nEdges, get_TotalWeightOfEdges());
        System.out.println("Adjacency Lists");
        TreeMap<Integer, String> hashMap = new TreeMap<Integer, String>();
        String vertexOutput;
        for(EdgeNode edgeNode : adjList) {

            if(hashMap.containsKey(edgeNode.vertex1)) {

                vertexOutput = hashMap.get(edgeNode.vertex1);
                vertexOutput += ", (" + edgeNode.vertex1 + ", " + edgeNode.vertex2 + ", " + edgeNode.weight + ")";

                hashMap.put(edgeNode.vertex1, vertexOutput);

            } else {

                vertexOutput = "v= " + edgeNode.vertex1 + "\t[(" + edgeNode.vertex1 + ", " + edgeNode.vertex2 + ", " + edgeNode.weight + ")";
                hashMap.put(edgeNode.vertex1, vertexOutput);

            }

        }

        for(String string : hashMap.values())
            System.out.println(string + "]");

    }

    public int get_nVertices() {
        return nVertices;
    }

    public int get_nEdges() {
        return nEdges;
    }

    public int get_TotalWeightOfEdges() {
        int totalEdgeWeight = 0;
        for(EdgeNode edgeNode : adjList)
            totalEdgeWeight += edgeNode.weight;

        return totalEdgeWeight;
    }


    public Graph dfsTraversal(int start) {
        /* Use recursion by calling a recursive dfs method;
            Visit all nodes
            If graph is not connected you will need to call dfs more than once to visit all
            node and to print out the information below.
            Print the following information gleaned from the dfs traversal
        Print nodes in order visited
        Connected?    ____
        NumberOfComponents?   _____
        Has a cycle?   _______
               If the graph is connected, return the spanning tree in the dfs traversal.
                Otherwise, return null.

        */

        return new Graph(0);
    }


    public Graph bfsTraversal(int start) {
/*
    Print the level lists found from the start vertex, one line at a time, labeled
    If the graph is connected, return the spanning tree discovered.
    Otherwise return null.
    */
        return new Graph(0);
    }

    public ArrayList<Integer> getShortestEdgePath(int x, int y) {
/*
     Return the shortest edge path from x to y .
     If no such list, return null
     Use breadth first search
*/
        return new ArrayList<>();
    }


    public void dijkstraShortestPaths(int start) {
/* Implement Dijkstra algorithm from text as discussed in class;
    Use the Java PriorityQueue<PQNode>   class. Use PQNode class below.
    This class has no updateKey method
    To simulate an updateKey method in priority queue, see Problem C-14.3 from text.
    Prints shortest paths from vertex start to all other vertices reachable from start
*/
    }

    public int[] bellmanFordShortestPaths(int start) {
/* Implement Bellman Ford Shortest Path algorithm from text
    Prints shortest paths from vertex start to all other vertices reachable from start */
        return new int[0];
    }

    public Graph KruskalMST() {
/* Implement Kruskal algorithm from text.
    Use clusters.
    If graph is connected
            Print the edges of the MST found and its total weight
             Returns the minimum spanning tree as a Graph
     else
	Print a message and return null
 */
        return new Graph(0);
    }


}


class EdgeNode implements Comparable<EdgeNode> {
    int vertex1;
    int vertex2;
    int weight;

    public EdgeNode(int v1, int v2, int w) {
        vertex1 = v1;
        vertex2 = v2;
        weight = w;
    }

    public int compareTo(EdgeNode node) {
        return weight - node.weight;
    }

    public String toString() {

        String s = "(";
        s = s + vertex1 + "," + vertex2 + "," + weight + ")";
        return s;
    }
}


class PQNode implements Comparable<PQNode> {
    int vertex;
    int distance;

    public PQNode(int v, int z) {
        vertex = v;
        distance = z;
    }

    public int compareTo(PQNode node) {
        return (distance - node.distance);
    }

    public String toString() {
        return "(" + vertex + "," + distance;
    }
}
