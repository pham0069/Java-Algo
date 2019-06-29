package algorithm.search;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * https://www.hackerrank.com/challenges/missing-numbers/problem
 *
 * Given two arrays of integers, find which elements in the second array are missing from the first array.
 *
 * Example
 * arr = [7, 2, 5, 3, 5, 3]
 * brr = [7, 2, 5, 4, 6, 3, 5, 3]
 *
 * The brr array is the orginal list. The numbers missing are [4, 6].
 *
 * Notes
 *
 * 1, If a number occurs multiple times in the lists, you must ensure that the frequency of that number in both lists is the same.
 * If that is not the case, then it is also a missing number.
 * 2, Return the missing numbers sorted ascending.
 * 3, Only include a missing number once, even if it is missing multiple times.
 * 4, The difference between the maximum and minimum numbers in the original list is less than or equal to .
 */
public class MissingNumbers {
    static int[] missingNumbers(int[] arr, int[] brr) {
        Map<Integer, Integer> af = getFrequency(arr);
        Map<Integer, Integer> bf = getFrequency(brr);
        List<Integer> missingNumbers = new ArrayList<>();
        for (int b: bf.keySet()) {
            if (!af.containsKey(b) || af.get(b) < bf.get(b)) {
                missingNumbers.add(b);
            }
        }

        Collections.sort(missingNumbers);
        int[] result = new int[missingNumbers.size()];
        for (int i = 0; i < missingNumbers.size(); i++) {
            result[i] = missingNumbers.get(i);
        }

        return result;
    }

    static Map<Integer, Integer> getFrequency(int[] brr) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int i : brr) {
            if (frequency.containsKey(i)) {
                frequency.put(i, frequency.get(i)+1);
            } else {
                frequency.put(i, 1);
            }
        }
        return frequency;
    }

    public static void main(String[] args){
        System.out.println(missingNumbers(new int[]{2, 3}, new int[]{1, 2, 3}));
    }
}
