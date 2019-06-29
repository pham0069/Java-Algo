package graph.search;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.Set;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/transitive-closure-of-a-graph/
 * https://www.geeksforgeeks.org/transitive-closure-of-a-graph-using-dfs/
 *
 * Given a directed graph, find out if a vertex v is reachable from another vertex u for all vertex pairs (u, v) in the
 * given graph. Here reachable mean that there is a path from vertex u to v. The reach-ability matrix is called
 * transitive closure of a graph.
 *
 *
 */
public class TransitiveClosure {
    public static void main(String[] args) {
        boolean[][] transitiveClosure = getTransitiveClosure(testGraph());
        print(transitiveClosure);

    }

    static void print(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print((matrix[i][j]?1:0) + " ");
            System.out.println();
        }
    }

    static Graph<Integer> testGraph() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();
        IntStream.range(0, 4).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        return graph;
    }

    /**
     * Based on Floyd Warshall algorithm
     */
    static boolean[][] getTransitiveClosure(Graph<Integer> graph) {
        Set<Integer> vertices = graph.getVertices();
        int n = vertices.size();
        boolean[][] isReachable = new boolean[n][n];
        for (int vertex : vertices) {
            for (int adj : graph.getAdjacency(vertex))
                isReachable[vertex][adj] = true;
        }

        /* Add all vertices one by one to the set of intermediate vertices.
         ---> Before start of a iteration, we have reachability values for all pairs of vertices such that the
         reachability values  consider only the vertices in set {0, 1, 2, .. k-1} as intermediate vertices.
         ----> After the end of a iteration, vertex no. k is added to the  set of intermediate vertices and the set
         becomes {0, 1, .. k} */
        for (int k = 0; k < n; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < n; i++) {
                // Pick all vertices as destination for the above picked source
                for (int j = 0; j < n; j++) {
                    // If vertex k is on a path from i to j, then make sure that the value of reach[i][j] is 1
                    isReachable[i][j] = isReachable[i][j] || (isReachable[i][k] && isReachable[k][j]);
                }
            }
        }

        return isReachable;
    }
}
