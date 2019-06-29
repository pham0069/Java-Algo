package graph.shortestPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/?ref=rp
 * https://www.youtube.com/watch?v=-mOEd_3gTK0
 *
 */
public class BellmanFordAlgorithm {
    static class Edge {
        int from;
        int to;
        int weight;
        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    static class Graph {
        List<Integer> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        void addVertices(int ... allVertices) {
            for (int v : allVertices) {
                vertices.add(v);
            }
        }
        void addEdge(int from, int to, int weight) {
            edges.add(new Edge(from, to, weight));
        }
    }

    static void getShortestPath(Graph graph, int source) {
        Map<Integer, Integer> shortestPath = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        shortestPath.put(source, 0);

        boolean updated = true;
        for (int i = 0; i <= graph.vertices.size(); i++) {
            updated = updateShortestPath(graph, shortestPath, parent);
            if (!updated) {
                break;
            }
        }

        if (updated) {
            System.out.println("Has negative weight cycle");
            return;
        }

        System.out.println(String.format("Source %d: ", source));
        for (int v : graph.vertices) {
            if (shortestPath.containsKey(v)) {
                System.out.println(String.format("Node %d: shortest path %d", v, shortestPath.get(v)));
                printShortestPath(parent, source, v);
            } else {
                System.out.println(String.format("Node %d: no path", v));
            }
        }
    }

    static void printShortestPath(Map<Integer, Integer> parent, int source, int dest) {
        List<Integer> path = new ArrayList<>();
        path.add(dest);
        Integer cur = dest;

        while (true) {
            cur = parent.get(cur);
            if (cur == null) {
                break;
            }
            path.add(cur);
        }
        Collections.reverse(path);
        System.out.println(path);
    }

    static boolean updateShortestPath(Graph graph, Map<Integer, Integer> shortestPath, Map<Integer, Integer> parent) {
        boolean updated = false;
        int u, v, w;
        for (Edge edge : graph.edges) {
            u = edge.from;
            v = edge.to;
            if (shortestPath.containsKey(u)) {
                w = shortestPath.get(u) + edge.weight;
                if (! shortestPath.containsKey(v) || shortestPath.get(v) > w) {
                    shortestPath.put(v, w);
                    parent.put(v, u);
                    updated = true;
                }
            }
        }
        return updated;
    }

    static Graph test1() {
        Graph graph = new Graph();
        graph.addVertices(0, 1, 2, 3, 4);
        graph.addEdge(0, 3, 8);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, -3);
        graph.addEdge(2, 4, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 3, 1);
        return graph;
    }

    // negative weight cycle
    static Graph test2() {
        Graph graph = new Graph();
        graph.addVertices(0, 1, 2, 3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 1, -6);
        return graph;
    }

    public static void main(String[] args) {
        Graph graph = test2();

        for (int v : graph.vertices) {
            getShortestPath(graph, v);
        }
    }

}
