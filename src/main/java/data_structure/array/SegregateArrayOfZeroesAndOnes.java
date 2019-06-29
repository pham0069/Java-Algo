package data_structure.array;

import com.google.common.base.Strings;

/**
 * https://www.geeksforgeeks.org/segregate-0s-and-1s-in-an-array-by-traversing-array-once/
 *
 * You are given an array of 0s and 1s in random order. Segregate 0s on left side and 1s on right side of the array.
 * Traverse array only once.
 * Example:
 * Input array   =  [0, 1, 0, 1, 0, 0, 1, 1, 1, 0]
 * Output array =  [0, 0, 0, 0, 0, 1, 1, 1, 1, 1]
 *
 * Method 1: Count number of 0s and 1s
 * Then put number of 0s in front of array, followed by remaining 1s
 * Have to traverse array twice
 *
 * Method 2: Use two indices to traverse array
 * Only need to traverse array once
 */
public class SegregateArrayOfZeroesAndOnes {
    public static void main(String[] args) {
        int[] array = {0, 1, 0, 1, 0, 0, 1, 1, 1, 0};
        //segregate(array);
        segregate2(array);
        print(array);
    }

    static void print(int[] array) {
        for (int i = 0; i < array.length; i++)
            System.out.print(array[i] + " ");
        System.out.println();
    }

    static void segregate(int[] array) {
        int i, zeroCount = 0;
        for (i = 0; i < array.length; i++)
            if (array[i] == 0)
                ++ zeroCount;
        for (i = 0; i < zeroCount; i++)
            array[i] = 0;
        for (i = zeroCount; i < array.length; i++)
            array[i] = 1;
    }

    static void segregate2(int[] array) {
        int left = 0, right = array.length-1;
        while (left < right) {
            while (array[left] == 0)
                left++;
            while (array[right] == 1)
                right--;
            if (left < right) {
                array[left] = 0;
                array[right] = 1;
                left++;
                right--;
            }
        }
    }
}
