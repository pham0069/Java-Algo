package data_structure.tree.bst;

import data_structure.tree.Node;

public class KthSmallestValueBST {
    public static Integer kthMinValue(Node node, int k) {
        if (node == null) {
            return null;
        }
        Node cur = node;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }
}
