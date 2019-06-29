package data_structure.tree.traversal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
 *
 * Given inorder and preorder sequence of a tree
 * Reconstruct the tree and print its post order sequences
 */
public class TreeConstructionFromInOrderAndPreOrder {
    public static void main(String[] args) {
        //Node treeRoot = buildTree(new int[]{3, 2, 4, 1, 6, 5}, new int[]{1, 2, 3, 4, 5, 6});
        Node treeRoot = buildTree3(new int[]{3, 2, 4, 1, 6, 5}, new int[]{1, 2, 3, 4, 5, 6});
        RecursiveTreeTraversal.printPostOrder(treeRoot);
    }

    /**
     * Not space efficient since using Arrays copy utility
     * Not time efficiency since search is linear, could take up to O(n) per search,
     * making total time up to O(n^2)
     */
    static Node buildTree(int[] inOrder, int[] preOrder) {
        if (inOrder.length == 0)
            return null;
        Node root = new Node(preOrder[0]);
        int rootIndex = findIndex(inOrder, root.key);
        if (rootIndex == -1)
            return null;
        Node left = buildTree(Arrays.copyOfRange(inOrder, 0, rootIndex),
                Arrays.copyOfRange(preOrder, 1, rootIndex+1));
        Node right = buildTree(Arrays.copyOfRange(inOrder, rootIndex+1, inOrder.length),
                Arrays.copyOfRange(preOrder, rootIndex+1, preOrder.length));
        root.left = left;
        root.right = right;
        return root;
    }

    static Node buildTree2(int[] inOrder, int[] preOrder) {
        return buildTree2(inOrder, preOrder, 0, inOrder.length, 0, buildIndexMap(inOrder));
    }

    static Map<Integer, Integer> buildIndexMap(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put(array[i], i);
        }
        return map;
    }

    /**
     * Improve space efficiency using index to indicate the portion for processing
     * Improve time efficiency using map to search index
     */
    static Node buildTree2(int[] inOrder, int[] preOrder, int inStart, int inEnd, int preStart, Map<Integer, Integer> inOrderMap) {
        if (inStart >= inEnd)
            return null;
        Node root = new Node(preOrder[preStart]);
        // use map instead of linear search
        // int rootIndex = findIndex(inOrder, root.key, inStart, inEnd);
        int rootIndex = inOrderMap.getOrDefault(root.key, -1);
        if (rootIndex == -1)
            return null;
        Node left = buildTree2(inOrder, preOrder, inStart, rootIndex, preStart+1, inOrderMap);
        Node right = buildTree2(inOrder, preOrder, rootIndex+1, inEnd, preStart+rootIndex-inStart+1, inOrderMap);
        root.left = left;
        root.right = right;
        return root;
    }

    static int findIndex(int[] array, int key, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == key)
                return i;
        }
        return -1;
    }

    static int findIndex(int[] array, int key) {
        return findIndex(array, key, 0, array.length);
    }

    /**
     * Use stack to store the path we visit when traversing the pre-order array
     * Use set to store the root of the next right sub-tree
     * @param inOrder
     * @param preOrder
     * @return
     */
    static Node buildTree3(int[] inOrder, int[] preOrder) {
        Node root = null;
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();

        for (int pre = 0, in = 0; pre < preOrder.length;) {

            Node node;
            // from current node, go to the left-most node
            do {
                node = new Node(preOrder[pre]);
                if (root == null) {
                    root = node;
                }
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek());
                        stack.pop().right = node;
                    } else {
                        stack.peek().left = node;
                    }
                }
                stack.push(node);
            } while (preOrder[pre++] != inOrder[in] && pre < preOrder.length);

            node = null;
            // find the first node on the stack that needs to set right child
            // if no node has right child, the order of node in the stack (reverse of preOrder) should be same as inOrder
            while (!stack.isEmpty() && in < inOrder.length && stack.peek().key == inOrder[in]) {
                node = stack.pop();
                in++;
            }
            // push the node with right-child to the stack and set
            // because in preOrder, after the left-most branch is the right child of the lowest node in the branch
            if (node != null) {
                set.add(node);
                stack.push(node);
            }
        }
        return root;
    }

    static Node buildTree4(int[] inOrder, int[] preOrder) {
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        int in = 0, pre = 0;
        Node root = null, node;
        while (in < inOrder.length) {

            do {
                node = new Node(preOrder[pre]);
                if (root == null)
                    root = node;
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek());
                        stack.pop().right = node;
                    } else {
                        stack.peek().left = node;
                    }
                }
                stack.push(node);

            } while(preOrder[pre++] != inOrder[in] && pre < preOrder.length);

            node = null;
            while(!stack.isEmpty() && in < inOrder.length && stack.peek().key == inOrder[in]) {
                node = stack.pop();
                in++;
            }

            if (node != null) {
                stack.push(node);
                set.add(node);
            }
        }
        return root;


    }



}
