package data_structure.linked_list;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoopInLinkedList {
    public static void main(String[] args){
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertAtTail(1);
        list.insertAtTail(2);
        list.insertAtTail(3);
        list.insertAtTail(4);
        list.insertAtTail(5);
        testLoop(list);
        Node tail = list.getNodeFromTail(0);
        Node third = list.getNodeFromTail(3);
        tail.next = third;
        testLoop(list);
        Node head = list.getNodeFromHead(0);
        tail.next = head;
        testLoop(list);
    }
    private static void testLoop(SinglyLinkedList list){
        System.out.println(getLoopSize1(list));
        System.out.println(getLoopSize2(list));
        System.out.println(detectCycleFloydAlgo(list));
        System.out.println(detectAndRemoveCycle(list));
    }

    /**
     * traverse list one by one, storing the visited node in a set
     * the first node that got revisited, is the node starting the loop
     */
    public static boolean detectAndRemoveCycle(SinglyLinkedList list){
        Set<Node> visited = new HashSet<>();
        Node prev = null, node = list.head;
        while (node != null){
            if (visited.contains(node)) {
                prev.next = null; //remove cycle
                return true;
            }
            visited.add(node);
            prev = node;
            node = node.next;
        }
        return false;
    }

    /**
     * https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/
     * If a cycle exists, slow and fast pointer could catch up with each other to same position
     * in at most n steps where n = cycle size. So in general it is faster than traversing list
     * one by one as the algo above
     */
    public static boolean detectCycleFloydAlgo(SinglyLinkedList list){
        Node slow = list.head, fast = list.head;
        while (slow != null && fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }
    public static int getLoopSize1(SinglyLinkedList list){
        Map<Node, Integer> visitMap = new HashMap<>();
        Node node = list.head;
        int visitTime = 0;
        while (node != null){
            if (visitMap.containsKey(node)) {
                return visitTime - visitMap.get(node) ;
            }
            visitMap.put(node, visitTime++);
            node = node.next;
        }
        return 0;
    }
    public static int getLoopSize2(SinglyLinkedList list){
        Node slow = list.head, fast = list.head;
        while (slow != null && fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return getLoopSize(slow);
        }
        return 0;
    }
    private static int getLoopSize(Node start){
        int count = 0;
        Node node = start;
        do{
            count ++;
            node = node.next;
        } while (node != start);
        return count;
    }
}
