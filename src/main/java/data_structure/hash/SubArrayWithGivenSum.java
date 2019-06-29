package data_structure.hash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.geeksforgeeks.org/find-subarray-with-given-sum/
 * https://www.geeksforgeeks.org/find-subarray-with-given-sum-in-array-of-integers/
 *
 * Given an unsorted array of nonnegative integers, find a continuous subarray which adds to a given number.
 *
 * Example 1:
 * Input: arr[] = {1, 4, 20, 3, 10, 5}, sum = 33
 * Ouptut: Sum found between indexes 2 and 4
 * Example 2:
 * Input: arr[] = {1, 4, 0, 0, 3, 10, 5}, sum = 7
 * Output: Sum found between indexes 1 and 4
 * Example 3:
 * Input: arr[] = {1, 4}, sum = 0
 * Output: No subarray found
 *
 * There may be more than one subarrays with sum as the given sum. The following solutions print first such subarray.
 * ---------------------------------------------------------------------------------------------------------------------
 * Method 1: Brute force
 * Calculate sum of all possible subarrays and print the sum equal to the given sum
 * Time complexity is O(n^2)
 *
 * Method 2: Adjust start and end element of subarray such that their sum equal to given sum
 * Can handle array with non-negative elements only
 * Time complexity is O(n), since each element is added at most once and removed at most once
 *
 * Method 3: Use hash to store all the sums of of subarray 0 --> i (for i = 0..n-1)
 * It is noted that sum of subarray i --> j = subarray sum 0 --> j - subarray sum 0 --> i-1
 * By using hash, we can quickly find if any previous subarrays has sum equal to x - currentSum
 * Time complexity is O(n). Space complexity is O(n).
 */
public class SubArrayWithGivenSum {
    public static void main(String[] args) {
        int[] array = {1, 4, 20, 3, 10, 5};
        int sum = 33;
        System.out.println(subArrayWithGivenSum(array, sum));
        System.out.println(subArrayWithGivenSum2(array, sum));
        System.out.println(subArrayWithGivenSum3(array, sum));
    }

    static Pair subArrayWithGivenSum(int[] array, int x) {
        int sum;
        for (int i = 0; i < array.length-1; i++) {
            sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];
                if (sum == x){
                    return new Pair(i, j);
                }
            }
        }
        return new Pair(-1, -1);
    }

    /**
     * Only works for non-negative array
     */
    static Pair subArrayWithGivenSum2(int[] array, int x) {
        int sum = 0, start = 0;
        for (int end = 0; end < array.length-1; end++) {
            sum += array[end];
            while (sum > x && start < end) {
                sum -= array[start++];
            }
            if (sum == x)
                return new Pair(start, end);
        }
        return new Pair(-1, -1);
    }

    /**
     * Only works for non-negative array
     */
    static Pair subArrayWithGivenSum3(int[] array, int x) {
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (sum == x) {
                return new Pair(0, i);
            }
            if (map.containsKey(sum - x)) {
                return new Pair(map.get(sum-x) + 1, i);
            }
            map.put(sum, i);

        }
        return new Pair(-1, -1);
    }

    @Data @ToString
    static class Pair {
        final int start;
        final int end;
    }
}
