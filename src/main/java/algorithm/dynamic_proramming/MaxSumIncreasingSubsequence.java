package algorithm.dynamic_proramming;

/**
 * https://practice.geeksforgeeks.org/problems/maximum-sum-increasing-subsequence/0
 *
 * Given an array A of N positive integers. Find the sum of maximum sum increasing subsequence of the given array.
 * Example:
 * Input: array = {1, 101, 2, 3, 100, 4, 5}
 * Output: 106
 * Explanation: All the increasing subsequences are : (1,101); (1,2,3,100); (1,2,3,4,5). Out of these (1, 2, 3, 100) has maximum sum,i.e., 106.
 *
 * Use DP to store max sum for increasing subsequence ending at each index. Time complexity is O(n^2). Space complexity is O(n).
 */
public class MaxSumIncreasingSubsequence {
    public static void main(String[] args) {
        //int[] array = {1, 101, 2, 3, 100, 4, 5};
        int[] array = {10, 8, 7, 5, 1, 2};
        System.out.println(getMaxSumForIncreasingSubsequence(array));
    }

    static int getMaxSumForIncreasingSubsequence(int[] array) {
        int[] maxSum = new int[array.length];
        int globalMax = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++)
                if (array[j] < array[i] && max < maxSum[j])
                    max = maxSum[j];
            maxSum[i] = max + array[i];
            if (maxSum[i] > globalMax)
                globalMax = maxSum[i];
        }
        return globalMax;
    }
}
