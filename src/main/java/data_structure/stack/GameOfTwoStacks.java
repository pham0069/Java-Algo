package data_structure.stack;

import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/game-of-two-stacks/problem
 *
 * Alexa has two stacks of non-negative integers, stack A={a[0],..., a[n-1]} and stack B={b[0],..., b[m-1]} where index
 * 0 denotes the top of the stack. Alexa challenges Nick to play the following game:
 * In each move, Nick can remove one integer from the top of either stack A or stack B.
 * Nick keeps a running sum of the integers he removes from the two stacks.
 * Nick is disqualified from the game if, at any point, his running sum becomes greater than some integer x given at the
 * beginning of the game. Nick's final score is the total number of integers he has removed from the two stacks.
 * Given A, B, and x for g games, find the maximum possible score Nick can achieve (i.e., the maximum number of integers
 * he can remove without being disqualified) during each game and print it on a new line.
 *
 * Similar to EqualStacks, store the stack with sum of numbers from top to all indices of stack. For each sum S1 of 1
 * stack smaller than x, do binary search to find the max sum S2 from the other stack such that S1 + S2. Update max
 * number of integers removed if necessary.
 *
 *
 * Note: its not difficult to but to get correct result, need to note:
 * 1. Stack may contain zero, need to handle the case of getting max index of element upper bounded by a value
 * 2. Sum of all elements in a stack could be big value. Need to store it in long to avoid overflow
 * 3. Need to consider the case that we only extract from one stack only as well
 */
public class GameOfTwoStacks {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int game = Integer.parseInt(scanner.nextLine().trim());

        for (int i = 0; i < game; i++) {
            String[] nmx = scanner.nextLine().split(" ");
            int n = Integer.parseInt(nmx[0].trim());
            int m = Integer.parseInt(nmx[1].trim());
            int x = Integer.parseInt(nmx[2].trim());

            int[] a = new int[n];
            String[] aItems = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                int aItem = Integer.parseInt(aItems[j].trim());
                a[j] = aItem;
            }

            int[] b = new int[m];
            String[] bItems = scanner.nextLine().split(" ");
            for (int j = 0; j < m; j++) {
                int bItem = Integer.parseInt(bItems[j].trim());
                b[j] = bItem;
            }

            int result = getMaxNumbersRemovable(a, b, x);
            System.out.println(result);
        }
    }

    static int getMaxNumbersRemovable(int[] a, int[] b, int x) {
        long[] accSumA = getAccumulativeSum(a);
        long[] accSumB = getAccumulativeSum(b);

        //equivalent to get max index extracted from single stack a or b
        int maxRemoved = Math.max(binarySearchUpperBound(accSumA, x)+1, binarySearchUpperBound(accSumB, x)+1);

        for (int aIndex = 0; aIndex < a.length; aIndex++) {
            if (accSumA[aIndex] > x)
                break;
            long sumBThreshold = x - accSumA[aIndex];
            int bIndex = binarySearchUpperBound(accSumB, sumBThreshold);
            if (bIndex < 0) {
                break;
            }
            maxRemoved = Math.max(maxRemoved, aIndex+bIndex+2);
        }

        return maxRemoved;
    }

    /**
     * Given non-decreasing ordered array, find the largest element index that is smaller or equal to given key
     * If all elements are larger than key, return -1 as not found index
     * @param array
     * @param key
     * @return
     */
    static int binarySearchUpperBound(long[] array, long key) {
        int low = 0, high = array.length-1, mid =(low+high)/2;
        if (array[low] > key)
            return -1;
        while (low <= high) {
            if (array[mid] == key) {
                //to ensure returning max index satisfying the condition
                while (mid < (array.length-1) && array[mid] == array[mid+1])
                     mid++;
                return mid;
            }
            else if (array[mid] < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        //to ensure returning max index satisfying the condition
        while (high < (array.length-1) && array[high] == array[high+1])
            high++;
        return high;
    }

    static long[] getAccumulativeSum(int[] array) {
        long[] accumulativeSum = new long[array.length];
        accumulativeSum[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            accumulativeSum[i] = accumulativeSum[i-1] + array[i];
        }
        return accumulativeSum;
    }
}
