package algorithm.sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] x = {5, 4, 3, 2, 1};
        recursiveSort(x);
        Utils.print(x);
    }
    static void recursiveSort(int array[]) {
        sort(array, 0, array.length-1);
    }

    static void sort(int array[], int left, int right) {
        if (left < right) {
            int mid = (left+right)/2;
            sort(array, left, mid);
            sort(array, mid+1, right);
            merge(array, left, mid, right);
        }
    }

    static void merge(int[] array, int left, int mid, int right) {
        // Copy original array to temporary array
        int[] first = Arrays.copyOfRange(array, left, mid+1);
        int[] second = Arrays.copyOfRange(array, mid+1, right+1);
        int n1 = first.length;
        int n2 = second.length;
        int i = 0, j = 0, k = left;

        // Merge the temporary array back to the original array
        while (i < n1 && j < n2) {
            if (first[i] <= second[j]) {
                array[k] = first[i];
                i++;
            } else {
                array[k] = second[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of first half to original array, if any
        while (i < n1) {
            array[k] = first[i];
            i++;
            k++;
        }

        // Copy the remaining elements of second half to original array, if any
        while (j < n2) {
            array[k] = second[j];
            j++;
            k++;
        }
    }

    static void inPlaceMerge(int[] array, int left, int mid, int right) {
        int i = left, j = mid+1;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                i ++;
            } else {
                int value = array[j];
                int k = j;
                // This may cause time complexity increases to O(N^2)
                // Shift all the elements between i and j right by 1.
                while (k != i) {
                    array[k] = array[k - 1];
                    k--;
                }
                array[k] = value;
            }
        }
    }

    static void swap(int[] array, int i, int j) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

    static void iterativeSort(int[] array) {
        int size = 1;
        int n = array.length;
        int start;

        /**
         * Merge subarrays in bottom up manner.
         * First merge subarrays of size 1 to create sorted subarrays of size 2,
         * then merge subarrays of size 2 to create sorted subarrays of size 4,
         * and so on
         */
        for (size = 1; size <= n-1; size = 2*size) {
            // Pick starting point of different subarrays of current size
            for (start = 0; start < n-1; start += 2*size) {
                int mid = Math.min(start + size - 1, n-1);
                int end = Math.min(start + 2*size - 1, n-1);

                // Merge subarrays array[start...mid]& array[mid+1...end]
                merge(array, start, mid, end);
            }
        }
    }

}
