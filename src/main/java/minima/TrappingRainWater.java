package minima;

import java.util.Arrays;

/**
 * https://www.geeksforgeeks.org/trapping-rain-water/
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
 *
 * Example 1:
 * Input: arr[]   = {2, 0, 2}
 * Output: 2
 * Explanation: Structure is like below. We can trap 2 units of water in the middle gap.

 |^| |^|
 |_|_|_|

 *
 * Example 2:
 * Input: arr[]   = {3, 0, 0, 2, 0, 4}
 * Output: 10
 * Explanation: Structure is like below. We can trap "3*2 units" of water between 3 an 2, "1 unit" on top of bar 2 and "3 units" between 2 and 4.
           |^|
 |^|       |-|
 |-|   |^| |-|
 |_|_ _|_|_|_|

 * Example 3:
 * Input: arr[] = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 * Output: 6
              |^|
      |^|     |-|^| |^|
 _|^|_|-|-|_|^|_|_|^|_|^|
 Trap "1 unit" between first 1 and 2, "4 units" between
 first 2 and 3 and "1 unit" between second last 1 and last 2
 */
public class TrappingRainWater {
    public static void main(String[] args) {
        //int[] array = {3, 0, 0, 2, 0, 4};//10
        //int[] array = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};//6
        int[] array = {1, 2, 3, 2, 1};
        System.out.println(getTrappedWater(array));
        System.out.println(getTrappedWater2(array));
        System.out.println(getTrappedWater3(array));
        System.out.println(getTrappedWater4(array));
    }

    /**
     * Intuitive approach: reduce the wall height one by one unit, from bottom to top
     * Each bar's unit removed is equivalent to a unit of water that the structure can store
     * Not good: complexity depends on height of wall, only works for int array
     * Note that this method modifies the original array to all-zeros ultimately
     */
    static int getTrappedWater(int[] originalArray) {
        int[] array = Arrays.copyOf(originalArray, originalArray.length);
        int i = 0, j = array.length-1;
        int total = 0;
        while (j-i > 2) {
            while (array[i] == 0)
                i++;
            while (array[j] == 0)
                j--;
            for (int k = i; k <= j; k++) {
                if(array[k] == 0)
                    total++;
                else
                    array[k]--;
            }
        }
        return total;
    }

    /**
     * Calculate the accumulated water element by element consider the amount of water on each bar,
     * using the formula to min(left[i], right[i]) - arr[i]
     * Time complexity is O(n). Space complexity is O(n)
     */
    static int getTrappedWater2(int[] array) {
        int n = array.length;
        // store the height of the tallest bar to the left of each bar including itself
        int left[] = new int[n];
        // store the height of the tallest bar to the right of each bar including itself
        int right[] = new int[n];
        // Fill left and right array
        left[0] = array[0];
        for (int i = 1; i < n; i++)
            left[i] = Math.max(left[i-1], array[i]);
        right[n-1] = array[n-1];
        for (int i = n-2; i >= 0; i--)
            right[i] = Math.max(right[i+1], array[i]);


        int total = 0;
        for (int i = 0; i < n; i++)
            total += Math.min(left[i],right[i]) - array[i];

        return total;
    }

    /**
     * Optimize space complexity by not necessarily hold the whole left and right array
     * Instead use 2 indices to traverse the array from top and bottom towards the mid
     * and 2 variables to store the maximum element on left and right
     * The strategy is to use leftMax and rightMax to determine min(left[i], right[i]) easily
     *
     */
    static int getTrappedWater3(int[] array) {
        int n = array.length;
        int total = 0;
        // maximum element on left and right
        int leftMax = 0, rightMax = 0;
        // indices to traverse the array
        int lo = 0, hi = n-1;

        while(lo <= hi) {
            if(array[lo] < array[hi]) {
                /**
                 * position lo cannot hold any water since it's the highest bar of all on the left
                 * min(left[lo], right[lo] = array[lo]
                 * min(left[lo], right[lo] - array[lo] = 0
                 */
                if(array[lo] > leftMax)
                    leftMax = array[lo];
                /**
                 * position lo can hold the water of leftMax - array[lo] since we have:
                 * leftMax = left[lo] <= rightMax <= right[lo]
                 * min(left[lo], right[lo]) = leftMax - array[lo]
                 */
                else
                    total += leftMax - array[lo];
                lo++;
            } else {
                if(array[hi] > rightMax)
                    rightMax = array[hi];
                else
                    total += rightMax - array[hi];
                hi--;
            }
        }

        return total;
    }

    /**
     * My approach with slight modification to make it more intuitive
     */
    static int getTrappedWater4(int[] array) {
        int n = array.length;
        int total = 0;
        int leftMax = array[0], rightMax = array[n-1];
        int lo = 1, hi = n-2;

        while(lo <= hi) {
            /**
             * change the condition here
             */
            if(leftMax <= rightMax) {
                if(array[lo] > leftMax)
                    leftMax = array[lo];
                else
                    total += leftMax - array[lo];
                lo++;
            } else {
                if(array[hi] > rightMax)
                    rightMax = array[hi];
                else
                    total += rightMax - array[hi];
                hi--;
            }
        }

        return total;
    }

}
