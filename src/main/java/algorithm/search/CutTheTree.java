package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/cut-the-tree/
 *
 * There is an undirected tree
 * where each vertex is numbered from 1 to n, and each contains a data value.
 * The sum of a tree is the sum of all its nodes' data values.
 * If an edge is cut, two smaller trees are formed.
 * The difference between two trees is the absolute value of the difference in their sums.
 *
 * Given a tree, determine which edge to cut so that the resulting trees
 * have a minimal difference between them, then return that difference.
 *
 *
 * Example
 * data = [1, 2, 3, 4, 5, 6]
 * edges = [(1, 2), (1, 3), (2, 6), (3, 4), (3, 5)]
 *
 * In this case, node numbers match their weights for convenience.
 * The graph is shown below.
 *
 *                     1
 *                  /    \
 *                2       3
 *               /      /  \
 *              6      4   5
 *
 * The values are calculated as follows:
 *
 * Edge    Tree 1  Tree 2  Absolute
 * Cut     Sum      Sum     Difference
 * 1-2       8         13         5
 * 1-3       9         12         3
 * 2-6       6         15         9
 * 3-4       4         17        13
 * 3-5       5         16        11
 * The minimum absolute difference is 3.
 *
 * Note: The given tree is always rooted at vertex 1.
 *
 * Constraints:
 * 3 <= n <= 10^5
 * 1 <= data <= 1001
 *
 * =====================
 * Solution
 * - take any node as the root and traverse from there
 * - 1 DFS to calculate a node's children's weight
 * It is observed that if the tree is cut such that this node and its children form a subtree,
 * the weight of this subtree is sum of its children's cumulative weight and the node's weight itself
 *
 * For example, with the tree above, rooted at node 1
 * The number in bracket is the weight of the subtree rooted at a node
 *                   1(21)
 *                 /      \
 *                2(8)    3(12)
 *               /      /     \
 *              6(6)   4(4)   5(5)
 *
 * If we make the cut edge 1-3, sub-tree (3, 4, 5) has weight 12 -> 2 subtrees' weights are 12 and 9
 *
 * - From the weight of the subtree, we can derive the weight difference between 2 formed subtrees as
 * |w1 - w2| = |w1 - (s-w1)| = |2*w1 - s|
 * where s is the sum of all nodes' weight
 * - It takes another DFS to find out which subtree can incur minimum weight difference
 * - Note that this works no matter which original root tree we take
 *
 * For example, if the tree above is rooted at 4
 *                   4 (21)
 *                   |
 *                   3 (17)
 *                /    \
 *               1(9)  5(5)
 *               |
 *               2(8)
 *               |
 *               6(6)
 *
 * If we make the cut edge 1-3, sub-tree (1, 2, 6) has weight 9 -> 2 subtrees' weights are 9 and 12
 *
 */
public class CutTheTree {
    static int cutTheTree(List<Integer> data, Map<Integer, List<Integer>> edges) {
        return getMinDiff(data, edges, getCumulativeWeight(data, edges));
    }

    static int[] getCumulativeWeight(List<Integer> data, Map<Integer, List<Integer>> edges) {
        int[] cumulativeWeight = new int[data.size()+1];
        int node;

        // standard dfs
        boolean[] visited = new boolean[data.size()+1];
        Stack<Integer> stack = new Stack<>();

        visited[1] = true;
        stack.push(1);

        while (!stack.isEmpty()) {
            node = stack.peek();
            boolean pushed = false;
            for (int adjacent : edges.get(node)) {
                if (!visited[adjacent]) {
                    stack.push(adjacent);
                    visited[adjacent] = true;
                    pushed = true;
                    break;
                }
            }
            if (!pushed) {
                node = stack.pop();
                int parent = -1; // for root node (no parent)
                if (!stack.isEmpty()) {
                    parent = stack.peek();
                }

                cumulativeWeight[node] += data.get(node-1);
                for (int adjacent : edges.get(node)) {
                    if (adjacent != parent) {
                        cumulativeWeight[node] += cumulativeWeight[adjacent];
                    }
                }
            }
        }

        return cumulativeWeight;
    }

    static int getMinDiff(List<Integer> data, Map<Integer, List<Integer>> edges, int[] cumulativeWeight) {
        int sum = data.stream().reduce((a, b)->a+b).get();
        int node, minDiff = Integer.MAX_VALUE;

        //standard dfs
        boolean[] visited = new boolean[data.size()+1];
        Stack<Integer> stack = new Stack<>();

        visited[1] = true;
        stack.push(1);

        while (!stack.isEmpty()) {
            node = stack.peek();

            /**
             * tweak here:
             * calculate the weight diff if the tree is cut such that
             * the sub-tree formed by this node & its children is one of the 2 subtrees
             */
            minDiff = Math.min(minDiff, getDiff(sum, cumulativeWeight[node]));

            boolean pushed = false;
            for (int adjacent : edges.get(node)) {
                if (!visited[adjacent]) {
                    stack.push(adjacent);
                    visited[adjacent] = true;
                    pushed = true;
                    break;
                }
            }
            if (!pushed) {
                stack.pop();
            }
        }

        return minDiff;
    }

    static int getDiff(int sum, int weight) {
        return Math.abs(2*weight - sum);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> data = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Map<Integer, List<Integer>> edges = new HashMap<>(n);

        for (int i = 0; i < n-1; i++) {
            try {
                List<Integer> edge = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                         .map(Integer::parseInt).collect(Collectors.toList());
                int first = edge.get(0);
                int second = edge.get(1);
                edges.computeIfAbsent(first, k -> new ArrayList<>());
                edges.get(first).add(second);
                edges.computeIfAbsent(second, k -> new ArrayList<>());
                edges.get(second).add(first);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int result = cutTheTree(data, edges);

        bufferedReader.close();

        System.out.println(result);
    }
}
