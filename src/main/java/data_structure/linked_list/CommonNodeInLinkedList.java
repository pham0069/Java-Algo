package data_structure.linked_list;

/**
 * https://www.geeksforgeeks.org/write-a-function-to-get-the-intersection-point-of-two-linked-lists/
 * Given two linked list. Check if these lists have a common node and if they do, find the first
 * common node between them.
 *
 * One solution is using two loops to check if a node of 1 list is equal to a node of the other list.
 * Time complexity is O(m*n) in the worst case
 *
 * Another solution is traversing a list and marking the visited nodes. Either by using additional
 * field in node to keep track of its visited state or using a set to store all the visited nodes.
 * Then traversing the second list and check if any node is visited.
 * Time complexity is O(m+n) and space complexity is O(m+n)
 *
 * The third solution is based on difference of node counts in the lists. Let say c1 is the node count
 * of list 1 and c2 is the node count of list 2. If the two lists have the same common node at index i1
 * in list 1 and i2 in list 2. From this common node onwards, the list content should be the same, i.e.
 * i1-i2=c1-c2. If c1<c2, we start traverse list 1 from head and list 2 from node at index c2-c1 in parallel,
 * we should be able to get the common node on the way if it exists.
 * Time complexity is O(m+n) and no space complexity is required
 */
public class CommonNodeInLinkedList {
    public static void main(String[] args){
        testCommon();
    }
    private static void testCommon(){
        SinglyLinkedList listA = new SinglyLinkedList();
        SinglyLinkedList listB = new SinglyLinkedList();
        listA.insertAtTail(1);
        listA.insertAtTail(3);
        listA.insertAtTail(5);
        listA.insertAtTail(7);
        Node commonA = listA.getMiddleNode();
        listB.insertAtTail(2);
        listB.insertAtTail(4);
        listB.insertAtTail(6);
        listB.insertAtTail(8);
        Node tailB = listB.getNodeFromTail(0);
        tailB.next = commonA;

        Node commonNode = getCommonNode(listA, listB);
        if (commonNode != null)
            commonNode.print();
        else
            System.out.println("No common node !");
    }
    public static Node getCommonNode(SinglyLinkedList listA, SinglyLinkedList listB){
        int countA = listA.getLength();
        int countB = listB.getLength();
        Node nodeA = listA.head, nodeB = listB.head;
        //get the start point of two lists
        if (countA < countB){
            for (int i = 0; i < countB-countA; i++)
                nodeB = nodeB.next;
        } else {
            for (int i = 0; i < countA-countB; i++)
                nodeA = nodeA.next;
        }
        //traverse the two lists in parallel
        while (nodeA!= null){
            if (nodeA == nodeB)
                return nodeA;
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }
        return null;
    }
}
