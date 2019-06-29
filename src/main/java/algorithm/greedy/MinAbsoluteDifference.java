package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/minimum-absolute-difference-in-an-array/forum
 * Consider an array of integers, a[0..n-1]. We define the absolute difference between two elements, a[i] and a[j]
 * (where i != j), to be the absolute value of a[i]-a[j].
 * Given an array of integers, find and print the minimum absolute difference between any two elements in the array.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format
 * The first line contains a single integer n, the size of array a .
 * The second line contains n space-separated integers a[0..n-1].
 * Constraints
 * Output Format
 * Print the minimum absolute difference between any two elements in the array.
 * Sample Input 0
 3
 3 -7 0
 * Sample Output 0
 3
 * Explanation 0
 * With 3 integers in our array, we have three possible pairs: (3, -7), (3, 0), and (-7,0). The absolute values of the
 * differences between these pairs are as follows:
 * (3, -7) -> 10
 * (3, 0) -> 3
 * (-7,0) -> 7
 * Notice that if we were to switch the order of the numbers in these pairs, the resulting absolute values would still
 * be the same. The smallest of these possible absolute differences is 3.
 */
public class MinAbsoluteDifference {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        IntStream.range(0, n).forEach(i -> a[i] = sc.nextInt());
        System.out.println(minimumAbsoluteDifference(a));
    }
    private static int minimumAbsoluteDifference(int[] a) {
        Arrays.sort(a);
        int min = Integer.MAX_VALUE, diff;
        for (int i = 0; i < a.length-1; i++){
            diff = Math.abs(a[i] - a[i+1]);
            if (diff < min)
                min = diff;
        }
        return min;
    }
}
