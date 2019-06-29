package data_structure.stack;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/maximum-element/problem
 *
 * You have an empty sequence, and you will be given  queries. Each query is one of these three types:
 * 1 x  -Push the element x into the stack.
 * 2    -KDTreeDelete the element present at the top of the stack.
 * 3    -Print the maximum element in the stack.
 *
 * https://www.geeksforgeeks.org/find-maximum-in-stack-in-o1-without-using-additional-stack/
 * Instead of pushing a single element to the stack, push a pair instead. The pair consists of the (value, localMax)
 * where localMax is the maximum value up to that element.
 *
 * When we insert a new element, if the new element is greater than the local maximum below it, we set the local
 * maximum of new element equal to the element itself.
 * Else, we set the local maximum of the new element equal to the the local maximum of the element below it.
 * The local maximum of the top of the stack will be the overall maximum.
 * Now if we want to know the maximum at any given point, we ask the top of the stack for local maximum associated with
 * it which can be done in O(1).
 */
public class MaximumElement {
    public static void main(String[] args) {
        fastMaxStack();
    }

    /**
     * get max in O(1) by using additional maxStack to store the max value of all elements under an element
     * Space complexity is O(n)
     */
    public static void fastMaxStack(){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> maxStack = new Stack<>();
        int tempMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++){
            int q = sc.nextInt();
            if (q==1){
                int d = sc.nextInt();
                stack.push(d);
                if (tempMax < d)
                    tempMax = d;
                maxStack.push (tempMax);
            } else if (q==2){
                stack.pop();
                maxStack.pop();
                if (maxStack.isEmpty())
                    tempMax = Integer.MIN_VALUE;
                else
                    tempMax = maxStack.peek();
            } else if (q==3){
                System.out.println(maxStack.peek());
            }
        }
    }

    // get max in O(n)
    public static void naiveMaxStack(){
        Scanner sc = new Scanner (System.in);
        int n = sc.nextInt();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++){
            int q = sc.nextInt();
            if (q==1){
                stack.push(sc.nextInt());
            } else if (q==2){
                stack.pop();
            } else if (q==3){
                Iterator<Integer> it = stack.iterator();
                int max = Integer.MIN_VALUE;
                while (it.hasNext()){
                    int d = it.next();
                    if (max < d)
                        max = d;
                }
                System.out.println(max);
            }
        }
    }

}
