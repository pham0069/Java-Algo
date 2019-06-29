package algorithm.misc;

/**
 * https://www.geeksforgeeks.org/equilibrium-index-of-an-array/
 *
 * Given an array A of N positive numbers. The task is to find the position where equilibrium first occurs in the array.
 * Equilibrium position in an array is a position such that the sum of elements before it is equal to the sum of elements
 * after it.
 * Example :
 * Input : A[] = {-7, 1, 5, 2, -4, 3, 0}
 * Output : 3
 * Explanation: 3 is an equilibrium index, because: A[0] + A[1] + A[2]  =  A[4] + A[5] + A[6]
 *
 * Calculate the sum of all elements first, so we can quickly get sum of two sides on any point.
 * Time complexity is O(n)
 */
public class EquilibriumPoint {
}
