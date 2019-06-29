package data_structure.tree.binary_tree;

import data_structure.tree.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/check-binary-tree-not-bst-duplicate-values/?ref=rp
 *
 * Traverse and store value in the set
 */
public class HasDuplicateValue {
    public static void main(String[] args) {
        System.out.println(hasDuplicateValue(Node.createTestTree2()));
        System.out.println(hasDuplicateValue(Node.createTestTree3()));
    }
    static boolean hasDuplicateValue(Node root) {
        Set<Integer> values = new HashSet<>();
        return traverseAndCheck(root, values);
    }

    static boolean traverseAndCheck(Node node, Set<Integer> values) {
        if (values.contains(node.key)) {
            return true;
        }
        values.add(node.key);

        if (node.left != null && traverseAndCheck(node.left, values)) {
            return true;
        }
        if (node.right != null && traverseAndCheck(node.right, values)) {
            return true;
        }
        return false;
    }
}
