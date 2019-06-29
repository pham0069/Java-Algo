package data_structure.stack;

import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/queue-using-stacks/
 *
 * Implement a Queue using 2 stacks s1 and s2.
 *
 * Use 1 stack to push objects enqueued
 * Use the other stack to pop objects dequeued
 */
public class QueueUsingTwoStacks {
    private final Stack s1 = new Stack();
    private final Stack s2 = new Stack();
    public void enqueue(Object o) {
        s1.push(o);
    }
    public Object dequeue() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty())
                s2.push(s1.pop());
        }
        if (s2.isEmpty()) {
            System.out.println("No object to dequeue");
            return null;
        }
        Object o = s2.pop();
        System.out.println(o);
        return o;
    }

    public static void main(String[] args) {
        QueueUsingTwoStacks queue = new QueueUsingTwoStacks();
        queue.enqueue(1);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue();
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(7);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }
}
