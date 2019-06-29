package data_structure.linked_list;

public class SinglyLinkedList {
    Node head;
    public Node delete(int index) {
        if (head == null || index < 0)
            return null;//invalid delete
        if (index == 0) {
            Node deleted = head;
            head = head.next;
            return deleted;
        }
        //find the node at (index-1)
        Node node = head;
        for (int i = 1; node!= null && i <= index-1; i++){
            node = node.next;
        }
        if (node == null || node.next == null){
            return null; //invalid delete
        }

        Node deleted = node.next;
        node.next = deleted.next;
        return deleted;
    }
    public Node insertAtIndex(int data, int index){
        if (index < 0)
            return null;//invalid insertion
        if (index == 0)
            return insertAtHead(data);

        Node newNode = new Node(data);
        Node previous = head;
        if (head == null)
            return null;//invalid insertion
        for (int i = 1; i < index; i++){
            previous = previous.next;
            if (previous == null){
                return null; //invalid insertion
            }
        }
        newNode.next = previous.next;
        previous.next = newNode;
        return newNode;
    }
    public Node insertAtHead(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        return newNode;
    }
    public Node insertAtTail(int data){
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node node = head;
            while (node.next != null)
                node = node.next;
            node.next = newNode;
        }
        return newNode;
    }
    public void print(){
        if (head != null)
            head.print();
        else
            System.out.println("Empty list");
    }
    public int getLength(){
        int length = 0;
        for (Node node = head; node!= null; node = node.next)
            length ++;
        return length;
    }

    /**
     * Swap the nodes at index x and y
     */
    public boolean swapNodes(int x, int y){
        if (x < 0 || y < 0 || x == y)
            return false;

        Node prevX = null, curX = head;
        int i = 0;
        while (curX != null && i < x){
            prevX = curX;
            curX = curX.next;
            i++;
        }

        Node prevY = null, curY = head;
        i = 0;
        while (curY != null && i < y){
            prevY = curY;
            curY = curY.next;
            i++;
        }

        if (curX == null || curY == null)
            return false;

        if (prevX != null)
            prevX.next = curY;
        else
            head = curY;
        if (prevY != null)
            prevY.next = curX;
        else
            head = curX;

        Node curYNext = curY.next;
        curY.next = curX.next;
        curX.next = curYNext;
        return true;
    }

    public void reverse(){
        if (head == null || head.next == null)
            return;
        Node prev = null, cur = head, next = head.next;
        while (cur != null){
            cur.next = prev;
            prev = cur;
            cur = next;
            if (cur != null) {
                next = cur.next;
            }
        }
        head = prev;
    }

    public Node getNodeFromHead(int index){
        Node node = head;
        for (int i = 0; i < index; i++){
            if (node == null)
                return null;
            node = node.next;
        }
        return node;
    }

    /**
     * return the node at a given index counting from tail
     * if considering the tail node is at index 0
     */
    public Node getNodeFromTail(int index){
        Node cur = head;
        for (int i = 0; i < index; i++){
            if (cur == null)
                return null;//there are less than index elements in list
            cur = cur.next;
        }
        if (cur == null)
            return null;
        Node beforeCur = head;
        while (cur.next != null){
            cur = cur.next;
            beforeCur = beforeCur.next;
        }
        return beforeCur;
    }

    public Node getMiddleNode(){
        if (head == null)
            return null;
        Node mid = head, last = head.next;
        while (last != null && last.next != null){
            mid = mid.next;
            last = last.next.next;
        }
        return mid;
    }

    public static void main(String[] args){
        test();
    }
    public static void test(){
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertAtHead(1);
        list.insertAtTail(2);
        list.insertAtIndex(3,1);
        list.print();
        list.insertAtIndex(4,10);
        list.print();
        list.insertAtIndex(5, 0);
        list.print();
        list.insertAtIndex(6, 4);
        list.print();
        list.delete(0);
        list.print();
        list.delete(3);
        list.print();
        list.delete(1);
        list.print();
        list.delete(3);
        list.print();
        list.delete(-1);
        list.print();
        list.insertAtTail(3);
        list.insertAtTail(4);
        list.insertAtTail(5);
        list.print();
        list.swapNodes(0, 2);
        list.print();
        list.swapNodes(0, 10);
        list.print();
        list.swapNodes(1, 2);
        list.print();
        list.reverse();
        list.print();

        printNode(list.getNodeFromHead(0));
        printNode(list.getNodeFromHead(2));
        printNode(list.getNodeFromHead(5));
        printNode(list.getNodeFromTail(0));
        printNode(list.getNodeFromTail(2));
        printNode(list.getNodeFromTail(5));

        printNode(list.getMiddleNode());
        list.delete(4);
        printNode(list.getMiddleNode());
    }
    private static void printNode(Node node){
        if (node == null)
            System.out.println(node);
        else
            System.out.println(node.data);
    }
}

class Node{
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        next = null;
    }
    public void print(){
        Node node = this;
        while (node!= null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }
}