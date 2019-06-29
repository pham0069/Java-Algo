package data_structure.tree.traversal;

import java.util.LinkedList;
import java.util.Queue;

public class TreeHeight {
    public static void main(String[] args) {
        Node root = Node.createTestTree2();
        System.out.println(iterativeGetHeight(root));
    }
    static int getHeight(Node root) {
        if (root == null)
            return 0;
        int lHeight = 0, rHeight = 0;
        if (root.left != null)
            lHeight = getHeight(root.left);
        if (root.right != null)
            rHeight = getHeight(root.right);
        return Math.max(lHeight, rHeight) + 1;
    }

    static int iterativeGetHeight(Node root) {
        if (root == null)
            return 0;
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 1));
        int maxLevel = 1;
        while (!queue.isEmpty()) {
            Pair pair = queue.remove();
            Node node = pair.node;
            int level = pair.level;
            if (node.left != null) {
                queue.add(new Pair(node.left, level+1));
                maxLevel = Math.max(maxLevel, level+1);
            }
            if (node.right != null) {
                queue.add(new Pair(node.right, level+1));
                maxLevel = Math.max(maxLevel, level+1);
            }
        }
        return maxLevel;
    }
}
