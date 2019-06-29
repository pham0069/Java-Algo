package algorithm.search;

import java.util.List;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-array/problem
 *
 * Watson gives Sherlock an array of integers. His challenge is to find an element of the array such that
 * the sum of all elements to the left is equal to the sum of all elements to the right.
 *
 * For examplee
 * arr = [5, 6, 8 , 11]
 * 8 is btw 2 subarrays that sum to 11
 *
 * arr = [1]
 * 1 is btw 2 subarrays that sum to 0
 *
 * Constraints
 * 1 <= T <= 10
 * 1 <= n <= 10^5
 * 1 < arr[i] <= 2*10^4
 * 0 <= i < n
 *
 * accumulativeSum <= 2*10^9 -> </=>could be stored as int
 */
public class SherlockAndArray {

    static String balancedSums(List<Integer> arr) {
        int[] accumulativeSum = new int[arr.size()];

        accumulativeSum[0] = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            accumulativeSum[i] = accumulativeSum[i-1] + arr.get(i);
        }

        int total = accumulativeSum[arr.size()-1];
        int leftSum, rightSum;
        for (int i = 0; i < arr.size(); i++) {
            if (i == 0) {
                leftSum = 0;
            } else {
                leftSum = accumulativeSum[i-1];
            }
            if (leftSum*2 + arr.get(i) == total) {
                return "YES";
            }
        }
        return "NO";
    }

}
