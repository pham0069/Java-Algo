package graph.cycleDetection;

/**
 * https://www.geeksforgeeks.org/detect-cycle-direct-graph-using-colors/
 * The idea is to do DFS of given graph and while doing traversal, assign one of the below three colors to every vertex.
 * 1. WHITE : Vertex is not processed yet.  Initially all vertices are WHITE.
 * 2. GRAY : Vertex is being processed (DFS for this vertex has started, but not finished which means that all descendants
 * (in DFS tree) of this vertex are not processed yet (or this vertex is in function call stack)
 * 3. BLACK : Vertex and all its descendants are processed.
 *
 * While doing DFS, if we encounter an edge from current vertex to a GRAY vertex, then this edge is back edge and hence
 * there is a cycle.
 *
 *
 */
public class CycleDetectionUsingColors {
    public static void main(String[] args) {

    }

}
