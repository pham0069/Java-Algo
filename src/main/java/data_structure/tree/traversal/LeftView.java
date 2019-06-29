package data_structure.tree.traversal;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/print-left-view-binary-tree/
 *
 * BFS and keep track of each node level
 *
 * Way 1: do level order traversal and only prints the first node of each level
 * Way 2: do recursive traversal for each level
 *
 */
public class LeftView {
    public static void main(String[] args) {
        Node root = Node.createTestTree2();
        leftView(root);
        System.out.println();
        iterativeLeftView(root);
        iterativeLeftView2(root);
    }

    static void iterativeLeftView(Node root) {
        if (root == null)
            return;
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));
        Set<Integer> levels = new HashSet<>();
        while (!queue.isEmpty()) {
            Pair pair = queue.remove();
            Node node = pair.node;
            int level = pair.level;
            if (!levels.contains(level)) {
                levels.add(level);
                System.out.print(node.key + " ");
            }
            if (node.left != null) {
                queue.add(new Pair(node.left, level+1));
            }
            if (node.right != null) {
                queue.add(new Pair(node.right, level+1));
            }
        }
        System.out.println();
    }

    static void iterativeLeftView2(Node root) {
        if (root == null)
            return;
        Queue<Node> queue = new LinkedList<>();
        Node delimiter = new Node(0);
        queue.add(root);
        queue.add(delimiter);
        Node node;
        while (!queue.isEmpty()) {
            node = queue.remove();
            if (node == delimiter)
                break;
            System.out.print(node.key + " ");
            do {
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
                node = queue.remove();
            } while (node != delimiter);
            queue.add(delimiter);
        }
        System.out.println();
    }

    static int maxLevel = 0;
    static void leftView(Node root) {
        leftView(root, 1);
    }

    static void leftView(Node node, int level) {
        if (node == null)
            return;
        if (level > maxLevel) {
            System.out.print(node.key + " ");
            maxLevel = level;
        }
        leftView(node.left, level+1);
        leftView(node.right, level+1);
    }
}
