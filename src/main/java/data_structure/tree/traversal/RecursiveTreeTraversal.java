package data_structure.tree.traversal;

/**
 * https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
 *
 * Given a tree
 *          1
 *         / \
 *        2   3
 *       / \
 *      4   5
 *
 * 3 types of traverse
 * 1. in-order (left, root, right): 4 2 5 1 3
 * 2. pre-order (root, left, right):1 2 4 5 3
 * 3. post-order (left, right, root): 4 5 2 3 1
 *
 * Time complexity is O(n)
 */
public class RecursiveTreeTraversal {
    public static void main(String[] args) {
        Node root = Node.createTestTree();
        printInOrder(root);
        System.out.println();
        printPreOrder(root);
        System.out.println();
        printPostOrder(root);
    }

    static void printInOrder(Node node){
        if (node == null)
            return;

        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }

    static void printPreOrder(Node node){
        if (node == null)
            return;

        System.out.print(node.key + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);
    }

    static void printPostOrder(Node node) {
        if (node == null)
            return;

        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(node.key + " ");
    }
}
