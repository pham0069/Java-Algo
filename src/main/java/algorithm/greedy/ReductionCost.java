package algorithm.greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Given an integer array. We can take a reduction operation on the array by replacing 2 distinct elements array[i] and
 * array[j] with their sum. The cost of this operation is array[i] + array[j].
 * Find the min total cost to reduce the array to single element
 *
 * For example, input = {4, 4, 4, 4, 6}. Min cost is 52 by taking following operations:
 * 1. Replace 4 and 4 by 8 -> {8, 4, 4, 6}
 * 2. Replace 4 and 4 by 8 -> {8, 8, 6}
 * 3. Replace 6 and 8 by 14 -> {14, 8}
 * 4. Replace 14 and 8 by 22 -> {22}
 * The total cost is 8 + 8 + 14 + 22 = 52.
 */
public class ReductionCost {
    public static void main(String[] args) {
        int[] input = {4, 4, 4, 4, 6};
        System.out.println(reduceCost(input));
    }

    static long reduceCost(int[] input) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        Arrays.stream(input).forEach(i -> heap.add(i));
        long totalCost = 0;
        while (heap.size() > 1) {
            int min = heap.poll();
            int nextMin = heap.poll();
            int cost = min + nextMin;
            totalCost += cost;
            heap.add(cost);
        }
        return totalCost;
    }
}
