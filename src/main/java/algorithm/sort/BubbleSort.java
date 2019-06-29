package algorithm.sort;

import java.util.Arrays;

/**
 * https://www.geeksforgeeks.org/bubble-sort/
 * https://www.geeksforgeeks.org/recursive-bubble-sort/
 *
 * Bubble Sort is the simplest sorting algorithm that works by repeatedly swapping the adjacent elements if they are in
 * wrong order.
 * Time complexity is O(n^2)
 *
 * For example, array = {2, 1, 5, 4, 3}
 * i = 0
 * 0    1, 2, 5, 4, 3
 * 1    1, 2, 5, 4, 3
 * 2    1, 2, 4, 5, 3
 * 3    1, 2, 4, 3, 5 (5 in place)
 * i = 1
 * 0    1, 2, 4, 3, 5
 * 1    1, 2, 4, 3, 5
 * 2    1, 2, 3, 4, 5 (4 in place)
 * i = 2
 * 0    1, 2, 3, 4, 5
 * 1    1, 2, 3, 4, 5
 * swapped = false (since 1-3 already in place)
 * can end now
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array = {2, 5, 1, 4, 3};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }

    static void bubbleSort(int array[]) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n-1; i++) {
            swapped = false;
            //after iteration i, array[n-i-1] is in place
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // swap adjacent
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
                System.out.println(String.format("[%d/%d]: %s", i, j, Arrays.toString(array)));
            }
            // already in order, no need to recheck
            if (!swapped)
                break;
        }
    }

    static void recursiveBubbleSort(int array[], int n) {
        if (n == 1)
            return;
        for (int i = 0; i < n-1; i++)
            if (array[i] > array[i+1]) {
                // swap
                int temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
            }
        recursiveBubbleSort(array, n-1);
    }
}
