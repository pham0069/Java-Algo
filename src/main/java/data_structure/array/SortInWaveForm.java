package data_structure.array;

/**
 * https://www.geeksforgeeks.org/sort-array-wave-form-2/
 * Given an unsorted array of integers, sort the array into a wave like array. An array ‘arr[0..n-1]’ is sorted in wave form if arr[0] >= arr[1] <= arr[2] >= arr[3] <= arr[4] >= …..
 * Example 0:
 * Input:  arr[] = {10, 5, 6, 3, 2, 20, 100, 80}
 * Output: arr[] = {10, 5, 6, 2, 20, 3, 100, 80} OR {20, 5, 10, 2, 80, 6, 100, 3} OR any other array that is in wave form
 * Example 1:
 * Input:  arr[] = {20, 10, 8, 6, 4, 2}
 * Output: arr[] = {20, 8, 10, 4, 6, 2} OR {10, 8, 20, 2, 6, 4} OR any other array that is in wave form
 *
 * Method 1: Sort and swap
 * Sort array in increasing order, then swap two adjacent elements
 * Time complexity is O(nlogn)
 * Method 2: Traverse array once and swaps adjacent elements if the trend is not correct
 */
public class SortInWaveForm {
    public static void main(String[] args) {
        int[] array = {10, 5, 6, 3, 2, 20, 100, 80};
        sortAsWave(array);
        print(array);
    }
    static void sortAsWave(int[] array) {
        if (array.length <= 2)
            return;
        boolean down = array[0]>array[1];
        int temp;
        for (int i = 1; i < array.length-1; i++) {
            down = !down;
            if (down ^ (array[i]>array[i+1])) {
                temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
            }

        }
    }

    static void print(int[] array) {
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }
}
