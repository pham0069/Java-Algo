package algorithm.sort3;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * https://practice.geeksforgeeks.org/problems/kth-smallest-element/0
 * https://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array/
 * http://www.ics.uci.edu/~eppstein/161/960125.html
 * http://www.cs.rit.edu/~ib/Classes/CS515_Spring12-13/Slides/022-SelectMasterThm.pdf
 * https://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array-set-2-expected-linear-time/
 * https://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array-set-3-worst-case-linear-time/
 *
 *
 * Given an array arr and a number K where K is smaller than size of array, the task is to find the K’th smallest
 * element in the given array. It is given that all array elements are distinct.
 *
 * Method 1: Sort array then get kth element
 * Time complexity is O(nlogn)
 *
 * Method 2: Use a max heap to store K smallest numbers
 * Time complexity is O(k+(n-k)logk)
 *
 * Method 3: Use a min heap to store the whole array
 * Poll the heap for k times to get the Kth smallest number
 * Time complexity is O(n+klogn)
 *
 * Method 4: Quick-sort-based
 * This is an optimization over method 1 if QuickSort is used as a sorting algorithm in first step. In QuickSort, we
 * pick a pivot element, then move the pivot element to its correct position and partition the array around it.
 * The idea is, not to do complete quick-sort, but stop at the point where pivot itself is k’th smallest element. Also,
 * not to recur for both left and right sides of pivot, but recur for one of them according to the position of pivot.
 * The worst case time complexity of this method is O(n^2), but it works in O(n) on average.
 *
 * Strategies to choose pivot:
 * 1. Fixed pivot, e.g. first or last element of subarray
 * 2. Random pivot
 * 3. Pivot that divides array in a balanced way (to improve the worst-case linear time)
 *
 * - Divide array[] into ⌈n/5⌉ groups where size of each group is 5 except possibly the last group which may have less
 * than 5 elements.
 * - Sort the above created ⌈n/5⌉ groups and find median of all groups. Create an auxiliary array 'median[]' and store
 * medians of all ⌈n/5⌉ groups in this median array.
 * - medOfMed = kthSmallest(median[0..⌈n/5⌉-1], ⌈n/10⌉)
 *
 *
 */
public class KthSmallestNumber {
    static Random random = new Random();
    public static void main(String[] args) {
        int[] array = {7, 10, 4, 3, 20, 15};
        int k = 3;
        System.out.println(getKthSmallestNumberUsingQuickSort(array, array.length, k));
    }

    static int getKthSmallestNumberUsingMaxHeap(int[] array, int n, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        for (int i = 0; i < k; i++) {
            maxHeap.add(array[i]);
        }
        for (int i = k; i < n; i++) {
            if (array[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(array[i]);
            }
        }
        return maxHeap.peek();
    }

    static int getKthSmallestNumberUsingQuickSort(int[] array, int n, int k){
        return recursiveGetKthSmallestNumber(array, 0, n-1, k);
    }

    static int recursiveGetKthSmallestNumber(int[] array, int start, int end, int k) {
        if (k > 0 && k <= end - start + 1) {
            //int pivotPosition = partition(array, start, end);
            //int pivotPosition = randomPartition(array, start, end);
            int median = findMedian(array, start, end);
            int pivotPosition = partition(array, start, end, median);
            int pivotRank = pivotPosition - start + 1;
            if (pivotRank == k)
                return array[pivotPosition];

            // If rank is higher, recur for left subarray
            if (pivotRank > k)
                return recursiveGetKthSmallestNumber(array, start, pivotPosition-1, k);

            // Else recur for right subarray
            return recursiveGetKthSmallestNumber(array, pivotPosition+1, end, k-pivotRank);
        }

        // i.e. k is more than number of elements in array
        return Integer.MAX_VALUE;
    }

    static int findMedianOfFixedSizeSubarray(int[] array, int start, int length) {
        Arrays.sort(array, start, start+length);
        return array[start+length/2];
    }

    static int findMedian(int[] array, int start, int end) {
        int n = end - start + 1;
        int m = (n+4)/5; //number of groups of 5
        int[] median = new int[m];
        int lastGroupSize = n%5==0?5:n%5;
        int i;
        for (i = 0; i < m-1; i++) {
            median[i] = findMedianOfFixedSizeSubarray(array, start+i*5, 5);
        }
        median[m-1] = findMedianOfFixedSizeSubarray(array, start+i*5, lastGroupSize);

        int medianOfMedian = median.length == 1? median[0]:recursiveGetKthSmallestNumber(median, 0, m-1, m/2);
        return medianOfMedian;
    }

    /**
     * Partition with given pivot
     */
    static int partition(int[] array, int start, int end, int pivot) {
        for (int i = start; i < end; i++) {
            if (array[i] == pivot) {
                swap(array, i, end);
                break;
            }
        }

        return partition(array, start, end);
    }

    static int randomPartition(int[] array, int start, int end) {
        int n = end - start + 1;
        int pivot = start + random.nextInt(n);
        swap(array, pivot, end);

        return partition(array, start, end);
    }

    /**
     * Partition with pivot fixed as array[end]
     */
    static int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int i = start; //array[start..i) <= pivot, array[i..end-1) > pivot
        for (int j = start; j <= end - 1; j++) {
            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, end);
        return i;//correct position of pivot
    }

    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
