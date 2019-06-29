package data_structure.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/and-xor-or/problem
 *
 * Given an array A[] of N distinct elements. Let M1 and M2 be the smallest and the next smallest element in the interval
 * [L, R] where 1 <= L < R <= N.
 *
 * S = (((M1 & M2) ^ (M1 | M2)) & (M1 ^ M2))
 * where &, | are ^ the bitwise operators AND, OR and XOR respectively.
 * Your task is to find the maximum possible value of S.
 *
 * S is equivalent to M1^M2. The most important is to get all the pairs M1 and M2 to compare their xor values.
 */

public class AndXorOr {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        Stack<Integer> stack = new Stack<>();
        int maxXor = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            if (stack.isEmpty() || a[i] > stack.peek()) {
                stack.push(a[i]);
            } else {
                while (!stack.isEmpty()){
                    int top = stack.pop();
                    maxXor = Math.max(maxXor, top ^ a[i]);
                    if (!stack.isEmpty())
                        maxXor = Math.max(maxXor, top ^ stack.peek());
                    if (stack.isEmpty() || a[i] > stack.peek()) {
                        stack.push(a[i]);
                        break;
                    }
                }
            }
        }
        //after the loop, the stack should be in increasing order
        //the top element in stack should be a[n-1]
        while (!stack.isEmpty()){
            int top = stack.pop();
            if (!stack.isEmpty())
                maxXor = Math.max(maxXor, top ^ stack.peek());
        }
        System.out.println(maxXor);
    }
}
