package graph.minimumSpanningTree;

import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/
 *
 * Like Kruskal’s algorithm, Prim’s algorithm is also a Greedy algorithm. It starts with an empty spanning tree. The
 * idea is to maintain two sets of vertices. The first set contains the vertices already included in the MST, the other
 * set contains the vertices not yet included. At every step, it considers all the edges that connect the two sets, and
 * picks the minimum weight edge from these edges. After picking the edge, it moves the other endpoint of the edge to
 * the set containing MST.
 *
 * A group of edges that connects two set of vertices in a graph is called cut in graph theory. So, at every step of
 * Prim’s algorithm, we find a cut (of two sets, one contains the vertices already included in MST and other contains
 * rest of the vertices), pick the minimum weight edge from the cut and include this vertex to MST Set (the set that
 * contains already included vertices).
 *
 * The solution handles an undirected graph represented by adjacent matrix. For 2 vertices u, v, graph[u][v] stores the
 * distance btw u and v. graph[u][v] = graph[v][u]. If u and v is not connected, graph[u][v] = 0
 *
 * The algorithm takes V loops. Each loop finds the next vertex to put to MST by getting min distance to current MST and
 * update the the min distance to MST of the remaining vertices, which takes at most 2V operations. Total time complexity
 * is O(V^2). Space complexity is O(V^2) to store the matrix.
 *
 * The complexity can be improved using adjacency list and binary heap
 *
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        int[][] graph = {
                {0, 2, 0, 6, 0},
                {2, 0, 3, 8, 5},
                {0, 3, 0, 0, 7},
                {6, 8, 0, 0, 9},
                {0, 5, 7, 9, 0}};
        getMST(graph);
    }

    static int getVertexWithMinDistanceToMST(int[] distanceToMST, boolean[] inMST, int n) {
        int minDistanceToMST = Integer.MAX_VALUE;
        int minVertex = -1;
        for (int i = 0; i < n; i++) {
            if (!inMST[i] && distanceToMST[i] < minDistanceToMST) {
                minDistanceToMST = distanceToMST[i];
                minVertex = i;
            }
        }
        return minVertex;
    }

    static void updateMinDistanceToMST(int[][] graph, int[] distanceToMST, int[] connectionToMST, boolean[] inMST, int n, int vertex) {
        for (int i = 0; i < n; i++) {
            int distanceToVertex = graph[vertex][i];
            if (!inMST[i] && distanceToVertex!= 0 && distanceToVertex < distanceToMST[i]) {
                distanceToMST[i] = distanceToVertex;
                connectionToMST[i] = vertex;
            }
        }
    }
    static void getMST(int[][] graph) {
        int n = graph.length;
        int[] distanceToMST = new int[n];
        int[] connectionToMST = new int[n];
        boolean[] inMST = new boolean[n];

        /**
         * Initialize parameters to hold data
         * Put vertex 0 to MST first
         */
        int nextVertex = 0;
        distanceToMST[nextVertex] = 0;
        inMST[nextVertex] = true;
        IntStream.range(1, n).forEach(i -> distanceToMST[i] = Integer.MAX_VALUE);
        IntStream.range(1, n).forEach(i -> inMST[i] = false);
        updateMinDistanceToMST(graph, distanceToMST, connectionToMST, inMST, n, nextVertex);

        long totalDistance = 0;
        /**
         * Get the vertex with minimum distance to the currently constructed MST and put it to MST
         * Update the minimum distance to the currently constructed MST of those vertices that have not been in MST yet
         */
        for (int i = 1; i < n; i++) {
            nextVertex = getVertexWithMinDistanceToMST(distanceToMST, inMST, n);
            if (nextVertex == -1){
                System.out.println("Cannot construct MST");
                break;
            }
            System.out.println("MST edge " + nextVertex + "-" + connectionToMST[nextVertex]
                    + " @ " + distanceToMST[nextVertex]);
            totalDistance += distanceToMST[nextVertex];
            inMST[nextVertex] = true;
            updateMinDistanceToMST(graph, distanceToMST, connectionToMST, inMST, n, nextVertex);
        }
        System.out.println("MST has total distance = " + totalDistance);
    }

}
