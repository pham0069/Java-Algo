package data_structure.tree.traversal;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.geeksforgeeks.org/level-order-tree-traversal/
 * similar like BFS
 */
public class LevelOrderTraversal {
    public static void main(String[] args) {
        Node root = Node.createTestTree();
        printLevelOrder(root);
    }

    static void printLevelOrder(Node root) {
        if (root == null)
            return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            System.out.print(node.key + " ");
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
        System.out.println();
    }
}
