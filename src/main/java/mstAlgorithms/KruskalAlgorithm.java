package mstAlgorithms;

import graph.Edge;
import graph.Graph;
import sets.DisjointSets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Subclass of MSTAlgorithm. Computes MST of the graph using Kruskal's algorithm. */
public class KruskalAlgorithm extends MSTAlgorithm {

    /**
     * Constructor for KruskalAlgorithm. Takes the graph
     * @param graph input graph
     */
    public KruskalAlgorithm(Graph graph) {
        super(graph);
    }

    /**
     * Compute minimum spanning tree for this graph.
     * Add edges of MST using the addMSTEdge method inherited from the parent
     * class MSTAlgorithm.
     * Should use Kruskal's algorithm and DisjointSets class.
     */
    @Override
    public void computeMST() {
        // FILL IN CODE
        DisjointSets disjointSets = new DisjointSets();
        disjointSets.createSets(numNodes());
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < numNodes(); i++) {
            Edge e = getFirstEdge(i);
            while (e != null) {
                edges.add(e);
                e = e.next();
            }
        }
        Collections.sort(edges);
        for (Edge e:edges
        ) {
            if (disjointSets.find(e.getId1()) != disjointSets.find(e.getId2())) {
                addMSTEdge(e);
                disjointSets.union(e.getId1(), e.getId2());
            }

        }

    }

}

