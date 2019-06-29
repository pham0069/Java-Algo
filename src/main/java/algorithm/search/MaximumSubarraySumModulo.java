package algorithm.search;

import java.io.IOException;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * https://www.hackerrank.com/challenges/maximum-subarray-sum/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=search
 *
 * We define the following:
 *
 * - A subarray of array a of length n is a contiguous segment from a[i] through a[j] where 0 <= i <=j < n.
 * - The sum of an array is the sum of its elements.
 *
 * Given an n element array of integers, a, and an integer, m.
 * Determine the maximum value of the sum of any of its subarrays modulo m.
 *
 * For example, a = [1, 2, 3] and m = 2.
 * The following table lists all subarrays and their moduli:
 * 		        sum	%2
 * [1]		    1	1
 * [2]		    2	0
 * [3]		    3	1
 * [1,2]		3	1
 * [2,3]		5	1
 * [1,2,3]		6	0
 *
 * The max modulus is 1
 *
 * https://www.youtube.com/watch?v=u_ft5jCDZXk
 *
 * Brute-force approach: check all subarray O(n^2) timeout
 *
 * For example, a = [1, 2, 3, 4, 5, 6, 7, -8, 2, 12, 11], m = 15
 * index                0  1  2  3  4  5  6   7  8   9  10
 * value           (a)  1  2  3  4  5  6  7  -8  2  12  11
 * accumulative sum     1  3  6  10 15 21 28 20 22  34  45
 * acc. sum modulo (s)  1  3  6  10 0  6  13  5  7  4   0
 * s[i] = sum modulo of subarray a[0..i]
 *
 * The sum modulo of subarray a[i..j]
 * = (s[j]-s[i-1])%m if s[j] >= s[i-1]
 * = (s[j]-s[i-1] + m)%m otherwise
 *
 * Observation:
 * - if s[j] >= s[i-1] -> the sum modulo of subarray a[i..j] is smaller than that of subarray a[0..j]
 * -> no ned to consider such cases
 * - if s[j] < s[i-1] -> the sum modulo of subarray a[i..j] is optimised for s[i-1] min
 * That means we need to find max value > s[j] on the right of s[j]
 *
 * We can do this with BST.
 * Running index from 0 to n, we can build a BST of elements on the left gradually.
 * It takes O(n*logn), which is much better than brute-force algo.
 */
public class MaximumSubarraySumModulo {
    // brute force, O(n^2), of course fail time limit :(
    static long maximumSum2(long[] a, long m) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] % m;
        }
        long max = 0;
        for (int i = 0; i < a.length; i++) {
            long sum = 0;
            for (int j = i; j < a.length; j++) {
                sum = (sum +a[j]) %m;
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    static long maximumSum(long[] a, long m) {
        long[] s = getAccumulativeSumModuloArray(a, m);

        long max = s[0];
        NavigableSet<Long> searchTree = new TreeSet<>();
        searchTree.add(s[0]);
        Long higher;
        for (int i = 1; i < s.length; i++) {
            higher = searchTree.higher(s[i]);
            if (higher != null) {
                max = Math.max(max, (s[i]+m-higher)%m);
            } else {
                max = Math.max(max, s[i]);
            }
            searchTree.add(s[i]);
        }
        return max;
    }

    static long[] getAccumulativeSumModuloArray(long[] a, long m) {
        long[] s = new long[a.length];
        s[0] = a[0] % m;
        for (int i = 1; i < a.length; i++) {
            s[i] = (s[i-1] + a[i]) % m;
        }
        return s;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            long m = Long.parseLong(nm[1]);

            long[] a = new long[n];

            String[] aItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                long aItem = Long.parseLong(aItems[i]);
                a[i] = aItem;
            }

            long result = maximumSum(a, m);

            System.out.print(result);
        }

        scanner.close();
    }

}
