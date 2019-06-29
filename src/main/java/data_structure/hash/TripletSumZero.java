package data_structure.hash;

/**
 * https://www.geeksforgeeks.org/find-triplets-array-whose-sum-equal-zero/
 * Given an array of distinct elements. The task is to find triplets in array whose sum is zero.
 * Example 1 :
 * Input : arr[] = {0, -1, 2, -3, 1}
 * Output : 0 -1 1, 2 -3 1
 *
 * Example 2:
 * Input : arr[] = {1, -2, 1, 0, 5}
 * Output : 1 -2  1
 * ---------------------------------------------------------------------------------------------------------------------
 * Method 1: Brute-force
 * Check all triplets of the array. Time complexity is O(n^3)
 *
 * Method 2: Hashing
 * Store all elements in a hash set. Compute all pairs' sum and check if its negated value is present in the set.
 * Time complexity is O(n^2). Space complexity is O(n).
 * This can work if array has distinct elements. To extend to array with duplicate elements, we need to check if the
 * array has a triplet (0, a, a)
 *
 * Method 3: Sorting
 * Sort the array. Run a loop to fix the first member in the potential triplet. Run 2 indices, 1 from the next element
 * after the first triplet member, 1 from the end of the array. Get the sum of these numbers and compare to zero, to adjust
 * the indices accordingly.
 * Time complexity is O(n^2). Space complexity is O(1).
 *
 * Equivalent to the problem of finding triplets such that two numbers have sum equal to the third
 */
public class TripletSumZero {
}
