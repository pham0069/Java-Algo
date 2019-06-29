package data_structure.array;

/**
 * https://www.geeksforgeeks.org/sort-an-array-of-0s-1s-and-2s/
 * Given an array A[] consisting 0s, 1s and 2s, write a function that sorts A[]. The functions should put all 0s first,
 * then all 1s and all 2s in last.
 * Example 1:
 * Input :  {0, 1, 2, 0, 1, 2}
 * Output : {0, 0, 1, 1, 2, 2}
 * Example 2:
 * Input :  {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1}
 * Output : {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2}
 *  --------------------------------------------------------------------------------------------------------------------
 *
 */
public class SegregateArrayOfZeroesOnesAndTwos {
    public static void main(String[] args) {
        //int[] array = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
        //int[] array = {1, 1, 1, 2, 1, 2, 1};
        int[] array = {0, 1, 2, 0, 1, 2};
        segregate(array);
        print(array);
    }
    static void print(int[] array) {
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }
    static void segregate(int[] array) {
        int min = 0, max = array.length-1;
        while (array[min] == 0)
            ++min;
        int mid = min;
        while (mid <= max) {
            while (array[mid] == 1)
                ++mid;
            while (array[max] == 2)
                --max;
            if (mid > max)
                break;

            if (array[mid] == 0) {
                array[min] = 0;
                array[mid] = 1;
                ++min;
                ++mid;
            } else if (array[mid] == 2) {
                array[mid] = array[max];
                array[max] = 2;
                --max;
            }
        }
    }
}
