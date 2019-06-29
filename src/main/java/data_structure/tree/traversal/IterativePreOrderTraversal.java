package data_structure.tree.traversal;

import java.util.Stack;

public class IterativePreOrderTraversal {
    public static void main(String[] args) {
        Node root = Node.createTestTree2();
        traverseWithStack(root);
    }


    /**
     * Straightforwards since recursive version is tailed-recursive
     */
    static void traverseWithStack(Node root){
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.key + " ");
            //Push right first since it is to be processed later than left
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
        System.out.println();
    }
}
