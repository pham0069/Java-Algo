package data_structure.array;

import java.util.Arrays;

/**
 * https://www.geeksforgeeks.org/counting-inversions/
 * Straightforward way is brute-force. Consider all pairs of number. Time complexity is O(N^2)
 * One way is enhanced merge sort
 * Second way is using AVL tree
 * Third way is using BIT tree
 */
public class InversionCount {
    public static void main(String[] args) {
        int[] x = {5, 4, 3, 2};
        System.out.println(getInversionCountUsingMergeSort(x));
    }

    /**
     * Note that this method modifies the passed array
     * @param array
     * @return
     */
    static int getInversionCountUsingMergeSort(int[] array) {
        return sortAndCount(array, 0, array.length-1);
    }

    static int sortAndCount(int[] array, int left, int right) {
        int count = 0;
        if (left < right) {
            int mid = (left+right)/2;
            count += sortAndCount(array, left, mid);
            count += sortAndCount(array, mid+1, right);
            count += mergeAndCount(array, left, mid, right);
        }
        return count;
    }

    static int mergeAndCount(int[] array, int left, int mid, int right) {
        int count = 0;
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
                // first[i..n1-1] are > second[j], thus n1-i inversions paired with second[j]
                count += n1-i;
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

        return count;
    }

}
