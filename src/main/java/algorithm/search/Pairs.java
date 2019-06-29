package algorithm.search;

import java.util.HashSet;
import java.util.Set;

/**
 * https://www.hackerrank.com/challenges/pairs/problem
 *
 * Given an array of integers and a target value, determine the number of pairs of array elements that have a difference
 * equal to the target value.
 *
 * For example,
 * k = 1
 * arr = [1,2, 3, 4]
 * There are three values that differ by 1: (1, 2), (2, 3) , and (3, 4). Return 3.
 *
 * Constraints:
 * array elements are distinct
 * 2 <= n <= 10^5
 * 0 < k < 10^9
 * 0 < arr[i] < 2^31 - 1
 */
public class Pairs {
    // Complete the pairs function below.
    static int pairs(int k, int[] arr) {
        Set<Integer> set = new HashSet<>();
        int pairs = 0;
        for (int i : arr) {
            if(set.contains(i-k)) {
                pairs ++;
            }
            if (set.contains(i+k)) {
                pairs ++;
            }
            set.add(i);
        }
        return pairs;

    }

    public static void main(String[] args) {
        System.out.println(pairs(1, new int[] {1, 2, 3, 4}));
    }
}
