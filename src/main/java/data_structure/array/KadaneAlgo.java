package data_structure.array;

import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0
 * https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
 *
 * Given an array containing both negative and positive integers. Find the contiguous sub-array with maximum sum.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input format:
 * The first line of input contains an integer T denoting the number of test cases. The description of T test cases
 * follows. The first line of each test case contains a single integer N denoting the size of array. The second line
 * contains N space-separated integers A1, A2, ..., AN denoting the elements of the array
 * Output format:
 * Print the maximum sum of the contiguous sub-array in a separate line for each test case.
 * Constraints:
 * 1 ≤ T ≤ 200, 1 ≤ N ≤ 1000, -100 ≤ A[i] <= 100
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample input
 2
 3
 1 2 3
 4
 -1 -2 -3 -4
 * Sample output
 6
 -1
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * Max sum of all contiguous subarray = max (max sum of continguous subarray ending at index i) for all i = 1..n
 * Time complexity is O(n). Space complexity is O(1)
 */
public class KadaneAlgo {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0){
            int n = sc.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++)
                array[i] = sc.nextInt();
            System.out.println(getMaxSumContiguousSubarray(array));
        }
    }
    public static long getMaxSumContiguousSubarray(int[] array){
        int n = array.length;
        long max = array[0];
        long cur = array[0];
        for (int i = 1; i < n; i++){
            if (cur > 0)
                cur = cur+array[i];
            else
                cur = array[i];
            max = Math.max(max, cur);
        }
        return max;
    }
}
