package graph;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a graph: stores the array of city nodes, the
 * adjacency list, as well as the hash table that maps city names to node ids.
 * Nodes are cities (of type CityNode); edges connect them and the cost of each edge
 * is the distance between the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a MinHeap from scratch, instead of using Java's built in PriorityQueue.
 */
public class Graph {
    private CityNode[] nodes; // nodes of the graph
    private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
    private int numEdges; // total number of edges
    // Add other variable(s) as needed:
    // add a HashMap to map cities to vertexIds.
    Map<String, Integer> map = new HashMap<>();

    /**
     * Constructor. Read graph info from the given file,
     * and create nodes and edges of the graph.
     *
     *   @param filename name of the file that has nodes and edges
     */
    public Graph(String filename) {
        // FILL IN CODE
        List<String> lines = readGraphFile(filename);

        int nodeNums = Integer.parseInt(lines.get(1).strip());
        this.nodes = new CityNode[nodeNums];

        for (int i = 0; i < nodeNums; i++) {
            String[] sts = lines.get(i+2).split(" ");
            String name = sts[0];
            double x = Double.parseDouble(sts[1]);
            double y = Double.parseDouble(sts[2]);
            this.nodes[i] = new CityNode(name, x, y);
            map.put(name, i);
        }

        this.adjacencyList = new Edge[nodeNums];
        for (int i = nodeNums + 3; i < lines.size(); i++) {
            String[] sts = lines.get(i).split(" ");
            int sourceCity = map.get(sts[0]);
            int destCity = map.get(sts[1]);
            int cost = Integer.parseInt(sts[2]);

            Edge edgeS = new Edge(sourceCity, destCity, cost);
            Edge edgeD = new Edge(destCity, sourceCity, cost);
            numEdges += 2;

            Edge p = getFirstEdge(sourceCity);
            if (p == null) {
                this.adjacencyList[sourceCity] = edgeS;
            } else {
                while (p.next() != null) {
                    p = p.next();
                }
                p.setNext(edgeS);
            }

            p = getFirstEdge(destCity);
            if (p == null) {
                this.adjacencyList[destCity] = edgeD;
            } else {
                while (p.next() != null) {
                    p = p.next();
                }
                p.setNext(edgeD);
            }

        }
    }

    /**
     * read USA.txt to String
     * @param fileName
     * @return
     */
    private List<String> readGraphFile(String fileName) {
        List<String> res = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null) {
                res.add(str);
            }
        } catch (IOException e) {
        }
        return res;
    }
    /**
     * Return the number of nodes in the graph
     * @return number of nodes
     */
    public int numNodes() {
        return nodes.length;
    }

    /** Return the head of the linked list that contains all edges outgoing
     * from nodeId
     * @param nodeId id of the node
     * @return head of the linked list of Edges
     */
    public Edge getFirstEdge(int nodeId) {
        return adjacencyList[nodeId];
    }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        if (adjacencyList == null || adjacencyList.length == 0) {
            System.out.println("Adjacency list is empty. Load the graph first.");
            return null;
        }
        Point[][] edges2D = new Point[numEdges][2];
        int idx = 0;
        for (int i = 0; i < adjacencyList.length; i++) {
            for (Edge tmp = adjacencyList[i]; tmp != null; tmp = tmp.next(), idx++) {
                edges2D[idx][0] = nodes[tmp.getId1()].getLocation();
                edges2D[idx][1] = nodes[tmp.getId2()].getLocation();
            }
        }

        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes() {
        if (nodes == null) {
            System.out.println("Array of nodes is empty. Load the graph first.");
            return null;
        }
        Point[] nodes = new Point[this.nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = this.nodes[i].getLocation();
        }

        return nodes;
    }

    /**
     * Used in GUIApp to display the names of the cities.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities() {
        if (nodes == null) {
            return null;
        }
        String[] labels = new String[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            labels[i] = nodes[i].getCity();
        }

        return labels;

    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) {
        return nodes[nodeId];
    }

    public Map<String, Integer> getMap() {
        return map;
    }
}
