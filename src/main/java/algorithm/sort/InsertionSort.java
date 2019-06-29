package algorithm.sort;

import java.util.Arrays;

/**
 * Insertion sort: given array a[0..n-1]
 * At iteration i-1, sub-array a[0..i-1] is already sorted
 * At next iteration i, we need to find the position in a[0..i-1] to insert a[i]
 * It should be the index k min: a[k] > a[i]
 * a[i] should take the position of a[k]
 * and all the elements a[k..i-1] should be shifted by 1 to the right
 * (i.e. becoming a[k+1..i]
 * This makes sub-array a[0..i] sorted
 * In the implementation, we can do shifting and find position k at the same time (in same loop)
 *
 *
 * For example, array = {2, 1, 5, 4, 3}
 * i = 1
 * 0    2, 2, 5, 4, 3
 * -1   break (< 0)
 * insert  1, 2, 5, 4, 3
 * i = 2
 * 1    1, 2, 5, 4, 3 (break as 2 < 5)
 * insert  1, 2, 5, 4, 3
 * i = 3
 * 2    1, 2, 5, 5, 3
 * 1    break (as 2 < 4)
 * insert 1, 2, 4, 5, 3
 * i = 4
 * 3    1, 2, 4, 5, 5
 * 2    1, 2, 4, 4, 5
 * 1    break (as 2 < 3)
 * insert 1, 2, 3, 4, 5
 */

public class InsertionSort {
    static void sort(int array[]) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            // aim to sort array [0..i]
            int key = array[i];
            int j = i - 1;

            // find position j to insert key
            while (j >= 0 && array[j] > key) {
                // shift element at index j to j+1
                array[j + 1] = array[j];
                System.out.println(String.format("[%d/%d]: %s", i, j, Arrays.toString(array)));
                j = j - 1;
            }
            // after the loop, array[j] <= key -> key should be inserted after index j
            array[j + 1] = key;
            System.out.println(String.format("[%d/insert]: %s", i, Arrays.toString(array)));

        }
    }

    public static void main(String[] args) {
        int[] array = {10, 8, 35, 3, 51, 23, 68, 1, 40, 89, 22};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
