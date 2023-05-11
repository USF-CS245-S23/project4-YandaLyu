package mstAlgorithms;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;

/** Subclass of MSTAlgorithm. Uses Prim's algorithm to compute MST of the graph. */
public class PrimAlgorithm extends MSTAlgorithm {
    private int sourceVertex;

    /**
     * Constructor for PrimAlgorithm. Takes the graph
     * @param graph input graph
     * @param sourceVertex the first vertex of MST
     */
    public PrimAlgorithm(Graph graph, int sourceVertex) {
        super(graph);
        this.sourceVertex = sourceVertex;
    }

    /**
     * Compute minimum spanning tree for this graph using Prim's algorithm.
     * Add edges of MST using the addMSTEdge method inherited from the parent
     * class MSTAlgorithm.
     * */
    @Override
    public void computeMST() {
        // FILL IN CODE
        boolean[] tableAdded = new boolean[numNodes()];
        List<Edge> candidates = new ArrayList<>();
        int nextV = sourceVertex;
        for (int i = 0; i < numNodes(); i++) {
            tableAdded[nextV] = true;
            Edge e = getFirstEdge(nextV);
            candidates.add(e);
            while (e.next() != null) {
                e = e.next();
                candidates.add(e);
            }
            nextV = findMinimumNonAddedVertex(tableAdded, candidates);
        }
    }
    /**
     * find min no added vertex
     * @param table
     * @param candidates
     * @return
     */
    private int findMinimumNonAddedVertex(boolean[] table, List<Edge> candidates) {
        Edge minEdge = null;
        int minCost = Integer.MAX_VALUE;
        for (Edge e:candidates
        ) {
            if (table[e.getId2()]) {
                continue;
            }
            if (e.getCost() < minCost) {
                minCost = e.getCost();
                minEdge = e;
            }
        }
        if (minEdge != null) {
            addMSTEdge(minEdge);
            int nextV = minEdge.getId2();
            candidates.remove(minEdge);
            return nextV;
        }

        return -1;

    }
}
