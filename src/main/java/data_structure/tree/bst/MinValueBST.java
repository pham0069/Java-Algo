package data_structure.tree.bst;

import data_structure.tree.Node;

public class MinValueBST {
    public static Integer minValue(Node node) {
        if (node == null) {
            return null;
        }
        Node cur = node;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    public static Integer maxValue(Node node) {
        if (node == null) {
            return null;
        }
        Node cur = node;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }
}
