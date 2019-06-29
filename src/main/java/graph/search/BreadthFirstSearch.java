package graph.search;

import graph.unweightedGraph.Graph;
import graph.unweightedGraph.UndirectedGraph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
 *
 */
public class BreadthFirstSearch {
    public static void main(String[] args) {
        Graph<Integer> graph = UndirectedGraph.one();
        breadthFirstSearch(graph, 3);
    }

    /**
     * Traverse all vertices if graph is connected
     */
    static <V> void breadthFirstSearch(Graph<V> graph) {
        Set<V> vertices = graph.getVertices();
        if (vertices.isEmpty())
            return;
        breadthFirstSearch(graph, vertices.iterator().next());
    }

    static <V> void breadthFirstSearch(Graph<V> graph, V start) {
        Deque<V> deque = new ArrayDeque<>();
        Set<V> visited = new HashSet<>();
        deque.add(start);
        while (deque.peek() != null) {
            V vertex = deque.poll();
            visited.add(vertex);
            graph.getAdjacency(vertex).stream()
                    .filter(v -> !visited.contains(v))
                    .forEach(deque::addLast);
            System.out.println(vertex);
        }
    }
}
