package algorithm.search;

import algorithm.greedy.CloudyDay;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/triple-sum/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=search
 *
 * Given 3 arrays a, b, c of different sizes
 * Find the number of distinct triplets (p, q, r) where  p in a, q in b, r in c and , satisfying the criteria:
 * p <= q
 * q >= r
 *
 * For example, given a = (3,5,7), b = {3, 6) and c = (4, 6, 9), we find four distinct triplets:
 * (3, 6, 4), (3, 6, 6), (5, 6, 4), (5, 6, 6)
 *
 * Constraints
 * 1 <= lena, lenb, lenc <= 10^5
 * 1 <= all elements in a, b, c <= 10^8
 */

public class TripleSum {

    static long triplets(int[] a, int[] b, int[] c) {
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);

        a = removeDuplicate(a);
        b = removeDuplicate(b);
        c = removeDuplicate(c);

        long count = 0;
        int aIndex, cIndex;
        for (int q: b) {
            aIndex = binarySearchLessOrEqualToKey(a, q);
            if (aIndex >= 0) {
                cIndex = binarySearchLessOrEqualToKey(c, q);
                if (cIndex >= 0) {
                    //beware not to use (aIndex+1)*(cIndex+1) since it could be overflown when calculating with int
                    count += Long.valueOf(aIndex+1)*(cIndex+1);
                }
            }
        }
        return count;
    }

    // given sorted array
    // return an array with duplicates value removed
    // for example (1, 2, 2, 3) -> (1, 2, 3)
    static int[] removeDuplicate(int[] array) {
        if (array.length == 0) {
            return array;
        }
        List<Integer> list = new ArrayList<>();
        list.add(array[0]);
        for (int i = 1; i < array.length; i++) {
            if (array[i] != array[i-1]) {
                list.add(array[i]);
            }
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    static int binarySearchLessOrEqualToKey(int[] array, int key) {
        return binarySearchLessOrEqualToKey(array, 0, array.length-1, key);
    }

    // given sorted array a
    // return the largest index in the range [start, end] such that value <= key
    // return -1 if all array elements > key
    static int binarySearchLessOrEqualToKey(int[] array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array[low] > key)
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array[mid] == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            } else if (array[mid] < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        if (foundIndex == -1)
            foundIndex = high;
        //to ensure returning largest index satisfying the condition
        while (foundIndex < end && array[foundIndex + 1] <= key) {
            foundIndex++;
        }
        return foundIndex;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String[] lenaLenbLenc = scanner.nextLine().split(" ");

        int lena = Integer.parseInt(lenaLenbLenc[0]);

        int lenb = Integer.parseInt(lenaLenbLenc[1]);

        int lenc = Integer.parseInt(lenaLenbLenc[2]);

        int[] arra = new int[lena];

        String[] arraItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lena; i++) {
            int arraItem = Integer.parseInt(arraItems[i]);
            arra[i] = arraItem;
        }

        int[] arrb = new int[lenb];

        String[] arrbItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenb; i++) {
            int arrbItem = Integer.parseInt(arrbItems[i]);
            arrb[i] = arrbItem;
        }

        int[] arrc = new int[lenc];

        String[] arrcItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < lenc; i++) {
            int arrcItem = Integer.parseInt(arrcItems[i]);
            arrc[i] = arrcItem;
        }

        long ans = triplets(arra, arrb, arrc);

        System.out.println(ans);

        scanner.close();

        //System.out.println(triplets(new int[]{1, 4, 5}, new int[]{2, 3, 3}, new int[]{1, 2, 3}));
    }
}
