package algorithm.divide_and_conquer;

/**
 * https://www.geeksforgeeks.org/find-a-peak-in-a-given-array/
 *
 * Given an array of integers. Find any peak element in it (even if array has multiple peaks).
 * An array element is peak if it is NOT smaller than its neighbors. For corner elements, we need to consider only
 * one neighbor.
 * Example 1: input {5, 10, 20, 15}, 20 is the only peak element.
 * Example 2: input {10, 20, 15, 2, 23, 90, 67}, there are two peak elements: 20 and 90.
 * Note that we need to return any one peak element.
 *
 * Following corner cases give better idea about the problem.
 * 1) If input array is sorted in strictly increasing order, the last element is always a peak element.
 * For example, 50 is peak element in {10, 20, 30, 40, 50}.
 * 2) If input array is sorted in strictly decreasing order, the first element is always a peak element.
 * 100 is the peak element in {100, 80, 60, 50, 20}.
 * 3) If all elements of input array are same, every element is a peak element.
 *
 * Method 1 - Brute force
 * Test all elements to see if it is peak by comparing with its previous and next element
 * Time complexity is O(n).
 *
 * Method 2 - Global maximum is also local maximum
 *
 * Method 3 - Divide and conquer
 *
 * A related problem is finding local maxima for an array, which is an element greater than the previous and next elements
 * Note that an array might not have a local maxima due to strict greater condition.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * https://www.geeksforgeeks.org/oracle-interview-experience-set-62-campus-server-technology/
 * Problem stated in another way: Find the length of shortest unordered (neither increasing nor decreasing) sub array
 * in given array.
 * Example 1: input 7 9 10 8 11 – output 3 ( 9 10 80)
 * Example 2: input 1 2 3 5 – output 0 (increasing order)
 * Hint: output always is either 3 or 0.
 * 0 if whole array increasing or decreasing.
 * 3 if peak or valley element exists
 */
public class PeakElement {
    public static void main(String[] args){
        //int[] array = {1, 3, 5, 9, 7, 10, 6};
        int[] array = {10, 20, 11, 25, 30, 40, 50, 60, 70};
        System.out.println(getPeakIndex(array));
        System.out.println(getPeakIndex2(array));
        System.out.println(getPeakIndex3(array));
    }

     static Integer getPeakIndex(int[] array){
        int n = array.length;
        if (n == 0)
            return null;
        if (n == 1)
             return 0;
        if (n == 2)
             return array[0]>array[1]?0:1;

        for (int i = 1; i < n-1; i++) {
            if (array[i-1] <= array[i] && array[i] >= array[i+1])
                return i;
        }
        if (array[0] >= array[1])
            return 0;
        return n-1;
    }

    static Integer getPeakIndex2(int[] array){
        if (array.length == 0)
            return null;
        int peakIndex = 0;
        for (int i = 1; i < array.length-1; i++) {
            if (array[i] > array[peakIndex]) {
                peakIndex = i;
            }
        }
        return peakIndex;
    }

    static Integer getPeakIndex3(int[] array){
         return getPeakIndex(array, 0, array.length-1, array.length);
    }

    static int getPeakIndex(int arr[], int low, int high, int n) {
        // Find index of middle element
        int mid = (low + high)/2;

        // Compare middle element with its neighbours (if neighbours exist)
        if ((mid == 0 || arr[mid-1] <= arr[mid])
                && (mid == n-1 || arr[mid+1] <= arr[mid]))
            return mid;
        // If middle element is not peak and its left neighbor is greater than it,then left half must have a peak
        if (mid > 0 && arr[mid-1] > arr[mid])
            return getPeakIndex(arr, low, (mid -1), n);

        // If middle element is not peak and its right neighbor is greater than it, then right half must have a peak
        return getPeakIndex(arr, (mid + 1), high, n);
    }

}
