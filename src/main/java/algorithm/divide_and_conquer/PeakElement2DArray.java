package algorithm.divide_and_conquer;

/**
 * https://www.geeksforgeeks.org/find-peak-element-2d-array/
 * https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-006-introduction-to-algorithms-fall-2011/lecture-videos/MIT6_006F11_lec01.pdf
 *
 *
 * An element is a peak element if it is greater than or equal to its four neighbors, left, right, top and bottom.
 * For example neighbors for A[i][j] are A[i-1][j], A[i+1][j], A[i][j-1] and A[i][j+1]. For corner elements, missing
 * neighbors are considered of negative infinite value.
 *
 * Examples: for input 2D array
 10 20 15
 21 30 14
 7  16 32
 * Output : 30
 * Explanation: 30 is a peak element because all its neighbors are smaller or equal to it. 32 can also be picked as a peak.
 *
 * Example 2:
 10 7
 11 17
 * Output : 17
 * Explanation: corner element case
 * ---------------------------------------------------------------------------------------------------------------------
 * Method 1 - Brute force
 * Time complexity is O(n^2)
 *
 * Method 2 - Divide & Conquer
 *
 * This problem is mainly an extension of finding a peak element in 1D array.
 * Consider mid column and find maximum element in it.
 * Let index of mid column be ‘mid’, value of maximum element in mid column be ‘max’ and maximum element be at
 * ‘mat[max_index][mid]’.
 * If max >= A[index][mid-1] & max >= A[index][pick+1], max is a peak, return max.
 * If max < mat[max_index][mid-1], recur for left half of matrix.
 * If max < mat[max_index][mid+1], recur for right half of matrix.
 *
 */
public class PeakElement2DArray {
    public static void main(String[] args){
        int array[][] = {{ 10, 8, 10, 10 },
                { 14, 13, 12, 11 },
                { 15, 9, 11, 21 },
                { 16, 17, 19, 20 } };

        System.out.println(findPeak(array, 4, 4));
    }

    static int findPeak(int[][] arr, int rows, int columns) {
        return findPeakRecursive(arr, rows, columns, columns/2);
    }

    /**
     * Get row index of max value among those elements in the same column
     */
    static int findMaxIndex(int[][] arr, int rows, int col) {
        int max = Integer.MIN_VALUE, maxIndex = 0;
        for (int i = 0; i < rows; i++) {
            if (max < arr[i][col]) {
                max = arr[i][col];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // Function to find a peak element
    static int findPeakRecursive(int[][] arr, int rows, int columns, int mid) {
        // Evaluating maximum of mid column
        int maxIndex = findMaxIndex(arr, rows, mid);
        int max = arr[maxIndex][mid];

        // If we are on the first or last column, max is a peak, need to prove
        if (mid == 0 || mid == columns-1)
            return max;

        // If mid column maximum is also peak
        if (max >= arr[maxIndex][mid-1] &&
                max >= arr[maxIndex][mid+1])
            return max;

        // If max is less than its left, find peak in the left half of the matrix
        if (max < arr[maxIndex][mid-1])
            return findPeakRecursive(arr, rows, columns, mid - mid/2);

        // If max is less than its left, i.e. max < arr[max_index][mid+1]
        return findPeakRecursive(arr, rows, columns, mid+mid/2);
    }
}
