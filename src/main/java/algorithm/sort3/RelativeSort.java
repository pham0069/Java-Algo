package algorithm.sort3;

import algorithm.sort.Utils;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://www.geeksforgeeks.org/sort-array-according-order-defined-another-array/
 *
 * Given two arrays A1[] and A2[], sort A1 in such a way that the relative order among the elements will be same as
 * those are in A2. For the elements not present in A2, append them at last in sorted order.
 * Example:
 * Input: A1[] = {2, 1, 2, 5, 7, 1, 9, 3, 6, 8, 8}, A2[] = {2, 1, 8, 3}
 * Output: A1[] = {2, 2, 1, 1, 8, 8, 3, 5, 6, 7, 9}
 *
 * Method 1: Sort and binary search
 * Let size of A1[] be m and size of A2[] be n.
 * 1) Create a temporary array temp of size m and copy contents of A1[] to it.
 * 2) Create another array visited[] of size m to mark if those elements in temp[] which are copied to A1[].
 * 3) Sort temp[]
 * 4) For each element of A2, binary search for all occurrences of A2[i] in temp[], if present then copy all occurrences
 * to A1[]. Also mark the copied elements visited[]. Finally copy all unvisited elements from temp[] to A1[].
 * Time complexity is O(mLogm + nLogm).
 *
 * Method 2: Use BST
 * Same as above, except using a BST to sort array1 instead of array. Each tree node keeps the key and keep track of
 * number of occurrences of the key. Time complexity is same as above
 *
 * Method 3: Use hash
 * Use hash map to store each key and its count of occurrences for array1. Traverse array2 to put keys present in array1
 * in order, using the hash. Sort the remaining keys and put them to array1.
 * Time complexity in worst case is O(mlogm+m+n)
 *
 * Method 4: Write a customized compared method
 */
public class RelativeSort {
    public static void main(String[] args) {
        int[] array1 = {2, 1, 2, 5, 7, 1, 9, 3, 6, 8, 8};
        int[] array2 = {2, 1, 8, 3};
        relativeSort(array1, array2);
        //relativeSort2(array1, array2);
    }

    static void relativeSort(int[] array1, int[] array2) {
        int m = array1.length;
        int[] temp = Arrays.copyOf(array1, m);
        boolean[] visited = new boolean[m];
        Arrays.sort(temp);
        int low, high, key, index = 0;
        for (int i = 0; i < array2.length; i++) {
            key = array2[i];
            int keyIndex = Arrays.binarySearch(temp, key);
            if (keyIndex != -1) {
                low = keyIndex;
                while (low >= 0 && temp[low] == key) {
                    visited[low] = true;
                    low--;
                }
                high = keyIndex;
                while (high < m && temp[high] == key) {
                    visited[high] = true;
                    high++;
                }
                for (int j = low+1; j < high; j++) {
                    array1[index] = temp[j];
                    ++index;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            if (!visited[i]) {
                array1[index] = temp[i];
                index++;
            }
        }
        System.out.println(Arrays.toString(array1));
    }

    static void relativeSort2(int[] array1, int[] array2) {
        RelativeComparator comparator = new RelativeComparator(array2);
        List<Integer> containerArray = Arrays.stream(array1).boxed().collect(Collectors.toList());
        Collections.sort(containerArray, comparator);
        System.out.println(containerArray);
    }

    static class RelativeComparator implements Comparator<Integer> {
        Map<Integer, Integer> priorities = new HashMap<>();
        RelativeComparator(int[] array) {
            for (int i = 0; i < array.length; i++)
                priorities.put(array[i], i);
        }
        @Override
        public int compare(@NonNull Integer n1, @NonNull Integer n2) {
            Integer priority1 = priorities.get(n1);
            Integer priority2 = priorities.get(n2);
            if (priority1 != null && priority2 != null)
                return priority1 - priority2;
            if (priority1 != null && priority2 == null)
                return -1;
            if (priority1 == null && priority2 != null)
                return 1;
            return n1-n2;
        }
    }
}
