package graph.maxFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/dinics-algorithm-maximum-flow/?ref=rp
 */
public class DinicAlgorithm {

    static int maxFlow(int[][] capacity, int source, int sink) {

        //store residual capacity
        int[][] residualCapacity = new int[capacity.length][capacity[0].length];
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity[0].length; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        // track max flow
        int maxFlow = 0;
        int[] level = new int[capacity.length];

        while(bfs(residualCapacity, level, source, sink)) {
            List<List<Integer>> augmentedPaths = dfs(residualCapacity, level, source, sink);

            for (List<Integer> augmentedPath : augmentedPaths) {
                int flow = getFlow(residualCapacity, augmentedPath);
                maxFlow += flow;

                updateResidualCapacity(residualCapacity, augmentedPath, flow);
            }

            printAugmentedPaths(augmentedPaths);
        }

        return maxFlow;
    }

    static int getFlow(int[][] residualCapacity, List<Integer> augmentedPath) {
        int prev, cur = augmentedPath.get(0);
        int flow = Integer.MAX_VALUE;
        for (int i = 1; i < augmentedPath.size(); i++) {
            prev = cur;
            cur = augmentedPath.get(i);
            flow = Math.min(flow, residualCapacity[prev][cur]);
        }
        return flow;
    }

    static void updateResidualCapacity(int[][] residualCapacity, List<Integer> augmentedPath, int flow) {
        int from, to;
        for (int i = 0; i < augmentedPath.size()-1; i++) {
            from = augmentedPath.get(i);
            to = augmentedPath.get(i+1);
            residualCapacity[from][to] -= flow;
            residualCapacity[to][from] += flow;
        }
    }

    static void printAugmentedPaths(List<List<Integer>> augmentedPaths) {
        System.out.println("Augmented paths");
        augmentedPaths.forEach(path -> {
            path.forEach(i -> System.out.print(i + " "));
            System.out.println();
        });
    }

    static boolean bfs(int[][] residualCapacity, int[] level, int source, int sink) {
        Arrays.fill(level, -1);
        Queue<Integer> queue = new LinkedList<>();

        level[source] = 0;// level is used to mark visited, i.e. -1 means unvisited
        queue.add(source);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < residualCapacity.length; v++) {
                if (level[v] == -1 && residualCapacity[u][v] > 0) {
                    level[v] = level[u] + 1;
                    queue.add(v);
                }
            }
        }

        return level[sink] != -1; // found augmented path
    }

    static List<List<Integer>> dfs(int[][] residualCapacity, int[] level, int source, int sink) {
        List<List<Integer>> result = new ArrayList<>();

        boolean[][] used = new boolean[residualCapacity.length][residualCapacity[0].length];
        List<Integer> augmentedPath;

        while ((augmentedPath = dfs(used, residualCapacity, level, source, sink)) != null) {
            result.add(augmentedPath);
        }

        return result;
    }

    static List<Integer> dfs(boolean[][] used, int[][] residualCapacity, int[] level, int source, int sink) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        int u, v;

        stack.add(source);
        visited.add(source);

        while (!stack.isEmpty()) {
            u = stack.peek();
            boolean found = false;
            for (v = 0; v < residualCapacity.length; v++) {
                // add next vertex
                if (!visited.contains(v)
                        && !used[u][v]
                        && level[v] == level[u]+1
                        && residualCapacity[u][v] > 0) {
                    stack.add(v);
                    visited.add(v);
                    found = true;
                    break;
                }
            }

            if (!found) {
                stack.pop();
            } else {
                // return the path by popping all elements from the stack
                if (v == sink) {
                    List<Integer> augmentedPath = new ArrayList<>();
                    while (!stack.isEmpty()) {
                        augmentedPath.add(stack.pop());
                    }
                    // convert from sink-source -> source-sink
                    Collections.reverse(augmentedPath);

                    // update used matrix
                    for (int i = 0; i < augmentedPath.size()-1; i++) {
                        used[augmentedPath.get(i)][augmentedPath.get(i+1)] = true;
                    }
                    return augmentedPath;
                }
            }
        }

        return null;
    }

    public static void main(String args[]){
        /*
        int[][] capacity = {
                {0, 3, 0, 3, 0, 0, 0},
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
