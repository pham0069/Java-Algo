package data_structure.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://www.geeksforgeeks.org/sliding-window-maximum-maximum-of-all-subarrays-of-size-k/
 *
 * Given an array A and an integer K. Find the maximum for each and every contiguous subarray of size K.
 * Input format:
 * The first line of input contains an integer T denoting the number of test cases. The first line of each test case contains a single integer N denoting the size of array and the size of subarray K. The second line contains N space-separated integers A1, A2, ..., AN denoting the elements of the array.
 * Output format:
 * Print the maximum for every subarray of size k.
 * Constraints:
 1 ≤ T ≤ 200
 1 ≤ N ≤ 107
 1 ≤ K ≤ N
 0 ≤ A[i] <= 107

 * Sample Input:
 2
 9 3
 1 2 3 1 4 5 2 3 6
 10 4
 8 5 10 7 9 4 15 12 90 13
 *
 * Sample Output:
 3 3 4 5 5 5 6
 10 10 10 15 15 90 90
 *
 * Explanation: Starting from first subarray of size k = 3, we have 3 as maximum. Moving the window forward, maximum
 * element are as 3, 4, 5, 5, 5 and 6.
 *
 * Methhod 1: Run two loops. In the outer loop, take all subarrays of size k. In the inner loop, get the maximum of the
 * current subarray. Time complexity is O(k*n)
 *
 * Method 2: Use Self-Balancing BST
 1) Pick first k elements and create a Self-Balancing Binary Search Tree (BST) of size k.
 2) Run a loop for i = 0 to n – k
 …..a) Get the maximum element from the BST, and print it.
 …..b) Search for arr[i] in the BST and delete it from the BST.
 …..c) Insert arr[i+k] into the BST.
 * Time complexity is O(nlogk)
 *
 * Method 3: Use deque
 * We create a Deque, of capacity k, that stores only useful elements of current window of k elements. An element is
 * useful if it is in current window and is greater than all other elements on left side of it in current window.
 * We process all array elements one by one and maintain deque to contain useful elements of current window and these
 * useful elements are maintained in sorted order. The element at front of the deque is the largest and element at rear of
 * deque is the smallest of current window.
 */
public class MaximumOfAllKSizedSubarrays {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 1, 4, 5, 2, 3, 6};
        int n = 9, k = 3;
        printKMax(array, n, k);
    }
    static void printKMax(int array[], int n, int k) {
        /** deque stores indexes of array elements that are useful in every window
         * in a way such that array[deque[i]] >= array[deque[j]] for i < j
         */
        Deque<Integer> deque = new ArrayDeque<>(k);
        for (int i = 0; i < k; ++i) {
            while ( (!deque.isEmpty()) && array[i] >= array[deque.getLast()])
                deque.removeLast();
            deque.addLast(i);
        }

        for (int i = k ; i < n; ++i) {
            // The element at the front of the queue is the largest element of previous window
            System.out.print(array[deque.getFirst()] + " ");
            // remove elements from the front outside current window, i.e. largest element of window should be head of queue
            while ( (!deque.isEmpty()) && deque.getFirst() <= i - k)
                deque.removeFirst();
            // remove elements from the rear smaller that current element
            while ( (!deque.isEmpty()) && array[i] >= array[deque.getLast()])
                deque.removeLast();
            deque.addLast(i);
        }

        // Print the maximum element of last window
        System.out.println(array[deque.getFirst()]);
    }

}
