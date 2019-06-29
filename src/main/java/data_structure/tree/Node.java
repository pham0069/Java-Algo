package data_structure.tree;

public class Node {
    public int key;
    public Node left, right;
    public Node(int key) {
        this.key = key;
        this.left = this.right = null;
    }
    public void setLeft(Node left) {
        this.left = left;
    }
    public void setRight(Node right) {
        this.right = right;
    }

    /**
     *         1
     *        / \
     *       2   3
     *      /\
     *     4  5
     */
    public static Node createTestTree() {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        return root;
    }

    /**
     *         1
     *        / \
     *       2   3
     *      / \
     *     4   5
     *     \  /
     *     6 7
     */
    public static Node createTestTree2() {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.left.right = new Node(6);
        root.left.right = new Node(5);
        root.left.right.left = new Node(7);
        return root;
    }

    /**
     *         1
     *        / \
     *       2   3
     *      / \
     *     4   5
     *     \  /
     *     6 3
     */
    public static Node createTestTree3() {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.left.right = new Node(6);
        root.left.right = new Node(5);
        root.left.right.left = new Node(3);
        return root;
    }
}
