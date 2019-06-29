package data_structure.queue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * https://www.geeksforgeeks.org/implement-stack-using-queue/
 *
 * Implement a Stack using 2 queue q1 and q2 .
 * Two approaches:
 * 1. Making push operation costly
 * Put newly entered element at the front of ‘q1’, so that pop operation just dequeues from ‘q1’. ‘q2’ is used to put
 * every new element at front of ‘q1’.
 * 2. Making pop operation costly
 * Enqueue new element at the end of q1. When we need to pop from stack, we keep dequeueing elements from q1 to q2,
 * except the last element, which is also the returned value.
 *
 */
public class StackUsingTwoQueues {

    public static void main(String[] args) {
        //StackOne stack = new StackOne();
        StackTwo stack = new StackTwo();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        stack.push(4);
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }



    static class StackOne {
        private Queue q1 = new ArrayDeque();
        private Queue q2 = new ArrayDeque();
        void push(Object o) {
            q2.offer(o);

            //put stack content into q2
            while (!q1.isEmpty()) {
                q2.offer(q1.poll());
            }

            //swap q1 and q2, such that q1 holds the stack content
            Queue temp = q2;
            q2 = q1;
            q1 = temp;
        }

        Object pop() {
            return q1.poll();
        }
    }

    static class StackTwo {
        private Queue q1 = new ArrayDeque();
        private Queue q2 = new ArrayDeque();
        void push(Object o) {
            q1.offer(o);
        }

        Object pop() {
            //final value of o is the last element of the queue, also the top of stack
            Object o = null;
            while (!q1.isEmpty()) {
                o = q1.poll();
                if (!q1.isEmpty())
                    q2.offer(o);
            }

            //swap q1 and q2, such that q1 holds the stack content
            Queue temp = q2;
            q2 = q1;
            q1 = temp;

            return o;
        }
    }
}
