package algorithm.sort3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.hackerrank.com/challenges/lilys-homework/problem
 *
 * Lily is given a homework regarding arrays.
 * Consider an array of n distinct integers, a[0..n-1]
 * Lily can swap any two elements of the array any number of times.
 * An array is beautiful if the sum of |a[i]-a[i-1]| for i = 1..n-1  is minimal.
 * Lily needs to determine what is the minimum number of swaps to transform a given array into a beautiful array.
 *
 * For example, a = [7, 15, 12, 3]
 * One beautiful array is [3, 7, 12, 15]
 * The transformation can be achieved in 2 swaps
 * (3, 7) -> [3, 15, 12, 7]
 * (7, 15] -> [3, 7, 12, 15]
 *
 * Properties of absolute: |a+b| >= |a| + |b|
 * |a[i+1]-a[i]| +  |a[i]-a[i-1]| >= |a[i+1]-a[i]|
 * Sum of all absolute value >= |a[n-1\-a[0]|
 * Min value happens if array a is sorted in ascending or descending order
 *
 * The problem becomes determining
 * - X = min swaps needed to transform the given array into ascending order
 * - Y = ...                                                descending order
 * - result = min (X, Y)
 *
 * To make it easy, we can transform the element value into its rank in array (from 1 to n)
 * For example, [7, 15, 12, 3] is transformed
 * - to ascending rank array [2, 4, 3, 1]
 * since 3 is rank 1, 7 is rank 2, 12 is rank 3, ... (in ascending order)
 * - to descending rank array [3, 1, 2, 4]
 * since 15 is rank 1, 12 is rank 2, 7 is rank 3, 5 is rank 4 (in descending order)
 *
 * This is equivalent to:
 * - X = min swaps to transform [2, 4, 3, 1] to [1, 2, 3, 4]
 * - Y = min swaps to transform [3, 1, 2, 4] to [1, 2, 3, 4]
 *
 * We can reuse the code in MinimumSwaps2 to get X and Y
 */
public class LilyHomework {
    public static void main(String[] args) {
        System.out.println(lilysHomework(new int[] {3, 4, 2, 5, 1}));
    }
    static int lilysHomework(int[] arr) {
        Map<Integer, Integer> originalValueToIndexMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            originalValueToIndexMap.put(arr[i], i);
        }

        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);

        return Math.min(minimumSwapsToAscending(originalValueToIndexMap, sorted),
                minimumSwapsToDescending(originalValueToIndexMap, sorted));
    }

    static int minimumSwapsToAscending(Map<Integer, Integer> originalValueToIndexMap, int[] sorted) {
        int[] arr = new int[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            int originalIndex = originalValueToIndexMap.get(sorted[i]);
            arr[originalIndex] = i+1;
        }

        return minimumSwaps(arr);
    }

    static int minimumSwapsToDescending(Map<Integer, Integer> originalValueToIndexMap, int[] sorted) {
        int n = sorted.length;
        int[] arr = new int[n];
        for (int i = 0; i < sorted.length; i++) {
            int originalIndex = originalValueToIndexMap.get(sorted[i]);
            arr[originalIndex] = n - i;
        }

        return minimumSwaps(arr);
    }

    //copy MinimumSwaps2 class
    static int minimumSwaps(int[] arr) {
        int swaps = 0;
        boolean[] visited = new boolean[arr.length];
        int entry, count, current;
        for (int i = 0; i < arr.length; i++) {
            if (visited[i]|| arr[i] == i+1) {
                continue;
            }
            entry = i;
            current = arr[entry]-1;
            visited[entry] = true;
            visited[current] = true;
            count = 2;
            while(arr[current]!= entry+1) {
                current = arr[current]-1;
                visited[current] = true;
                count ++;
            }
            swaps += count-1;
        }
        return swaps;
    }
}
