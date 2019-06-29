package graph.topologicalSorting;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.List;

/**
 *
 * https://www.geeksforgeeks.org/maximum-edges-can-added-dag-remains-dag/
 *
 * A DAG is given to us, we need to find maximum number of edges that can be added to this DAG, after which new graph
 * still remain a DAG that means the reformed graph should have maximal number of edges, adding even single edge will
 * create a cycle in graph.
 *
 * We have to add all the edges in one direction only to save ourselves from making a cycle. This is the trick to solve
 * this question. We sort all our nodes in topological order and create edges from node to all nodes to the right if
 * not there already.
 * How can we say that, it is not possible to add any more edge? The reason is we have added all possible edges from
 * left to right and if we want to add more edge we need to make that from right to left, but adding edge from right to
 * left we surely create a cycle because its counter part left to right edge is already been added to graph and creating
 * cycle is not what we needed.
 * So solution proceeds as follows, we consider the nodes in topological order and if any edge is not there from left to
 * right, we will create it.
 */
public class MaximumEdgeDAG {
    public static void main(String[] args) {
        DirectedGraph<Integer> graph = DirectedGraph.acyclicDirectedGraph();
        printMaximumEdgeAddition(graph);
    }

    static <V> void printMaximumEdgeAddition(Graph<V> graph) {
        List<V> topologicalSort = TopologicalSort.getTopologicalSort(graph);
        for (int i= 0; i < topologicalSort.size(); i++) {
            V vertex = topologicalSort.get(i);
            for (int j = i+1; j < topologicalSort.size(); j++) {
                V next = topologicalSort.get(j);
                if (!graph.getAdjacency(vertex).contains(next)) {
                    System.out.println(vertex + "-" + next);
                }

            }
        }
    }
}
