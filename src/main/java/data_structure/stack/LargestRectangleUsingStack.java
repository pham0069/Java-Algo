package data_structure.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/largest-rectangle/problem
 *
 * Skyline Real Estate Developers is planning to demolish a number of old, unoccupied buildings and construct a
 * shopping mall in their place. Your task is to find the largest solid area in which the mall can be constructed.
 *
 * There are a number of buildings in a certain two-dimensional landscape. Each building has a height, given by h[i]
 * where i in [1, n]. If you join k adjacent buildings, they will form a solid rectangle of area k * min(h[i], ..., h[i+k-1]).
 *
 * For example, the heights array h = [3, 2, 3]. A rectangle of height h = 2 and length k = 3 can be constructed within
 * the boundaries. The area formed is h*k = 2*3 = 6.
 *
 * The idea is if a height at an index k is used to get the rectangle, the rectangle area is optimized by spanning from
 * index i to index j such that h[i-1] is previous element smaller than h[k] and h[i] >= h[k]; h[j+1] is next element
 * smaller than h[k]. As in next larger elemet problem, i and j can be found in O(1) for each k. Thus max optimized
 * rectangle can be found in O(n).
 */
public class LargestRectangleUsingStack {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] height = new int[n];
        for (int i = 0; i < n; i++){
            height[i] = sc.nextInt();
        }
        getLargestRectangle(height);
    }
    public static void getLargestRectangle(int[] height){
        Stack<Integer> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        int i;
        for (i = 0; i < height.length; i++){
            if (stack.isEmpty() || height[i] >= height[stack.peek()]){
                //in this way stack stores number in increasing order
                stack.push(i);
            } else {
                max = Math.max(max, popMaxRect(stack, height, i));
                stack.push(i);
            }
        }

        max = Math.max(max, popMaxRect(stack, height, i));

        System.out.println(max);

    }
    static int popMaxRect(Stack<Integer> stack, int[] height, int i){
        int max = Integer.MIN_VALUE;
        while (!stack.isEmpty()){
            int index = stack.pop();
            //i is the right index of height[index], i.e. height[i] is the first number on the right < height[index]
            int rightIndex = i;
            //stack.peek() is the left index of height[index], i.e. a[stack.peek()] is the first number on the left < height[index]
            int leftIndex;
            if (stack.isEmpty())
                //if this is the last number in the stack, that means it is the smallest number from a[0] to a[index]
                leftIndex = -1;
            else
                leftIndex = stack.peek();
            int temp = height[index]*(rightIndex-leftIndex-1);
            if (temp > max)
                max = temp;
            if (i == height.length)
                continue;
            if (stack.isEmpty() || height[leftIndex] <= height[i])
                break;
        }
        return max;
    }
}
