package graph.topologicalSorting;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/all-topological-sorts-of-a-directed-acyclic-graph/
 *
 * In a Directed acyclic graph, we may have vertices which are unreachable from each other (i.e. u, v where there is no
 * path from u to v, or from v to u), thus we can order them in many ways. These various topological sorting is
 * important in many cases, for example if some relative weight is also available between the vertices, which is to
 * minimize then we need to take care of relative ordering as well as their relative weight, which creates the need of
 * checking through all possible topological ordering.
 *
 * We can go through all possible ordering via backtracking:
 * 1. Initialize all vertices as unvisited.
 * 2. Now choose vertex which is unvisited and has zero indegree and decrease indegree of all those vertices by 1
 * (corresponding to removing edges) now add this vertex to result and call the recursive function again and backtrack.
 * After returning from function reset values of visited, result and indegree for enumeration of other possibilities.
 */
public class AllTopologicalSorts {
    public static void main(String[] args) {
        DirectedGraph<Integer> graph = DirectedGraph.acyclicDirectedGraph2();
        printAllTopologicalSorts(graph);
    }



    private static <V> void allTopologicalSorts(Graph<V> graph, Set<V> visited, Map<V, Integer> indegree, Stack<V> stack) {
        boolean hasNextNodeToVisit = false;

        for (V vertex : graph.getVertices()) {
            if (!visited.contains(vertex) && indegree.get(vertex) == 0) {
                // including in result
                visited.add(vertex);
                stack.add(vertex);
                for (V adjacent : graph.getAdjacency(vertex)) {
                    int updatedDegree = indegree.get(adjacent) - 1;
                    indegree.put(adjacent, updatedDegree);
                }
                allTopologicalSorts(graph, visited, indegree, stack);

                // resetting visited, result and indegree for backtracking
                visited.remove(vertex);
                stack.pop();
                for (V adjacent : graph.getAdjacency(vertex)) {
                    int updatedDegree = indegree.get(adjacent) + 1;
                    indegree.put(adjacent, updatedDegree);
                }

                hasNextNodeToVisit = true;
            }
        }
        // We reach here if all vertices are visited. So we print the solution here
        if (!hasNextNodeToVisit) {
            stack.forEach(i -> System.out.print(i + " "));
            System.out.println();
        }
    }

    static <V> void printAllTopologicalSorts(Graph<V> graph) {
        Set<V> visited = new HashSet<>();
        Map<V, Integer> indegree = new HashMap<>();
        for (V vertex : graph.getVertices()) {
            indegree.put(vertex, 0);
        }
        for (V vertex : graph.getVertices()) {
            for (V adjacent : graph.getAdjacency(vertex)) {
                indegree.put(adjacent, indegree.get(adjacent)+1);
            }
        }
        Stack<V> stack = new Stack<V>();
        allTopologicalSorts(graph, visited, indegree, stack);
    }
}
