package graph.bipartiteGraph;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * https://www.geeksforgeeks.org/bipartite-graph/
 * https://www.geeksforgeeks.org/check-if-a-given-graph-is-bipartite-using-dfs/?ref=rp
 *
 * A Bipartite Graph is a graph whose vertices can be divided into two independent sets,
 * U and V such that every edge (u, v) either connects a vertex from U to V or a vertex from V to U.
 * In other words, for every edge (u, v), either u belongs to U and v to V, or u belongs to V and v to U.
 * We can also say that there is no edge that connects vertices of same set.
 *
 * A bipartite graph is possible if the graph coloring is possible using two colors
 * such that vertices in a set are colored with the same color.
 * Note that it is possible to color a cycle graph with even cycle using two colors.
 * For example, the following graph can be dissected into 2 sets: red (R1, R2, R3) and blue (B1, B2, B3)
 *
 *    R1 --- B1
 *   /         \
 *  B2          R3
 *   \         /
 *   R2 --- B3
 *
 * It is not possible to color a cycle graph with odd cycle using two colors
 * R2-R3 is an edge with same color on both vertices
 *
 *           R1
 *        /     \
 *       B1     B2
 *       |      |
 *       R2 --- R3
 *
 *
 * Can use BFS or DFS to assign colors
 * Another method is backtracking for m-coloring problem
 * https://www.geeksforgeeks.org/m-coloring-problem-backtracking-5/
 */
public class IsBipartiteGraph {
    static final int BLUE = 0;
    static final int RED = 1;

    static class Graph {
        List<Integer> vertices = new ArrayList<>();
        Multimap<Integer, Integer> edges = HashMultimap.create();

        void addVertices(Integer... vertices) {
            this.vertices.addAll(Lists.newArrayList(vertices));
        }

        void addEdge(int from, int to) {
            edges.put(from, to);
            edges.put(to, from);
        }
    }

    // using BFS
    static boolean isBipartite(Graph graph) {
        if (graph.vertices.isEmpty()) {
            return true;
        }

        Map<Integer, Integer> colors = new HashMap<>();

        for (int source : graph.vertices) {
            boolean visited = colors.containsKey(source);
            if (!visited) {
                if (!dfs(graph, colors, source)) {
                    return false;
                }
            }
        }

        printBipartite(colors);

        return true;
    }

    static void printBipartite(Map<Integer, Integer> colors) {
        List<Integer> red = colors.entrySet().stream()
                .filter(e -> e.getValue() == RED)
                .map(Map.Entry::getKey).collect(Collectors.toList());
        System.out.println("Red:" + red);
        List<Integer> blue = colors.entrySet().stream()
                .filter(e -> e.getValue() != RED)
                .map(Map.Entry::getKey).collect(Collectors.toList());
        System.out.println("Blue:" + blue);
    }

    static boolean bfs(Graph graph, Map<Integer, Integer> colors, int source) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        colors.put(source, RED);

        while (!queue.isEmpty()) {
            int u = queue.remove();
            int uColor = colors.get(u);
            for (int v : graph.edges.get(u)) {
                boolean visited = colors.containsKey(v);
                if (visited) {
                    if (uColor == colors.get(v)) {
                        return false;
                    }
                } else {
                    queue.add(v);
                    colors.put(v, 1 - uColor);
                }
            }
        }

        return true;
    }

    static boolean dfs(Graph graph, Map<Integer, Integer> colors, int source) {
        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        colors.put(source, RED);

        while (!stack.isEmpty()) {
            int u = stack.peek();
            int uColor = colors.get(u);
            boolean found = false;
            for (int v : graph.edges.get(u)) {
                boolean visited = colors.containsKey(v);
                if (visited) {
                    if (uColor == colors.get(v)) {
                        return false;
                    }
                } else {
                    stack.push(v);
                    colors.put(v, 1 - uColor);
                    found = true;
                    break;
                }
            }

            if (!found) {
                stack.pop();
            }
        }

        return true;
    }

    public static void main (String[] args) {
        System.out.println(isBipartite(test1()));
        System.out.println(isBipartite(test2()));
        System.out.println(isBipartite(test3()));
        System.out.println(isBipartite(test4()));
    }

    static Graph test1() {
        Graph graph = new Graph();
        graph.addVertices(0, 1, 2, 3);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        return graph;
    }

    static Graph test2() {
        Graph graph = new Graph();
        graph.addVertices(0, 1, 2, 3);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(0, 2);
        return graph;
    }

    static Graph test3() {
        Graph graph = new Graph();
        graph.addVertices(0, 1, 2, 3, 4, 5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);
        return graph;
    }

    static Graph test4() {
        Graph graph = new Graph();
        graph.addVertices(0, 1, 2, 3, 4, 5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);
        graph.addEdge(0, 4);
        return graph;
    }
}
