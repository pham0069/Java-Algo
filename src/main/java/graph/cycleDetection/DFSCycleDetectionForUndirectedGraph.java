package graph.cycleDetection;

import graph.unweightedGraph.Graph;
import graph.unweightedGraph.UndirectedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
 *
 * If we apply same code of DFSCycleDetectionForDirectedGraph on undirected graph, the answer is always true as each
 * edge (u,v) always form a circle. At a current vertex to find the next vertex to visit, we need to exclude the previous
 * vertex which was visited before the current vertex to avoid such a case
 *
 * Using recursive DFS is more intuitive to understand the approach. Using iterative DFS requires maintaining the
 * relationship between a vertex and its predecessor.
 *
 */
public class DFSCycleDetectionForUndirectedGraph {
    public static void main(String[] args) {
        System.out.println(hasCycle(testOne()));
        System.out.println(hasCycle(testTwo()));
        System.out.println(hasCycle(testThree()));
        System.out.println(hasCycle(testFour()));

        System.out.println(hasCycle2(testOne()));
        System.out.println(hasCycle2(testTwo()));
        System.out.println(hasCycle2(testThree()));
        System.out.println(hasCycle2(testFour()));
    }

    static Graph<Integer> testOne() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        IntStream.range(0, 5).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        return graph;
    }

    static Graph<Integer> testTwo() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        IntStream.range(0, 5).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(3, 4);
        return graph;
    }

    static Graph<Integer> testThree() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        IntStream.range(0, 6).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        return graph;
    }

    static Graph<Integer> testFour() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        IntStream.range(0, 6).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(4, 5);
        return graph;
    }

    static <V> boolean hasCycle2(Graph<V> graph) {
        Set<V> visited = new HashSet<V>();
        for (V v : graph.getVertices()) {
            if (!visited.contains(v))
                if (recursiveFindCycle(graph, visited, v, null))
                    return true;
        }
        return false;
    }

    static <V> boolean recursiveFindCycle(Graph<V> graph, Set<V> visited, V cur, V prev) {
        visited.add(cur);
        for (V next : graph.getAdjacency(cur)) {
            if (visited.contains(next) && prev != null && next != prev)
                return true;
            if (!visited.contains(next))
                if (recursiveFindCycle(graph, visited, next, cur))
                    return true;
        }
        return false;
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

    static <V> boolean foundCycleDuringDepthFirstSearch(Graph<V> graph, Set<V> visited, V start) {
        Stack<V> stack = new Stack<>();
        /**
         * map a vertex with the vertex preceding it in the traversal
         */
        Map<V, V> predecessor = new HashMap<>();
        visit(stack, visited, start);

        while (!stack.isEmpty()) {
            V cur = stack.peek();
            boolean shouldPop = true;
            for (V next : graph.getAdjacency(cur)) {
                /**
                 * ----------------------------------------------------
                 * Modification here:
                 * Check if next has been visited in this traversal
                 * and if it's not the same vertex as where current vertex comes from
                 * to avoid the case of detecting cycle formed between 2 vertices cur and next
                 */
                if (stack.contains(next) && predecessor.get(cur) != null && predecessor.get(cur) != next)
                    return true;
                //------------------------------------------------------
                if (!visited.contains(next)) {
                    predecessor.put(next, cur);
                    visit(stack, visited, next);
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
