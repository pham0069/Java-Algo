package algorithm.sort;

/**
 * https://www.geeksforgeeks.org/selection-sort/
 *
 * The selection sort algorithm sorts an array by repeatedly finding the minimum element (considering ascending order)
 * from unsorted part and putting it at the beginning. The algorithm maintains two subarrays in a given array.
 * 1) The subarray which is already sorted.
 * 2) Remaining subarray which is unsorted.
 *
 * In every iteration of selection sort, the minimum element (considering ascending order) from the unsorted subarray
 * is picked and moved to the sorted subarray.
 *
 * Time complexity is O(n^2)
 *
 * https://www.geeksforgeeks.org/stable-selection-sort/
 * Selection sort is not stable by nature due to swapping that could make items out of order.
 * It can be made stable by shifting element to put minimum elements to correct position without swapping.
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] array = {100, 4, 29, 238, 103};
        selectionSort(array);
        Utils.print(array);
    }


    static void selectionSort(int array[]) {
        int n = array.length;
        for (int i = 0; i < n-1; i++) {
            int minIndex = i;
            for (int j = i+1; j < n; j++)
                if (array[j] < array[minIndex])
                    minIndex = j;

            //swap to put min value to correct position i
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }
    static void stableSelectionSort(int array[]) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++)
                if (array[j] < array[minIndex])
                    minIndex = j;

            // Move minimum element at current i.
            int min = array[minIndex];
            while (minIndex > i) {
                array[minIndex] = array[minIndex - 1];
                minIndex--;
            }
            array[i] = min;
        }
    }

}
