package minima;

/**
 * https://www.geeksforgeeks.org/find-the-element-before-which-all-the-elements-are-smaller-than-it-and-after-which-all-are-greater-than-it/
 *
 * Given an unsorted array of size N. Find the first element in array such that all of its left elements are smaller
 * and all right elements to it are greater than it. Return index of the element if there is such an element, otherwise
 * return -1.
 *
 * Examples:
 * Input:   arr[] = {5, 1, 4, 3, 6, 8, 10, 7, 9};
 * Output:  Index of element is 4
 * Explanation: All elements on left of arr[4] are smaller than it and all elements on right are greater.
 *
 * Input:   arr[] = {5, 1, 4, 4};
 * Output:  Index of element is -1
 *
 * Method 1: Brute force. Time complexity is O(n^2)
 * Method 2: Populate 2 arrays leftMax (max value so far compared to left elements) and rightMin (min value so far
 * compared to right elements). The index i where array[i] = leftMax[i] = rightMin[i] is the index to be found. Check this
 * by traversing i from 0 to n-1. Time complexity is O(n).
 * Can further optimize space complexity by storing leftMax only. rightMin is processed on the fly, without being stored
 * on any array.
 */
public class ElementWithLeftSideSmallerAndRightSideGreater {
}
