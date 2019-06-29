package data_structure.tree.traversal;

import java.util.Stack;

/**
 * Morris traversal:
 * Traverse tree in-order without using recursion or stack
 * We first create links to Inorder successor and print the data using these links,
 * and finally revert the changes to restore original tree.
 *
 *
 */
public class IterativeInOrderTraversal {
    public static void main(String[] args) {
        Node root = Node.createTestTree2();
        traverseWithStack(root);
        morrisTraverse(root);
    }

    static void traverseWithStack(Node root){
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            System.out.print(node.key + " ");
            node = node.right;
        }
        System.out.println();
    }

    static void morrisTraverse(Node root) {
        if (root == null)
            return;

        Node current = root, previous;
        while (current != null) {
            if (current.left == null) {
                System.out.print(current.key + " ");
                current = current.right;
            } else {
                previous = current.left;
                /* Find the inorder predecessor of current */
                while (previous.right != null && previous.right != current) {
                    previous = previous.right;
                }

                /* Make current as right child of its inorder predecessor */
                if (previous.right == null) {
                    previous.right = current;
                    current = current.left;
                }
                /* Revert the changes made in the 'if' part to restore the
                    original tree i.e., fix the right child of predecessor*/
                else {
                    previous.right = null;
                    System.out.print(current.key + " ");
                    current = current.right;
                }
            }

        }
    }
}
