package graph.topologicalSorting;


import graph.weightedGraph.Edge;
import graph.weightedGraph.UndirectedGraph;
import graph.weightedGraph.Vertex;
import graph.weightedGraph.WeightedGraphImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/longest-path-between-any-pair-of-vertices/
 *
 * We are given a map of cities connected with each other via cable lines such that there is no cycle between any two
 * cities. We need to find the maximum length of cable between any two cities for given city map.
 * For example, input : n = 6
 1 2 3  // Cable length from 1 to 2 (or 2 to 1) is 3
 2 3 4
 2 6 2
 6 4 6
 6 5 5
 * Output: maximum length of cable = 12
 *
 */
public class LongestPathInDAG {
    public static void main(String[] args) {
        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<>();
        IntStream.rangeClosed(1, 6).forEach(i -> graph.addVertex(i));
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 4);
        graph.addEdge(2, 6, 2);
        graph.addEdge(6, 4, 6);
        graph.addEdge(6, 5, 5);

        printLongestPath(graph);
    }

    private static void reductionCost() {

    }

    static <T> void printLongestPath(UndirectedGraph<T, Integer> graph) {
        int maxLength = 0;
        for (Vertex<T> vertex : graph.getVertices()) {
            int l = getMaxLengthFromVertex(graph, vertex);
            if (l > maxLength)
                maxLength = l;
        }
        System.out.println(maxLength);
    }

    static <T> int getMaxLengthFromVertex(WeightedGraphImpl<T, Integer> graph, Vertex<T> start) {
        Stack<Vertex<T>> stack = new Stack<>();
        Map<Vertex<T>, Integer> maxLengthMap = new HashMap<>();
        int maxLength = 0;
        maxLengthMap.put(start, 0);
        stack.push(start);
        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.peek();
            boolean shouldPop = true;
            for (Edge<T, Integer> edge : graph.getEdges(vertex)) {
                Vertex<T> adjacent = edge.getVertex2();
                if (stack.size() == 1 || !adjacent.equals(stack.get(stack.size()-2))) {
                    stack.push(adjacent);
                    int currentLength = maxLengthMap.get(vertex) + edge.getWeight();
                    if (maxLengthMap.get(adjacent) == null
                            || currentLength > maxLengthMap.get(adjacent))
                        maxLengthMap.put(adjacent, currentLength);
                    if (currentLength > maxLength)
                        maxLength = currentLength;
                    shouldPop = false;
                    break;
                }
            }
            if (shouldPop) {
                stack.pop();
            }
        }

        return maxLength;
    }
}
