package data_structure.heap;

/**
 * https://www.geeksforgeeks.org/kth-largest-element-in-a-stream/
 *
 * Given an infinite stream of integers, find the k’th largest element at any point of time.
 * Example:
 * Input: stream[] = {10, 20, 11, 70, 50, 40, 100, 5, ...}, k = 3
 * Output:           {_,   _, 10, 11, 20, 40, 50,  50, ...}
 * Extra space allowed is O(k)
 *
 * Method 1: Keep an array of size k, to store k largest elements in stream in sorted order. The k’th largest element
 * can be found in O(1) time.
 * For every new element in stream, check if the new element is smaller than current k’th largest element. If yes, then
 * ignore it. If no, then remove the smallest element from array and insert new element in sorted order.
 * Time complexity of processing a new element is O(k).
 *
 * Method 2: Use BST
 * Time to get k'th largest element is O(logk). Time to process new element is O(logk)
 *
 * Method 3: Use heap
 * Time to get k'th largest element is O(1). Time to process new element is O(logk)
 */
public class KthLargestElementInAStream {
}
