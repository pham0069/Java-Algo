package data_structure.linked_list;

/**
 * https://www.geeksforgeeks.org/segregate-even-and-odd-elements-in-a-linked-list/
 *
 * Given a linked list, write a function to modify the linked list such that all even numbers
 * appear before all the odd numbers in the modified linked list. Also, keep the order of
 * even and odd numbers same.
 * For example, input = 17->15->8->12->10->5->4->1->7->6->NULL
 * output = 8->12->10->4->6->17->15->5->1->7->NULL
 */
public class SegregateEvenAndOddNodes {
    public static void main(String[] args){
        testSegregate();
    }
    private static void testSegregate(){
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertAtTail(17);
        list.insertAtTail(15);
        list.insertAtTail(8);
        list.insertAtTail(12);
        list.insertAtTail(10);
        list.insertAtTail(5);
        list.insertAtTail(4);
        list.insertAtTail(1);
        list.insertAtTail(7);
        list.insertAtTail(6);
        Node segregatedHead = segregate(list);
        if (segregatedHead != null)
            segregatedHead.print();
    }

    /**
     * time complexity is O(n)
     */
    private static Node segregate(SinglyLinkedList list){
        //0 for even, 1 for odd
        Node[] head = new Node[2];
        Node[] tail = new Node[2];
        for (Node node = list.head; node != null; node = node.next){
            int index = node.data %2;
            if (head[index] == null){
                head[index] = node;
                tail[index] = node;
            } else {
                tail[index].next = node;
                tail[index] = tail[index].next;
            }
        }
        //even head is null, i.e. no even node
        if (head[0] == null)
            return head[1];
        //even head is present
        tail[0].next = head[1];//link even tail to odd head
        tail[1].next = null;//link odd tail to null
        return head[0];
    }
}
