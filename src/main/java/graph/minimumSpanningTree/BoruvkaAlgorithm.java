package graph.minimumSpanningTree;

/**
 * https://www.geeksforgeeks.org/boruvkas-algorithm-greedy-algo-9/
 *
 * Algorithm
 * 1) Input is a connected, weighted and directed graph.
 * 2) Initialize all vertices as individual components (or sets).
 * 3) Initialize MST as empty.
 * 4) While there are more than one components, do following for each component.
 * a)  Find the closest weight edge that connects thiscomponent to any other component.
 * b)  Add this closest edge to MST if not already added.
 * 5) Return MST.
 *
 * Time Complexity of Boruvka’s algorithm is O(E log V) which is same as Kruskal’s and Prim’s algorithms.
 */
public class BoruvkaAlgorithm {
}
