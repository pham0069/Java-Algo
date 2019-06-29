package algorithm.sort3;

/**
 * https://www.geeksforgeeks.org/kth-smallest-element-in-a-subarray/
 *
 * Given an array arr of size N. The task is to find the kth smallest element in the subarray(l to r, both inclusive).
 * There could be multiple queries
 * 
 * Example:
 *
 * Input : arr = {3, 2, 5, 4, 7, 1, 9}, query = (2, 6, 3)
 * Output : 4
 * Explanation: sorted subarray in a range 2 to 6 is {1, 2, 4, 5, 7} and 3rd element is 4
 *
 * Approaches
 * 1. Naive approach: sort the subarray and get the kth smallest number.
 * Time complexity is s*log(s) where s = r-l+1, i.e. length of subarray
 *
 * 2. Using max heap of size k to store k smallest numbers of subarray
 * Time complexity is k+(s-k)*log(k) ~ s*log(k)
 *
 * 3.
 *
 */
public class KthSmallestNumberSubarray {
}
