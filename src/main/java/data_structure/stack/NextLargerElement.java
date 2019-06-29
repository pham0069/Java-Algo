package data_structure.stack;

import java.util.Stack;

/**
 * https://practice.geeksforgeeks.org/problems/next-larger-element/0
 * https://www.geeksforgeeks.org/next-greater-element/
 *
 * Given an array A of size N having distinct elements, the task is to find the next greater element for each element of
 * the array in order of their appearance in the array. If no such element exists, output -1.
 *
 * Solution is to use a stack to store index of useful elements. At each index, useful elements are those on the right
 * and larger than the element at that index. The stack stores the index in increasing order. Thus, larger next element
 * should have index at the top of the useful stack.
 *
 * Time complexity is O(n) as each element is pushed one time and popped at most 1 time
 * Space complexity is O(n) in worst case, where the array is in increasing order
 */
public class NextLargerElement {
    public static void main(String[] args) {
        int[] array = {7, 1, 5, 2, 3, 10, 8};
        printNextLargerElement(array);
    }
    static void printNextLargerElement(int[] array) {
        Stack<Integer> stack = new Stack<>();
        for (int i = array.length-1; i >= 0; i--) {
            //pop out index of elements smaller than itself
            while(!stack.isEmpty() && array[stack.peek()] < array[i]) {
                stack.pop();
            }
            //print out the next larger element which is at the top of stack if any
            if (stack.isEmpty())
                System.out.println(i + "\t" + (-1));
            else
                System.out.println(i + "\t" + array[stack.peek()]);
            stack.push(i);
        }
    }
}
