package algorithm.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/playing-with-numbers/problem
 *
 * Given an array of integers, you must answer a number of queries.
 * Each query consists of a single integer, x, and is performed as follows:
 *
 * 1. Add x to each element of the array, permanently modifying it for any future queries.
 * 2. Find the absolute value of each element in the array and print the sum of the absolute values on a new line.
 *
 * Tip: The Input/Output for this challenge is very large,
 * so you'll have to be creative in your approach to pass all test cases.
 *
 * Constraints
 * 1 <= n <= 5*10^5
 * 1 <= q <= 5*10^5
 * -2000 <= arr[i] <= 2000
 * -2000 <= queries[j] <= 2000
 *
 *
 * a[0..n-1]
 * Case 1: x >= 0
 * - a >= 0 -> |a+x| += x
 * - -x < a < 0 -> |a+x| + |a| = x
 * - a <= -x -> |a+x| -=x
 *
 * Case 2: x <= 0
 * - a < 0 -> |a+x| -= x
 * - 0 < a < -x -> |a+x| + \a| = -x
 * - a > -x -> |a+x| += x
 *
 * Tricky:
 * original array and accumulative sum can be stored in long
 * accumulative x and result could be long (not int)
 *
 */
public class AbsoluteElementSums {

    static int[] getAccumulativeSum(int[] arr) {
        int[] sum = new int[arr.length];
        sum[0] = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i-1] + Math.abs(arr[i]);
        }
        return sum;
    }

    static long[] playingWithNumbers(int[] arr, int[] queries) {
        Arrays.sort(arr);
        int[] sum = getAccumulativeSum(arr);

        int n = arr.length;
        long[] result = new long[queries.length];
        int i1, i2;
        long x = 0, s1, s2, s3;

        for (int i = 0; i < queries.length; i++) {
            x += queries[i];
            if (x == 0) {
                result[i] = sum[n-1];
            } else if (x > 0) {
                i1 = binarySearchLessOrEqualToKey(arr, -x);
                i2 = binarySearchGreaterOrEqualToKey(arr, 0);
                i2 = (i2==-1) ? n : i2;

                s1 = (i1==-1) ? 0 : sum[i1];            // 0..i1 <= -x
                s3 = (i2==n) ? 0 : (sum[n-1]-sum[i2-1]);  // i1+1..i2-1 <-x, > 0
                s2 = sum[n-1] - s1 - s3;                // i2..n-1 >= 0
                result[i] = (s1 - (i1+1)*x) + ((i2-1-i1)*x- s2) + (s3 + (n-i2)*x);
            } else {
                i1 = binarySearchLessOrEqualToKey(arr, 0);
                i2 = binarySearchGreaterOrEqualToKey(arr, -x);
                i2 = (i2==-1) ? n :i2;

                s1 = (i1==-1) ? 0 : sum[i1];            // 0..i1 <= 0
                s3 = (i2==n) ? 0 : (sum[n-1]-sum[i2-1]);  // i1+1..i2-1 <-x, > 0
                s2 = sum[n-1] - s1 - s3;                // i2..n-1 >= -x
                result[i] = (s1 - (i1+1)*x) + ((i2-1-i1)*(-x) - s2) + (s3 + (n-i2)*x);
            }
        }

        return result;
    }

    static int binarySearchLessOrEqualToKey(int[] array, long key) {
        if (key > Integer.MAX_VALUE) {
            return array.length-1;
        }
        return binarySearchLessOrEqualToKey(array, 0, array.length-1, (int)key);
    }

    static int binarySearchGreaterOrEqualToKey(int[] array, long key) {
        if (key > Integer.MAX_VALUE) {
            return -1;
        }
        return binarySearchGreaterOrEqualToKey(array, 0, array.length-1, (int)key);
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

    static int binarySearchGreaterOrEqualToKey(int[] array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array[high] < key)
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
            foundIndex = low;
        //to ensure returning smallest index satisfying the condition
        while (foundIndex > start && array[foundIndex - 1] >= key) {
            foundIndex--;
        }
        return foundIndex;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] queries = new int[q];

        String[] queriesItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < q; i++) {
            int queriesItem = Integer.parseInt(queriesItems[i]);
            queries[i] = queriesItem;
        }

        long[] result = playingWithNumbers(arr, queries);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);

            if (i != result.length - 1) {
                System.out.println();
            }
        }

        scanner.close();
    }

}