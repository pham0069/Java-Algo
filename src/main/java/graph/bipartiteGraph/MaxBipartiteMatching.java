package graph.bipartiteGraph;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/maximum-bipartite-matching/?ref=rp
 *
 * A matching in a Bipartite Graph is a set of the edges chosen in such a way
 * that no two edges share an endpoint.
 *
 * A maximum matching is a matching of maximum size (maximum number of edges).
 * In a maximum matching, if any edge is added to it, it is no longer a matching. T
 * here can be more than one maximum matchings for a given Bipartite Graph.
 *
 *
 * Why do we care?
 * There are many real world problems that can be formed as Bipartite Matching.
 * For example, consider the following problem:
 * - There are M job applicants and N jobs.
 * Each applicant has a subset of jobs that he/she is interested in.
 * Each job opening can only accept one applicant and a job applicant can be appointed for only one job.
 * - Find an assignment of jobs to applicants in such that as many applicants as possible get jobs.
 *
 * MBP can be solved by converting this problem into max-flow problem, then use Ford-Fulkerson algo on it
 *
 */
public class MaxBipartiteMatching {
    static class BipartiteGraph {
        List<Integer> red = new ArrayList<>();
        List<Integer> blue = new ArrayList<>();
        Multimap<Integer, Integer> edges = HashMultimap.create();

        void addRed(Integer ... vertices) {
            red.addAll(Lists.newArrayList(vertices));
        }

        void addBlue(Integer ... vertices) {
            blue.addAll(Lists.newArrayList(vertices));
        }

        void addEdge(int red, int blue) {
            edges.put(red, blue);
        }
    }

    @Data
    static class Pair {
        final int from;
        final int to;
    }

    static class WeightedGraph {
        List<Integer> vertices = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> edges = new HashMap<>();

        void addVertices(Integer ... vertices) {
            this.vertices.addAll(Lists.newArrayList(vertices));
        }

        void addVertices(List<Integer> vertices) {
            this.vertices.addAll(vertices);
        }

        void addEdge(int from, int to, int weight) {
            edges.computeIfAbsent(from, k -> new HashMap<>());
            edges.get(from).put(to, weight);
        }

        void updateWeight(int from, int to, int increment) {
            edges.computeIfAbsent(from, k -> new HashMap<>());
            int currentWeight = edges.get(from).getOrDefault(to, 0);
            edges.get(from).put(to, currentWeight + increment);
        }
    }

    static final int SOURCE = -1;
    static final int SINK = -2;

    static int getMaxBipartiteMatching(BipartiteGraph bipartiteGraph) {
        WeightedGraph graph = toMaxFlowGraph(bipartiteGraph);
        return getMaxFlowForBipartiteGraph(graph);
    }

    static int getMaxFlowForBipartiteGraph(WeightedGraph graph) {
        List<Integer> augmentedPath;
        Set<Pair> forward = new HashSet<>();
        Set<Pair> reverse = new HashSet<>();
        int maxFlow = 0, u, v;

        while ((augmentedPath = bfs(graph)) != null) {
            // each path's flow is 1 (for sure)
            maxFlow ++;

            for (int i = 0; i < augmentedPath.size()-1; i++) {
                u = augmentedPath.get(i);
                v = augmentedPath.get(i+1);
                graph.updateWeight(u, v, -1);
                graph.updateWeight(v, u, 1);
                if (i != 0 && i != augmentedPath.size()-2) {//not an edge from source or to sink
                    Pair forwardPair = new Pair(u, v);
                    Pair reversePair = new Pair(v, u);

                    if (reverse.contains(forwardPair)) { // cancel the edge
                        forward.remove(reversePair);
                        reverse.remove(forwardPair);
                    } else { // add the edge
                        forward.add(forwardPair);
                        reverse.add(reversePair);
                    }
                }
            }
        }

        System.out.println(forward);

        return maxFlow;
    }

    static List<Integer> bfs(WeightedGraph graph) {
        Map<Integer, Integer> parent = new HashMap<>();
        Deque<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(SOURCE);
        visited.add(SOURCE);

        while (!queue.isEmpty()) {
           int u = queue.remove();
           if (!graph.edges.containsKey(u)) {
               continue;
           }
           for (Map.Entry<Integer, Integer> entry : graph.edges.get(u).entrySet()) {
               int v = entry.getKey();
               if (entry.getValue() > 0 && !visited.contains(v)) {
                   queue.add(v);
                   parent.put(v, u);

                   if (v == SINK) {
                       //extract augmented path from parent map
                       List<Integer> augmentedPath = new ArrayList<>();
                       int cur = SINK;
                       while (cur != SOURCE) {
                           augmentedPath.add(cur);
                           cur = parent.get(cur);
                       }
                       augmentedPath.add(SOURCE);
                       Collections.reverse(augmentedPath);
                       return augmentedPath;
                   }
               }
           }
        }

        return null;
    }

    static WeightedGraph toMaxFlowGraph(BipartiteGraph graph) {
        WeightedGraph result = new WeightedGraph();

        result.addVertices(SOURCE, SINK);
        result.addVertices(graph.red);
        result.addVertices(graph.blue);

        // connect source to all reds
        for (int r : graph.red) {
            result.addEdge(SOURCE, r, 1);
        }
        // connect all blues to sink
        for (int b : graph.blue) {
            result.addEdge(b, SINK, 1);
        }
        // connect reds to blues as in bipartite graph
        for (Map.Entry<Integer, Integer> entry : graph.edges.entries()) {
            result.addEdge(entry.getKey(), entry.getValue(), 1);
        }
        return result;
    }

    static BipartiteGraph test1() {
        BipartiteGraph graph = new BipartiteGraph();
        graph.addRed(1, 2, 3, 4, 5, 6);
        graph.addBlue(7, 8, 9, 10, 11, 12);
        graph.addEdge(1, 9);
        graph.addEdge(1, 10);
        graph.addEdge(3, 7);
        graph.addEdge(3, 10);
        graph.addEdge(4, 9);
        graph.addEdge(5, 9);
        graph.addEdge(5, 10);
        graph.addEdge(6, 12);
        return graph;
    }

    static BipartiteGraph test2() {
        BipartiteGraph graph = new BipartiteGraph();
        graph.addRed(0, 1, 2, 3);
        graph.addBlue(4, 5, 6, 7, 8, 9);
        graph.addEdge(0, 5);
        graph.addEdge(0, 6);
        graph.addEdge(0, 8);
        graph.addEdge(1, 6);
        graph.addEdge(1, 7);
        graph.addEdge(1, 8);
        graph.addEdge(1, 9);
        graph.addEdge(2, 6);
        graph.addEdge(2, 9);
        graph.addEdge(3, 7);
        return graph;
    }

    public static void main(String[] args) {
        System.out.println(getMaxBipartiteMatching(test2()));
    }
}
