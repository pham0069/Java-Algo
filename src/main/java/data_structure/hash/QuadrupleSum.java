package data_structure.hash;

/**
 * https://www.geeksforgeeks.org/find-four-numbers-with-sum-equal-to-given-sum/
 * https://www.geeksforgeeks.org/find-four-elements-that-sum-to-a-given-value-set-2/
 * Given an array of integers, find any one combination of four elements in the array whose sum is equal to a given value.
 * Example: input {10, 2, 3, 4, 5, 9, 7, 8} and sum = 23
 * Output: 3 5 7 8
 * Explanation: 3 + 5 + 7 + 8 = 23
 *
 * Method 1: Brute force.
 * Try all possible quadruples from array. Take O(n^4) time
 *
 * Method 2: Fix first and second element from array. Use hash-way or sort-way to find the remaining two elements that
 * sum up with the first two to the given value.
 * Take O(n^3) time
 *
 * Method 3: Calculate all O(n^2) pair sums. Check if any two sums has sum up to the given value. If they exists, further
 * check if these two sums come from four distinct elements of the array
 * Take O(n^2) time ??
 */
public class QuadrupleSum {
}
