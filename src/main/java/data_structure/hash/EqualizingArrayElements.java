package data_structure.hash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://www.geeksforgeeks.org/minimize-count-of-divisions-by-d-to-obtain-at-least-k-equal-array-elements/
 *
 * Given an array A[ ] of size N and two integers K and D
 * The task is to calculate the minimum possible number of operations required to obtain at least K equal array elements.
 * Each operation involves replacing an element A[i] by A[i] / D. This operation can be performed any number of times.
 *
 * For example.
 * Input: N = 5, A[ ] = {1, 2, 3, 4, 5}, K = 3, D = 2
 * Output: 2
 * Explanation:
 * Step 1: Replace A[3] by A[3] / D, i.e. (4 / 2) = 2. Hence, the array modifies to {1, 2, 3, 2, 5}
 * Step 2: Replace A[4] by A[4] / D, i.e. (5 / 2) = 2. Hence, the array modifies to {1, 2, 3, 2, 2}
 * Hence, the modified array has K(= 3) equal elements.
 * Hence, the minimum number of operations required is 2.
 *
 * Input: N = 4, A[ ] = {1, 2, 3, 6}, K = 2, D = 3
 * Output: 1
 * Explanation:
 * Replacing A[3] by A[3] / D, i.e. (6 / 3) = 2. Hence, the array modifies to {1, 2, 3, 2}.
 * Hence, the modified array has K(= 2) equal elements.
 * Hence, the minimum number of operations required is 1.
 *
 *
 */
public class EqualizingArrayElements {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>();
        arr.add(63);
        arr.add(25);
        arr.add(30);
        arr.add(33);
        System.out.println(minOperations(arr, 2, 2));
    }

    static class Division {
        int value;
        int steps;
        Division (int value, int steps) {
            this.value = value;
            this.steps = steps;
        }
    }

    static int minOperations(List<Integer> arr, int threshold, int d) {
        Map<Integer, List<Integer>> valueToSteps = new HashMap<>();
        for (int a : arr) {
            List<Division> divisions = getAllDivisions(a, d);
            for (Division division : divisions) {
                valueToSteps.computeIfAbsent(division.value, v -> new ArrayList<>());
                valueToSteps.get(division.value).add(division.steps);
            }
        }

        int minOperations = Integer.MAX_VALUE;
        for (int value : valueToSteps.keySet()) {
            List<Integer> stepsList = valueToSteps.get(value);
            if (stepsList.size() < threshold) {
                continue;
            }
            Collections.sort(stepsList);
            int ops = 0;
            for (int i = 0; i < threshold; i++) {
                ops += stepsList.get(i);
            }
            minOperations = Math.min(minOperations, ops);
        }
        return minOperations;
    }

    static List<Division> getAllDivisions(int a, int d) {
        List<Division> result = new ArrayList<>();
        int value = a;
        int steps = 0;
        while (value != 0) {
            result.add(new Division(value, steps));
            value /= d;
            steps ++;
        }
        result.add(new Division(value, steps));
        return result;
    }
}
