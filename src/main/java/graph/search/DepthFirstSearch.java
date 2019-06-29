package graph.search;

import graph.unweightedGraph.Graph;
import graph.unweightedGraph.UndirectedGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
 *
 */
public class DepthFirstSearch {
    public static void main(String[] args) {
        Graph<Integer> graph = UndirectedGraph.one();
        depthFirstSearch(graph);
    }

    /**
     * Traverse the whole graph. Work even if graph is disconnected
     */
    static <V> void depthFirstSearch(Graph<V> graph) {
        Set<V> visited = new HashSet<>();
        Set<V> vertices = graph.getVertices();
        for (V vertex : vertices) {
            if (!visited.contains(vertex))
                depthFirstSearch(graph, visited, vertex);
        }
    }

    /**
     * Search from a given vertex
     * @param graph
     * @param start
     * @param <V>
     */
    static <V> void depthFirstSearch(Graph<V> graph, V start) {
        Set<V> visited = new HashSet<>();
        depthFirstSearch(graph, visited, start);
    }
    /**
     * Use stack to hold traverse path for backtrack, as alternative to recursive approach to avoid stack overflow
     */
    static <V> void depthFirstSearch(Graph<V> graph, Set<V> visited, V start) {
        Stack<V> stack = new Stack<>();

        visit(stack, visited, start);

        while (!stack.isEmpty()) {
            V vertex = stack.peek();
            boolean shouldPop = true;
            for (V adjacent : graph.getAdjacency(vertex)) {
                if (!visited.contains(adjacent)) {
                    visit(stack, visited, adjacent);
                    shouldPop = false;
                    break;
                }
            }
            if (shouldPop)
                stack.pop();
        }
    }

    static <V> void visit(Stack<V> stack, Set<V> visited, V vertex) {
        stack.push(vertex);
        visit(visited, vertex);
    }
    static <V> void visit(Set<V> visited, V vertex) {
        visited.add(vertex);
        System.out.println(vertex);
    }

    /**
     * Recursive depth first search. Easy, intuitive but could cause stack overflow
     */
    static <V> void depthFirstSearch2(Graph<V> graph, V start) {
        Set<V> visited = new HashSet<>();
        recursiveSearch(graph, visited, start);
    }

    static <V> void recursiveSearch (Graph<V> graph, Set<V> visited, V vertex) {
        visit(visited, vertex);
        for (V adjacent : graph.getAdjacency(vertex)) {
            if (!visited.contains(adjacent))
                recursiveSearch(graph, visited, adjacent);
        }
    }
}
