package data_structure.tree.traversal;

import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/iterative-postorder-traversal/
 * https://www.geeksforgeeks.org/iterative-postorder-traversal-using-stack/
 * https://www.geeksforgeeks.org/iterative-postorder-traversal-set-3/
 *
 * Iterative postorder traversal is more complexed than the other two traversals
 * due to its nature of non-tail recursion, i.e. there is an extra statement after the final recursive call to itself
 *
 *
 *
 */
public class IterativePostOrderTraversal {
    public static void main(String[] args) {
        Node root = Node.createTestTree2();
        traverseWithOneStack(root);
        traverseWithTwoStacks(root);
        traverseWithOneStackAndMark(root);
    }

    /**
     * Observation: the reverse of post-order traversal is same as pre-order traversal with right before left
     * We can traverse in the right-pre-order way (using 1 stack as in iterativePreOrderTraversal)
     * During the traversal, we push the popped out nodes from this stack to another stack
     * This reverses the reversed right-pre-order, i.e. printing the popped out nodes from second stack
     */
    static void traverseWithTwoStacks(Node root) {
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        Stack<Node> reverse = new Stack<>();
        stack.push(root);
        Node node;
        while (! stack.isEmpty()) {
            node = stack.pop();
            reverse.push(node);
            //push left before right since right is processed first
            if (node.left != null)
                stack.push(node.left);
            if (node.right != null)
                stack.push(node.right);
        }
        while (!reverse.isEmpty()) {
            System.out.print(reverse.pop().key + " ");
        }
        System.out.println();
    }

    static void traverseWithOneStack(Node root){
        if (root == null)
            return;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                if (node.right != null)
                    stack.push(node.right);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            //if current node is a parent with non-null right child,
            // set next node to process to this right child
            // and push back the parent to the stack
            if (!stack.isEmpty() && node.right == stack.peek()) {
                Node temp = node;
                node = stack.pop();
                stack.push(temp);
            } else {
                // when reaching here, current node either does not have right child
                // or right child has already been processed
                System.out.print(node.key + " ");
                node = null;
            }
        }
        System.out.println();
    }

    static void traverseWithOneStackAndMark(Node root) {
        if (root == null)
            return;
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, Mark.NODE));
        while (!stack.isEmpty()) {
            Pair pair = stack.pop();
            Node node = pair.node;
            Mark mark = pair.mark;

            switch (mark) {
                case NODE:
                    stack.add(new Pair(node, Mark.LEFT));
                    if (node.left != null)
                        stack.add(new Pair(node.left, Mark.NODE));
                    break;
                case LEFT:
                    stack.add(new Pair(node, Mark.RIGHT));
                    if (node.right != null)
                        stack.add(new Pair(node.right, Mark.NODE));
                    break;
                case RIGHT:
                    System.out.print(node.key + " ");
            }
        }
        System.out.println();
    }

    static class Pair {
        Node node;
        Mark mark;
        Pair(Node node, Mark mark) {
            this.node = node;
            this.mark = mark;
        }
    }

    static enum Mark {
        LEFT,
        RIGHT,
        NODE
    }
}
