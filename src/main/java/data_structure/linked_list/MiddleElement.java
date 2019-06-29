package data_structure.linked_list;

public class MiddleElement {
    public static void main(String[] args) {
        Node root = createLinkedList(new int[]{5, 2, 6, 8, 9, 3});
        getMiddleElement(root);
    }

    static void getMiddleElement(Node root) {
        Node slow = root, fast = root;
        if (root == null)
            return;
        while (fast!= null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        System.out.println(slow.data);
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
