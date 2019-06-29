package data_structure.linked_list;

public class SortedLinkedList {
    public static void main(String[] args){
        testMerge();
        testIntersection();
    }
    public static void quickSort(SinglyLinkedList list){

    }
    public static void testMerge(){
        SinglyLinkedList listA = new SinglyLinkedList();
        SinglyLinkedList listB = new SinglyLinkedList();
        listA.insertAtTail(1);
        listA.insertAtTail(3);
        listA.insertAtTail(6);
        listB.insertAtTail(2);
        listB.insertAtTail(5);
        listB.insertAtTail(8);
        Node mergedHead = mergeSortedLists(listA, listB);
        if (mergedHead != null)
            mergedHead.print();
        else
            System.out.println("Empty merge");
    }
    private static void testIntersection(){
        SinglyLinkedList listA = new SinglyLinkedList();
        SinglyLinkedList listB = new SinglyLinkedList();
        listA.insertAtTail(2);
        listA.insertAtTail(4);
        listA.insertAtTail(6);
        listA.insertAtTail(8);
        listB.insertAtTail(1);
        listB.insertAtTail(2);
        listB.insertAtTail(3);
        listB.insertAtTail(4);
        listB.insertAtTail(6);
        Node intersectionHead = intersectSortedLists(listA, listB);
        if (intersectionHead != null)
            intersectionHead.print();
        else
            System.out.println("Empty intersection !");
    }

    /**
     * https://www.geeksforgeeks.org/intersection-of-two-sorted-linked-lists/
     * Given two lists sorted in increasing order, create and return a new list representing the intersection
     * of the two lists. The new list should be made with its own memory — the original lists should not be changed.
     * Time complexity is O(m+n)
     */
    public static Node intersectSortedLists(SinglyLinkedList listA, SinglyLinkedList listB) {
        Node headA = listA.head;
        Node headB = listB.head;
        if (headA == null || headB == null)
            return null;
        Node curA = headA, curB = headB;
        Node head = null, tail = null;
        while (curA != null && curB != null){
            if (curA.data == curB.data){
                if (head == null){
                    head = new Node(curA.data);
                    tail = head;
                } else {
                    tail.next = new Node(curA.data);
                    tail = tail.next;
                }
                curA = curA.next;
                curB = curB.next;
            } else if (curA.data < curB.data){
                curA = curA.next;
            } else {
                curB = curB.next;
            }
        }
        return head;
    }

    /**
     * https://www.geeksforgeeks.org/intersection-of-two-sorted-linked-lists/
     * Given two lists sorted in increasing order. Merge these lists into a single list in ascending order.
     * Time complexity is O(m+n)
     */
    public static Node mergeSortedLists(SinglyLinkedList listA, SinglyLinkedList listB) {
        Node headA = listA.head;
        Node headB = listB.head;
        if (headA == null)
            return headB;
        if (headB == null)
            return headA;

        Node curA = headA, curB = headB;
        Node head = null, tail = null;
        while (curA != null && curB != null){
            if (curA.data < curB.data){
                if (tail != null)
                    tail.next = curA;
                tail = curA;
                curA = curA.next;
            } else {
                if (tail != null)
                    tail.next = curB;
                tail = curB;
                curB = curB.next;
            }
            if (head == null)
                head = tail;
        }
        if (curA == null && curB != null)
            tail.next = curB;
        else
            tail.next = curA;
        return head;
    }
}
