package graph.maxFlow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * FordFulkerson - Edmonds-Karp algo using BFS
 */
public class FordFulkersonAlgorithm {
    static int maxFlow(int[][] capacity, int source, int sink) {

        //store residual capacity
        int[][] residualCapacity = new int[capacity.length][capacity[0].length];
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity[0].length; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        // map BFS child to parent
        Map<Integer, Integer> childToParent = new HashMap<>();

        // store all augmented paths
        List<List<Integer>> augmentedPaths = new ArrayList<>();

        // track max flow
        int maxFlow = 0;

        while(bfs(residualCapacity, childToParent, source, sink)) {
            List<Integer> augmentedPath = new ArrayList<>();
            int flow = Integer.MAX_VALUE;
            int v = sink;

            // get augmented path and min residual capacity on the path
            while (v != source) {
                augmentedPath.add(v);
                int u = childToParent.get(v);
                flow = Math.min(flow, residualCapacity[u][v]);
                v = u;
            }
            augmentedPath.add(source);

            // update list of augmented paths
            Collections.reverse(augmentedPath);
            augmentedPaths.add(augmentedPath);
            // update max flow
            maxFlow += flow;
            System.out.println(flow);

            // update residual capacity
            v = sink;
            while (v != source) {
                int u = childToParent.get(v);
                residualCapacity[u][v] -= flow; // forward edge
                residualCapacity[v][u] += flow; // reverse edge
                v = u;
            }
        }

        printAugmentedPaths(augmentedPaths);
        return maxFlow;
    }

    static void printAugmentedPaths(List<List<Integer>> augmentedPaths) {
        System.out.println("Augmented paths");
        augmentedPaths.forEach(path -> {
            path.forEach(i -> System.out.print(i + " "));
            System.out.println();
        });
    }

    static boolean bfs(int[][] residualCapacity, Map<Integer, Integer> childToParent, int source, int sink) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean foundAugmentedPath = false;

        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < residualCapacity.length; v++) {
                if (!visited.contains(v) && residualCapacity[u][v] > 0) {
                    childToParent.put(v, u);
                    visited.add(v);
                    queue.add(v);
                    if (v == sink) {
                        foundAugmentedPath = true;
                        break;
                    }
                }
            }
        }

        return foundAugmentedPath;
    }

    public static void main(String args[]){
        /*
        int[][] capacity = {{0, 3, 0, 3, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 0},
                {3, 0, 0, 1, 2, 0, 0},
                {0, 0, 0, 0, 2, 6, 0},
                {0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0}};

        System.out.println("\nMaximum capacity " + maxFlow(capacity, 0, 6));

         */
        int[][] capacity = {
                {0, 10, 10, 0, 0, 0},
                {0, 0, 2, 4, 8, 0},
                {0, 0, 0, 0, 9, 0},
                {0, 0, 0, 0, 0, 10},
                {0, 0, 0, 6, 0, 10},
                {0, 0, 0, 0, 0, 0},
        };

        System.out.println("\nMaximum capacity " + maxFlow(capacity, 0, 5));
    }
}
