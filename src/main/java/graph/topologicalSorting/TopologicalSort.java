package graph.topologicalSorting;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/topological-sorting/
 *
 * Topological sorting for a Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every directed
 * edge uv, vertex u comes before v in the ordering. Topological Sorting for a graph is not possible if the graph is not
 * a DAG.
 *
 * Topological Sorting is mainly used for scheduling jobs from the given dependencies among jobs. In computer science,
 * applications of this type arise in instruction scheduling, ordering of formula cell evaluation when recomputing
 * formula values in spreadsheets, logic synthesis, determining the order of compilation tasks to perform in makefiles,
 * data serialization, and resolving symbol dependencies in linkers
 *
 * We can modify DFS to find Topological Sorting of a graph. In DFS, we start from a vertex, we first print it and then
 * recursively call DFS for its adjacent vertices. In topological sorting, we use a temporary stack. We don’t print the
 * vertex immediately, we first recursively call topological sorting for all its adjacent vertices, then push it to a
 * stack. Finally, print contents of stack. Note that a vertex is pushed to stack only when all of its adjacent vertices
 * (and their adjacent vertices and so on) are already in stack.
 *
 *
 */
public class TopologicalSort {
    public static void main(String[] args) {
        DirectedGraph<Integer> graph = DirectedGraph.acyclicDirectedGraph();
        printTopologicalSort(graph);

    }

    static <V> void printTopologicalSort(Graph<V> graph) {
        Stack<V> reverseTopologicalSort = getReverseTopologicalSort(graph);
        while (!reverseTopologicalSort.isEmpty()) {
            System.out.print(reverseTopologicalSort.pop() + " ");
        }
    }

    static <V> List<V> getTopologicalSort(Graph<V> graph) {
        List<V> topologicalSort = new ArrayList<V>();
        Stack<V> reverseTopologicalSort = getReverseTopologicalSort(graph);
        while (!reverseTopologicalSort.isEmpty()) {
            topologicalSort.add(reverseTopologicalSort.pop());
        }
        return topologicalSort;
    }

    static <V> Stack<V> getReverseTopologicalSort(Graph<V> graph) {
        Set<V> visited = new HashSet<>();
        Stack<V> stack = new Stack<>();
        for (V vertex : graph.getVertices()) {
            if (!visited.contains(vertex))
                depthFirstSearch(graph, visited, vertex, stack);
        }
        // destination is pushed first, source is pushed later
        // hence stack contains vertices in reverse topological order
        return stack;

    }

    static <V> void depthFirstSearch(Graph<V> graph, Set<V> visited, V start, Stack<V> topologicalSort) {
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
            if (shouldPop) {
                topologicalSort.push(stack.pop());
            }
        }

    }

    static <V> void visit(Stack<V> stack, Set<V> visited, V vertex) {
        stack.push(vertex);
        visited.add(vertex);
    }
}
