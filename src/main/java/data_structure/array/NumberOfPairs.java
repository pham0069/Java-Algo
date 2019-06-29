package data_structure.array;

import java.util.Arrays;

/**
 * https://www.geeksforgeeks.org/find-number-pairs-xy-yx/
 *
 * Given two arrays X[] and Y[] of positive integers
 * Find number of pairs such that x^y > y^x
 * where x is an element from X[] and y is an element from Y[].
 *
 * Naive approach is brute force. Time complexity is O(m*n)
 * Observation: If y > x then x^y > y^x with some exceptions.
 *
 * x^y > y^x
 * -> ln(x^y) > ln(y^x)
 * -> y*ln(x) > x*ln(y)
 * -> ln(x)/x > ln(y)/y
 * Consider f(x) = ln(x)/x. We need to find the condition for f(x) > f(y)
 * By taking limit and derivative, we have some properties of this function:
 * f(x) -> - infinity when x -> 0+
 * f(x) -> 0 when x -> infinity +
 * f'(x) = (1-ln(x))/x^2 = 0 when x = e
 * f(x) reaches maximum point of 1 when x = e
 * Thus:
 * - if x > e or x >= 3 (for integers), f(x) decreases when x increases
 * -> if x, y >= 3, f(x) > f(y) when x < y
 * - need to consider exceptions when x <= 2 or y <=2
 *  if x = 0, no y valid
 *  if x = 1, only y = 0
 *  if x = 2, y = 0, 1, >= 5 (all values, except 3, 4)
 *  if y = 0, all x > 0
 *  if y = 1, all x > 1
 *  if y = 2, only x = 3
 */
public class NumberOfPairs {
    public static void main(String[] args) {

    }

    /**
     * Given x, return number of values in y satisfying x^y > y^x
     * @param x
     * @param y
     * @param belowFiveCount
     * @return
     */
    static int numberOfPairs(int x, int[] y, int[] belowFiveCount) {
        //no y value can satisfy the condition
        if (x == 0)
            return 0;

        //only y = 0 satisfying the condition
        if (x == 1)
            return belowFiveCount[0];

        // Find number of elements in y[] with values greater than x
        // getting upper-bound of x with binary search
        int index = Arrays.binarySearch(y, x);
        int count = 0;
        // key not found, index = - insertionPoint - 1, i.e. insertionPoint = -(index + 1)
        // number of elements > x = y.length - insertionPoint
        if (index < 0) {
            index = Math.abs(index+1);
            count = y.length - index;
        } else {
            // key found, find the first first index that > x
            while (index< y.length && y[index]==x) {
                index++;
            }
            count = y.length - index;
        }

        // If we have reached here, then x must be greater than 1,
        // increase number of pairs for y=0 and y=1
        count += (belowFiveCount[0] + belowFiveCount[1]);

        // Decrease number of pairs for x=2 and (y=4 or y=3)
        if (x == 2)
            count -= (belowFiveCount[3] + belowFiveCount[4]);

        // Increase number of pairs for x=3 and y=2
        if (x == 3)
            count += belowFiveCount[2];

        return count;
    }

    static int numberOfPairs(int[] x, int[] y) {
        int belowFiveCount[] = new int[5];
        Arrays.sort(y);
        for (int i = 0; i < y.length; i++) {
            if (y[i] >= 5) {
                break;
            }
            belowFiveCount[y[i]] += 1;
        }

        int totalPairs = 0;
        for (int i = 0; i < x.length; i++) {
            totalPairs += numberOfPairs(x[i], y, belowFiveCount);
        }

        return totalPairs;
    }
}
