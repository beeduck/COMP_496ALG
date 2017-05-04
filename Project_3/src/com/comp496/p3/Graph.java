/**
 * Kristopher Morris
 * Project 3 - COMP 496ALG Spring 2017
 * 04/27/17
 */

package com.comp496.p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
                if(line.isEmpty())
                    continue;
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
            if(edgeNode.visited)
                continue;

            if(!visitedMap.get(edgeNode.vertex2)) {
                spanningEdges.add(edgeNode);
                visitEdge(edgeNode);
                dfsTraversal(edgeNode.vertex2, adjList[edgeNode.vertex2]);
            } else if (visitedMap.get(edgeNode.vertex2) && !edgeNode.visited) {
                cycle = true;
            }
        }
    }

    private void visitEdge(EdgeNode edgeNode) {

        edgeNode.visited = true;

        for(EdgeNode edgeNode1 : adjList[edgeNode.vertex2]) {
            if(edgeNode1.vertex2 == edgeNode.vertex1) {
                edgeNode1.visited = true;
            }
        }
    }


    public Graph bfsTraversal(int start) {

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

        ArrayList<Integer> path = new ArrayList<>();
        if(x == y) {
            path.add(x);
            return path;
        }

        initializeVisitedMap();

        int previousNode[] = new int[nVertices];
        Queue<EdgeNode> edgeNodeQueue = new LinkedList<>();
        for(EdgeNode edgeNode : adjList[x])
            edgeNodeQueue.add(edgeNode);

        EdgeNode edgeNode;
        while(!edgeNodeQueue.isEmpty()) {

            edgeNode = edgeNodeQueue.remove();
            if(visitedMap.get(edgeNode.vertex2))
                continue;

            visitedMap.put(edgeNode.vertex2, true);

            previousNode[edgeNode.vertex2] = edgeNode.vertex1;

            if(edgeNode.vertex2 == y) {
                int current = y;
                path.add(current);
                while(previousNode[current] != x) {
                    path.add(previousNode[current]);
                    current = previousNode[current];
                }
                path.add(x);
                Collections.reverse(path);
                return path;
            }

            for(EdgeNode edgeNode1 : adjList[edgeNode.vertex2])
                edgeNodeQueue.add(edgeNode1);
        }

        return null;
    }


    public void dijkstraShortestPaths(int start) {

        // Distance from start
        int[] distance = new int[nVertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        // Parent of node on shortest path
        int[] parentNode = new int[nVertices];
        Arrays.fill(parentNode, -1);

        initializeVisitedMap();

        PriorityQueue<PQNode> priorityQueue = new PriorityQueue<>();

        // Initialize PQ
        for(int i = 0; i < nVertices; i++)
            priorityQueue.add(new PQNode(i, distance[i]));

        PQNode pqNode = priorityQueue.remove();
        while(pqNode.distance != Integer.MAX_VALUE){
            visitedMap.put(pqNode.vertex, true);
            for(EdgeNode edgeNode : adjList[pqNode.vertex]) {
                if(visitedMap.get(edgeNode.vertex2))
                    continue;

                // Update distance for v2 if v1 + the weight from it to v2 is less than v2 current distance
                if(distance[edgeNode.vertex2] > pqNode.distance + edgeNode.weight) {
                    distance[edgeNode.vertex2] = pqNode.distance + edgeNode.weight;
                    parentNode[edgeNode.vertex2] = pqNode.vertex;
                    priorityQueue.add(new PQNode(edgeNode.vertex2, distance[edgeNode.vertex2]));
                }
            }

            pqNode = priorityQueue.remove();
        }

        // Output distance and weights
        for(int i = 0; i < nVertices; i++) {
            if(i == start || distance[i] == Integer.MAX_VALUE)
                continue;

            System.out.print("dijk - [" + start + " to " + i + "]:\tweight: " + distance[i]);
            String path = String.valueOf(i);
            int parent = parentNode[i];
            while(parent != start) {
                path = String.valueOf(parent) + " " + path;
                parent = parentNode[parent];
            }
            path = String.valueOf(start) + " " + path;
            System.out.println('\t' + path);
        }
    }

    public int[] bellmanFordShortestPaths(int start) {

        // Distance from start
        int[] distance = new int[nVertices];
        Arrays.fill(distance, Integer.MAX_VALUE/2);
        distance[start] = 0;

        // Parent of node on shortest path
        int[] parentNode = new int[nVertices];
        Arrays.fill(parentNode, -1);

        for(int i = 0; i < nVertices - 1; i++) {
            for(EdgeNode edgeNode : adjList[i]) {

                if(distance[edgeNode.vertex2] > distance[i] + edgeNode.weight) {
                    distance[edgeNode.vertex2] = distance[i] + edgeNode.weight;
                    parentNode[edgeNode.vertex2] = i;
                }

            }
        }


        // Output distance and weights
        for(int i = 0; i < nVertices; i++) {
            if(i == start || distance[i] == Integer.MAX_VALUE)
                continue;

            System.out.print("bellman - [" + start + " to " + i + "]:\tweight: " + distance[i]);
            String path = String.valueOf(i);
            int parent = parentNode[i];
            while(parent != start) {
                path = String.valueOf(parent) + " " + path;
                parent = parentNode[parent];
            }
            path = String.valueOf(start) + " " + path;
            System.out.println('\t' + path);
        }

        return new int[0];
    }

    public Graph KruskalMST() {

        Graph graph = new Graph(nVertices);

        // Initialize array for parent of cluster
        int clusterParents = nVertices;
        int parentCluster[] = new int[nVertices];
        for(int i = 0; i < nVertices; i++) {
            parentCluster[i] = i;
        }

        PriorityQueue<EdgeNode> priorityQueue = new PriorityQueue<>();

        // Initialize PQ
        for(int i = 0; i < nVertices; i++)
            for(EdgeNode edgeNode : adjList[i])
                priorityQueue.add(edgeNode);

        EdgeNode edgeNode;
        while(!priorityQueue.isEmpty()) {
            edgeNode = priorityQueue.remove();

            if(parentCluster[edgeNode.vertex2] != parentCluster[edgeNode.vertex1]) {

                // Decrease cluster parents and add edge to graph
                clusterParents--;
                graph.addEdge(edgeNode.vertex1, edgeNode.vertex2, edgeNode.weight);

                // Move all nodes of old cluster parent to new cluster parent
                int oldCluster = parentCluster[edgeNode.vertex2];
                for(int i = 0; i < nVertices; i++) {
                    if(parentCluster[i] == oldCluster)
                        parentCluster[i] = parentCluster[edgeNode.vertex1];
                }

            }
        }

        // Output graph if there is a single cluster (connected)
        if(clusterParents == 1) {
            System.out.println("krustal - mst");
            graph.printGraph();

            return graph;
        } else {
            System.out.println("kruskal - graph is not connected.");
            return null;
        }
    }


    private Graph createSpanningGraph(ArrayList<EdgeNode> spanningEdges) {
        Graph graph = new Graph(nVertices);
        for(EdgeNode edgeNode : spanningEdges)
            graph.addEdge(edgeNode.vertex1, edgeNode.vertex2, edgeNode.weight);

        return graph;
    }

    private void initializeVisitedMap() {
        visitedMap = new HashMap<>();
        for(int i = 0; i < nVertices; i++)
            visitedMap.put(i, false);
    }
}

class EdgeNode implements Comparable<EdgeNode> {
    int vertex1;
    int vertex2;
    int weight;

    boolean visited;

    public EdgeNode(int v1, int v2, int w) {
        vertex1 = v1;
        vertex2 = v2;
        weight = w;
        visited = false;
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
