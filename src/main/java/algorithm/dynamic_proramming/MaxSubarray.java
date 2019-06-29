package algorithm.dynamic_proramming;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/maxsubarray/problem
 * https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
 *
 * We define subsequence as any subset of an array. We define a subarray as a contiguous subsequence in an array.
 * Given an array, find the maximum possible sum among:
 * - all nonempty subarrays.
 * - all nonempty subsequences.
 * Print the two values as space-separated integers on one line.
 * Note that empty subarrays/subsequences should not be considered.
 *
 * For example, given an array [-1, 2, 3, -4, 5, 10], the maximum subarray sum is comprised of element inidices 1->5 and the sum is 2+3+(-4)+5+10 = 16.
 * The maximum subsequence sum is comprised of element indices {1, 2, 4, 5} and the sum is 2+3+5+10 = 20.
 * ------------------------------------------------------------------------------------------------------------------------------------------------
 * Input Format:
 * The first line of input contains a single integer t, the number of test cases.
 * The first line of each test case contains a single integer n.
 * The second line contains n space-separated integers arr[i] where 0<=i<n.
 *
 * Constraints:
 * The subarray and subsequences you consider should have at least one element.
 *
 * Output Format:
 * Print two space-separated integers denoting the maximum sums of nonempty subarrays and nonempty subsequences, respectively.
 * ------------------------------------------------------------------------------------------------------------------------------------------------
 * Sample Input 0:
 2
 4
 1 2 3 4
 6
 2 -1 2 3 4 -5
 * Sample Output 0:
 10 10
 10 11
 * Explanation 0:
 * In the first case: The maximum sum for both types of subsequences is just the sum of all the elements since they are all positive.
 * In the second case: The subarray [2,-1,2,3,4] is the subarray with the maximum sum, and [2,2,3,4] is the subsequence with the maximum sum.
 *
 * Sample Input 1:
 1
 5
 -2 -3 -1 -4 -6
 * Sample Output 1:
 -1 -1
 * Explanation 1:
 * Since all of the numbers are negative, both the maximum subarray and maximum subsequence sums are made up of one element, -1.
 * ------------------------------------------------------------------------------------------------------------------------------------------------
 * In case input has no positive element, max subarray and max subsequence are both made up of one element, which is the largest (non-positive) element
 * Otherwise, max subsequence should include all the positive elements in the input.
 * For max subarray, we apply DP to calculate. Denote maxSubarray[i] as max subarray that includes input[i] as last element of the subarray. Hence,
 * maxSubarray[i] = max (maxSubarray[i-1]+input[i], input[i]), i.e. subarray[i] reaches max when it include single member input[i], or when combined
 * with previous max subarray ending at (i-1). There is no need to hold the whole array of subarray[i], just need to store the previous max subarray
 * to get the current one.
 * Time complexity is O(n). Space complexity is O(1).
 *
 * Similar approach is known as Kadane algorithm, which tries to maintain the positive segment's sum.
 * It considers empty subarray with sum as 0. Hence it will return 0 for all-negative-element input.
 */
public class MaxSubarray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0){
            int n = sc.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++){
                array[i] = sc.nextInt();
            }
            maxSubsequenceSumAndSubarraySum(array);
        }
    }

    static void maxSubsequenceSumAndSubarraySum(int[] array) {
        int maxSubsequenceSum = 0;
        int maxSubarraySum = 0;
        int maxElement = Integer.MIN_VALUE;
        int curMaxSubarray = 0;
        int element = 0;
        for (int i = 0; i < array.length; i++) {
            element = array[i];
            if (element > 0)
                maxSubsequenceSum += element;
            if (element > maxElement)
                maxElement = element;
            curMaxSubarray = Math.max(curMaxSubarray+element, element);
            if (curMaxSubarray > maxSubarraySum)
                maxSubarraySum = curMaxSubarray;
        }
        if (maxElement <= 0){
            maxSubarraySum = maxElement;
            maxSubsequenceSum = maxElement;
        }
        System.out.println(maxSubarraySum + " " + maxSubsequenceSum);
    }

    static int maxSubarraySum(int[] array){
        int maxSoFar = 0, maxEndingHere = 0;
        for (int i = 0; i < array.length; i++) {
            maxEndingHere += array[i];
            if (maxEndingHere < 0)
                maxEndingHere = 0;
            else if (maxEndingHere > maxSoFar)
                maxSoFar = maxEndingHere;
        }
        return maxSoFar;
    }
}
