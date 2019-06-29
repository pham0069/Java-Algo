package algorithm.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.hackerrank.com/challenges/icecream-parlor/problem
 *
 * Given array arr[] and a number m
 * Find 2 indexes (1-based) of 2 elements whose sum is m
 *
 * Constraints:
 * 0<= arr[i] <= 10^4
 */
public class IceCreamParlor {
    // approach1- using hashmap
    static int[] icecreamParlorA1(int m, int[] arr) {
        int result[] = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int x = arr[i];
            int y = m - x;
            Integer j = map.get(y);
            if (j != null) {
                result[0] = j + 1;
                result[1] = i + 1;
                break;
            }
            map.put(x, i);

        }
        return result;
    }

    // approach2 - using array
    static int[] icecreamParlorA2(int m, int[] arr) {
        int result[] = new int[2];
        int n = arr.length;
        int frequency[] = new int[10001];

        Arrays.fill(frequency, -1);

        for (int i = 0; i < n; i++) {
            int x = arr[i];
            int y = m - x;

            if (y >= 0) {

                int j = frequency[y];

                if (j != -1) {
                    result[0] = j + 1;
                    result[1] = i + 1;
                    break;
                }
            }

            frequency[x] = i;
        }

        return result;

    }

}
