package data_structure.linked_list;

/**
 * https://www.geeksforgeeks.org/add-two-numbers-represented-by-linked-lists/
 * Given two numbers represented by two lists
 * Write a function that returns the sum list.
 * The sum list is list representation of the addition of two input numbers.
 * E.g. List 5 -> 6 -> 3 represent 365
 * List 8 -> 4 -> 2 represent 248
 *
 * https://www.geeksforgeeks.org/add-two-numbers-represented-by-linked-lists-set-3/
 *
 */
public class AddTwoNumberRepresentedByLinkedList {
    public static void main(String[] args) {
        testReverseOrder();
        testForwardOrder();
    }

    static void testReverseOrder() {
        Node first = createLinkedList(new int[]{5, 6, 3});
        Node second = createLinkedList(new int[]{8, 4, 2});
        Node rootResult = addLinkedLists(first, second);
        print(rootResult);
    }

    static void testForwardOrder() {
        Node first = createLinkedList(new int[]{5, 6, 3});
        Node second = createLinkedList(new int[]{8, 4, 2});

        Node reverseRootResult =  addLinkedLists(
                reverseLinkedList(first), reverseLinkedList(second));

        print(reverseLinkedList(reverseRootResult));
    }

    static Node reverseLinkedList(Node node) {
        if (node == null || node.next == null)
            return node;
        Node next = node.next;
        Node reverseRoot = reverseLinkedList(next);
        next.next = node;
        node.next = null; // this is to ensure to set next for the original root node to null
                            // actually no effect on intermediate nodes
        return reverseRoot;
    }


    static Node addLinkedLists(Node first, Node second) {
        Node root = null, result = null;
        int carrier = 0;
        while (first != null || second != null) {
            int sum = carrier;
            if (first != null) {
                sum += first.data;
                first = first.next;
            }
            if (second != null) {
                sum += second.data;
                second = second.next;
            }
            Node node = new Node(sum%10);
            carrier = sum/10;
            if (root == null) {
                root = node;
            }
            if (result != null) {
                result.next = node;
            }
            result = node;
        }

        if (carrier > 0) {
            result.next = new Node(carrier);
        }
        return root;
    }

    static void print(Node root) {
        Node node = root;
        while (node != null) {
            if (node == root){
                System.out.print(node.data);
            } else {
                System.out.print(" -> " + node.data);
            }
            node = node.next;
        }
        System.out.println();
    }


    static Node createLinkedList(int[]values) {
        Node root = null, prev = null, cur = null;
        for (int i = 0; i < values.length; i++) {
            prev = cur;
            cur = new Node(values[i]);
            if (prev != null){
                prev.next = cur;
            } else {
                root = cur;
            }
        }
        return root;
    }
    static class Node {
        int data;
        Node next;
        Node(int data) {
            this.data = data;
        }
    }

}
