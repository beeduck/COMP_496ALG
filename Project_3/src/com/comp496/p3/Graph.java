package com.comp496.p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by duck on 4/24/17.
 */
public class Graph {
    private ArrayList<EdgeNode>[] adjList;
    private int nVertices;
    private int nEdges;
    private int totalEdgeWeight;

    public Graph(String inputFileName) {
        //creates Graph from data in file
        System.out.println(new File(inputFileName).getAbsolutePath());
        String line;
        String[] split;
        this.nEdges = 0;
        this.totalEdgeWeight = 0;
        try {
            FileReader fileReader = new FileReader(inputFileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if( (line = bufferedReader.readLine()) != null)
                this.nVertices = Integer.valueOf(line);

            this.initializeAdjList();

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
        //Creates a  Graph with n vertices and 0 edges
        this.nVertices = n;
        this.totalEdgeWeight = 0;
        this.initializeAdjList();
    }

    private void initializeAdjList() {
        adjList = new ArrayList[this.nVertices];
        for(int i = 0; i < nVertices; i++)
            adjList[i] = new ArrayList<>();
    }

    public void addEdge(int i, int j, int weight) {
        //adds an edge to the graph
        if(adjList[i] == null)
            adjList[i] = new ArrayList<>();

        adjList[i].add(new EdgeNode(i, j, weight));

        if(adjList[j] == null)
            adjList[j] = new ArrayList<>();

        adjList[j].add(new EdgeNode(j, i, weight));

        totalEdgeWeight += weight;
        nEdges++;
    }

    public void printGraph() {
        //prints nVertices, nEdges, and adjacency lists and total edge weight
        System.out.printf("Graph: nVertices = %d\tnEdges = %d\ttotalEdgeWeight = %d\n", nVertices, nEdges, get_TotalWeightOfEdges());
        System.out.println("Adjacency Lists");

        int vertexCount = 0;
        for(ArrayList<EdgeNode> edgeNodes : adjList)
            System.out.println("v= " + vertexCount++ + '\t' + edgeNodes.toString());

    }

    public int get_nVertices() {
        return nVertices;
    }

    public int get_nEdges() {
        return nEdges;
    }

    public int get_TotalWeightOfEdges() {
        return totalEdgeWeight;
    }

    private boolean cycle;
    private HashMap<Integer, Boolean> visitedMap;
    private ArrayList<EdgeNode> spanningEdges;
    public Graph dfsTraversal(int start) {

        // Initialize DFS traversal
        cycle = false;
        visitedMap = new HashMap<>();
        spanningEdges = new ArrayList<>();

        for(int i = 0; i < nVertices; i++)
            visitedMap.put(i, false);


        // Begin DFS traversal on start vertex
        int components = 1;
        dfsTraversal(start, adjList[start]);


        // Begin DFS traversal on unvisited vertices
        boolean connected = true;
        for(Map.Entry<Integer, Boolean> entry : visitedMap.entrySet()) {
            if(!entry.getValue()) {
                connected = false;
                components++;
                dfsTraversal(entry.getKey(), adjList[entry.getKey()]);
            }
        }


        // Output DFS traversal results
        System.out.println( "dfs - cycle: " + (cycle ? "yes" : "no") );
        System.out.println( "dfs - connected: " + (connected ? "yes" : "no") );
        System.out.println( "dfs - components: " + components);

        if(connected) {
            return createSpanningGraph(spanningEdges);
        }

        return null;
    }

    private void dfsTraversal(int vertex, ArrayList<EdgeNode> edgeNodes) {
        System.out.println("dfs visited: " + vertex);
        if(!visitedMap.get(vertex)) {
            visitedMap.put(vertex, true);
        }

        // Traverse each edge if the destination vertex is unvisited
        for(EdgeNode edgeNode : edgeNodes) {
            if(!visitedMap.get(edgeNode.vertex2)) {
                spanningEdges.add(edgeNode);
                dfsTraversal(edgeNode.vertex2, adjList[edgeNode.vertex2]);
            } else {
                cycle = true;
            }
        }
    }


    public Graph bfsTraversal(int start) {
/*
    Print the level lists found from the start vertex, one line at a time, labeled
    If the graph is connected, return the spanning tree discovered.
    Otherwise return null.
    */

        // Initialize BFS traversal
        visitedMap = new HashMap<>();
        for(int i = 0; i < nVertices; i++)
            visitedMap.put(i, false);

        spanningEdges = new ArrayList<>();
        ArrayList<ArrayList<Integer>> levels = new ArrayList<>();


        // Initialize queue with starting vertex edges
        Queue<EdgeNode> edgeNodeQueue = new LinkedList<>();
        ArrayList<EdgeNode> edgeNodes = adjList[start];
        visitedMap.put(start, true);
        ArrayList<Integer> levelList = new ArrayList<>();
        levelList.add(start);
        levels.add(levelList);
        for(EdgeNode edgeNode : edgeNodes)
            edgeNodeQueue.add(edgeNode);


        // Begin traversal
        EdgeNode edgeNode;
        levelList = new ArrayList<>();
        while(!edgeNodeQueue.isEmpty()) {

            edgeNode = edgeNodeQueue.remove();
            if(!visitedMap.get(edgeNode.vertex2)) {
                levelList.add(edgeNode.vertex2);
                visitedMap.put(edgeNode.vertex2, true);
                spanningEdges.add(edgeNode);
                for(EdgeNode edgeNode1 : adjList[edgeNode.vertex2]) {
                    edgeNodeQueue.add(edgeNode1);
                }

            } else {
                if(!levelList.isEmpty()) {
                    levels.add(levelList);
                    levelList = new ArrayList<>();
                }
            }

        }


        // Output traversal results
        boolean connected = true;
        for(boolean bool : visitedMap.values())
            if(!bool) connected = false;

        int level = 0;
        for(ArrayList<Integer> arrayList : levels)
            System.out.println("L" + level++ +  ": " + arrayList);
        System.out.println( "bfs - connected: " + (connected ? "yes" : "no") );

        if(connected) {
            return createSpanningGraph(spanningEdges);
        }

        return null;
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


    private Graph createSpanningGraph(ArrayList<EdgeNode> spanningEdges) {
        Graph graph = new Graph(nVertices);
        for(EdgeNode edgeNode : spanningEdges)
            graph.addEdge(edgeNode.vertex1, edgeNode.vertex2, edgeNode.weight);

        return graph;
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
