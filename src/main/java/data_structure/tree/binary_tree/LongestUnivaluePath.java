package data_structure.tree.binary_tree;

import data_structure.tree.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.codingninjas.com/codestudio/problems/longest-univalue-path_985291?topList=top-amazon-coding-interview-questions
 *
 *
 */
public class LongestUnivaluePath {
    private static final Map<Integer, Integer> map = new HashMap<>();
    private static final Counter counter = new Counter();

    public static void main(String[] args) {
        System.out.println(getLongestUnivaluePath(Node.createTestTree4()));
        System.out.print(counter.count);
    }

    public static int getLongestUnivaluePath(Node root) {
        if (root == null)  {
            return 0;
        }

        int maxPath = 0;
        maxPath =  Math.max(maxPath, getRootedLongestUnivaluePath(root));
        maxPath = Math.max(maxPath, getLongestUnivaluePath(root.left));
        maxPath = Math.max(maxPath, getLongestUnivaluePath(root.right));

        return maxPath;
    }

    public static int getRootedLongestUnivaluePath(Node root) {
        if (map.containsKey(root.id)) {
            return map.get(root.id);
        }

        counter.increment();
        int maxLeft = 0, maxRight = 0;
        if (root.left != null) {
            if (root.left.key == root.key) {
                maxLeft = 1 + getRootedLongestUnivaluePath(root.left);
            }
        }
        if (root.right != null) {
            if (root.right.key == root.key) {
                maxRight = 1 + getRootedLongestUnivaluePath(root.right);
            }
        }

        int max = maxLeft + maxRight;

        // store calculated value in map
        map.put(root.id, max);
        return max;
    }

    public static class Counter {
        private int count = 0;
        void increment() {
            count ++;
        }

        void clear() {
            count = 0;
        }
    }
}
