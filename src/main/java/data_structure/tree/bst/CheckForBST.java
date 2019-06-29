package data_structure.tree.bst;

import data_structure.tree.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.geeksforgeeks.org/a-program-to-check-if-a-binary-tree-is-bst-or-not/
 *
 * Given a binary tree (of distinct values), check if it is valid binary search tree ?
 */
public class CheckForBST {

    /**
     * Simple but wrong:
     * For each node, check if the left node of it is smaller than the node and right node of it is greater than the node.
     *
     * For example:
     *       3
     *      / \
     *     2   4
     *    / \
     *   1   5
     *
     *  Right-most node of 3-rooted tree (5) > right-node of 3(4)
     */
    static boolean isBST2(Node node) {
        if (node == null)
            return true;
        if (node.left != null && node.left.key > node.key)
            return false;
        if (node.right != null && node.right.key < node.key)
            return false;
        return isBST2(node.left) && isBST2(node.right);
    }

    //==================================================================
    /**
     * Simple but inefficient
     * since getMin() and getMax() are called multiple times
     * to traverse all kinds of sub-trees
     */
    static boolean isBST3(Node node) {
        if (node == null)
            return true;
        if (node.left != null && getMax(node.left) > node.key)
            return false;
        if (node.right != null && getMin(node.right) < node.key)
            return false;
        return isBST3(node.left) && isBST3(node.right);
    }

    static int getMin(Node node) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        int min = node.key;
        while (!queue.isEmpty()) {
            Node head = queue.remove();
            min = Math.min(head.key, min);
            if (head.left != null)
                queue.add(head.left);
            if (head.right != null)
                queue.add(head.right);
        }
        return min;
    }

    static int getMax(Node node) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        int max = node.key;
        while (!queue.isEmpty()) {
            Node head = queue.remove();
            max = Math.max(head.key, max);
            if (head.left != null)
                queue.add(head.left);
            if (head.right != null)
                queue.add(head.right);
        }
        return max;
    }

    //==================================================================
    static boolean isBST(Node node) {
        return isBST(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    static boolean isBST(Node node, int min, int max) {
        if (node == null)
            return true;

        // false if this node violates the min/max constraints
        if (node.key < min || node.key > max)
            return false;

        // otherwise check the subtrees recursively tightening the min/max constraints
        // only applicable for tree with distinct values
        return (isBST(node.left, min, node.key-1) &&
                isBST(node.right, node.key+1, max));
    }


}
