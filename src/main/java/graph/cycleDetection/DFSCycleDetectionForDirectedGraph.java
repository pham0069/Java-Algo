package graph.cycleDetection;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
 *
 * Given a directed graph, check whether the graph contains a cycle or not. Your function should return true if the
 * given graph contains at least one cycle, else return false.
 *
 * Depth First Traversal can be used to detect a cycle in a Graph. DFS for a connected graph produces a tree. There is
 * a cycle in a graph only if there is a back edge present in the graph. A back edge is an edge that is from a node to
 * itself (self-loop) or one of its ancestor in the tree produced by DFS.
 *
 * To detect a back edge, we can keep track of vertices currently in recursion stack of function for DFS traversal. If
 * we reach a vertex that is already in the recursion stack, then there is a cycle in the tree. The edge that connects
 * current vertex to the vertex in the recursion stack is a back edge. We have used recStack[] array to keep track of
 * vertices in the recursion stack. Time complexity is O(V+E)
 *
 */
public class DFSCycleDetectionForDirectedGraph {
    public static void main(String[] args) {
        System.out.println(hasCycle(testDirectedGraph()));//return true
    }

    static Graph<Integer> testDirectedGraph() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();
        IntStream.range(0, 4).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3);
        return graph;
    }

    static <V> boolean hasCycle(Graph<V> graph) {
        Set<V> visited = new HashSet<V>();
        for (V v : graph.getVertices()) {
            if (!visited.contains(v))
                if (foundCycleDuringDepthFirstSearch(graph, visited, v))
                    return true;
        }
        return false;
    }

    /**
     * Modify depth first search to check for back edge
     * @return
     */
    static <V> boolean foundCycleDuringDepthFirstSearch(Graph<V> graph, Set<V> visited, V start) {
        Stack<V> stack = new Stack<>();

        visit(stack, visited, start);

        while (!stack.isEmpty()) {
            V vertex = stack.peek();
            boolean shouldPop = true;
            for (V adjacent : graph.getAdjacency(vertex)) {
                /**
                 * ----------------------------------------------------
                 * Modification here:
                 * Check if vertex has been visited in this traversal
                 * If yes, it is part of the back edge forming cycle
                 */
                if (stack.contains(adjacent))
                    return true;
                //------------------------------------------------------
                if (!visited.contains(adjacent)) {
                    visit(stack, visited, adjacent);
                    shouldPop = false;
                    break;
                }
            }
            if (shouldPop)
                stack.pop();
        }
        return false;
    }

    static <V> void visit(Stack<V> stack, Set<V> visited, V vertex) {
        stack.push(vertex);
        visited.add(vertex);
    }
}
