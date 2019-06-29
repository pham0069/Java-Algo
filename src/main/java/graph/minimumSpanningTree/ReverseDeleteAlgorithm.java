package graph.minimumSpanningTree;

/**
 * https://www.geeksforgeeks.org/reverse-delete-algorithm-minimum-spanning-tree/
 *
 * Reverse KDTreeDelete algorithm is closely related to Kruskal’s algorithm. In Kruskal’s algorithm what we do is : Sort edges
 * by increasing order of their weights. After sorting, we one by one pick edges in increasing order. We include current
 * picked edge if by including this in spanning tree not form any cycle until there are V-1 edges in spanning tree,
 * where V = number of vertices.
 *
 * In Reverse KDTreeDelete algorithm, we sort all edges in decreasing order of their weights. After sorting, we one by one
 * pick edges in decreasing order. We include current picked edge if excluding current edge causes disconnection in
 * current graph. The main idea is delete edge if its deletion does not lead to disconnection of graph.
 *
 *
 * Algorithm
 * 1) Sort all edges of graph in non-increasing order of edge weights.
 * 2) Initialize MST as original graph and remove extra edges using step 3.
 * 3) Pick highest weight edge from remaining edges and check if deleting the edge disconnects the graph or not. If
 * it disconnects, then we don't delete the edge. Else we delete the edge and continue.
 *
 * This algorithm can be optimized to O(ElogV*(loglog V)^3). But this optimized time complexity is still less efficient
 * than Prim and Kruskal Algorithms for MST.
 */
public class ReverseDeleteAlgorithm {
}
