package data_structure.linked_list;

import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 * Given a linked list. Check if it is symmetric, i.e. the node at index i and n-i-1 should be equal
 *
 * One solution is using an additional stack to push all the nodes' data first. When we pop the stack,
 * we would get the list content in the reverse order. So we can traverse the list and pop the stack
 * in parallel and check if the data are the same. If all are same, the list is symmetric
 * Time complexity is O(n) and space complexity is O(n)
 *
 * Another solution is dividing the list into two halves by finding the mid node of the list first.
 * We reverse the second half and check if the first half and reversed second half are the same.
 * After the check, we reverse the second half again to ensure that the list content is not changed.
 * Time complexity is O(n) and space complexity is O(1)
 *
 * The third solution is recursive. We maintain two pointers: 1 left, 1 right. Left is init with list
 * head while right moves from head to tail. By recursive call, we can move left pointer to the right
 * and right pointer to the left. The base check is comparing if head and tail's data are the same. If
 * yes, we continue to check if left.next and right.prev are equal. Right.prev is simply the right
 * pointer when recursive method is returned and left.next is simply obtained from the current global
 * left pointer. The two pointers traverse from one end of list to the other end.
 * Time complexity is O(n) and space complexity is O(1).
 *
 */
public class SymmetricLinkedList {
    private static Node left;
    public static void main(String[] args){
        testSymmetric();
    }
    private static void testSymmetric(){
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertAtTail(1);
        list.insertAtTail(2);
        list.insertAtTail(3);
        list.insertAtTail(2);
        list.insertAtTail(1);
        System.out.println(isSymmetricWithStack(list));
        System.out.println(isSymmetricWithReverse(list));
        System.out.println(isSymmetricWithRecursion(list));
        list.print();
        list.insertAtTail(5);
        System.out.println(isSymmetricWithStack(list));
        System.out.println(isSymmetricWithReverse(list));
        System.out.println(isSymmetricWithRecursion(list));
        list.print();
    }
    public static boolean isSymmetricWithStack(SinglyLinkedList list){
        Stack<Integer> stack = new Stack<>();
        for (Node node = list.head; node != null; node = node.next){
            stack.push(node.data);
        }
        for (Node node = list.head; node != null; node = node.next){
            if (node.data != stack.pop())
                return false;
        }
        return true;
    }
    public static boolean isSymmetricWithReverse(SinglyLinkedList list){
        //first half have same number of nodes as second half if list's node count is even
        //first half have 1 more elements than second half if list's node count is odd
        Node firstHalfTail = list.getMiddleNode();
        if (firstHalfTail == null || firstHalfTail.next == null)
            return true;
        Node secondHalfHead = firstHalfTail.next;
        //reverse second half
        Node tail = reverse(secondHalfHead);
        Node first = list.head, second = tail;
        boolean isSymmetric = true;
        //second half has less or equal number of nodes than first half, so it would end first
        while (second != null){
            if (first.data != second.data){
                isSymmetric = false;
                break;
            }
            first = first.next;
            second = second.next;
        }
        //reverse second half again to maintain the list content
        reverse(tail);
        return isSymmetric;
    }

    public static Node reverse(Node node){
        Node reverseHead = recursiveReverse(node);
        if (node != null)
            node.next = null;
        return reverseHead;
    }

    public static Node recursiveReverse(Node node){
        if (node == null || node.next == null) {
            return node;
        }
        Node reverseHead = reverse(node.next);
        node.next.next = node;
        return reverseHead;
    }

    public static boolean isSymmetricWithRecursion(SinglyLinkedList list){
        left = list.head;
        //by recursive call, left moves to the right and right moves to the left
        return recursiveIsSymmetric(list.head);
    }
    public static boolean recursiveIsSymmetric(Node right){
        if (left.next == null || right.next == null)
            return left.data == right.data;
        if (!recursiveIsSymmetric(right.next))
            return false;
        left = left.next;
        return left.data == right.data;
    }
}
