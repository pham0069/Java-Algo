package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/maximum-perimeter-triangle/problem
 *
 * Given an array of stick lengths, use 3 of them to construct a non-degenerate triange with the maximum possible
 * perimeter. Print the lengths of its sides as 3 space-separated integers in non-decreasing order.
 *
 * If there are several valid triangles having the maximum perimeter:
 *
 * Choose the one with the longest maximum side.
 * If more than one has that maximum, choose from them the one with the longest minimum side.
 * If more than one has that maximum as well, print any one them.
 * If no non-degenerate triangle exists, print -1.
 *
 * For example, assume there are stick lengths = {1, 2, 3, 4, 5, 10}. The triplet (1, 2, 3) will not form a triangle.
 * Neither will (4, 5, 10) or (2, 3, 5), so the problem is reduced to (2, 3, 4) and (3, 4, 5). The longer perimeter is
 * 3+4+5=12.
 *
 * Input Format
 *
 * The first line contains single integer n, the size of array sticks.
 * The second line contains n space-separated integers stick[i], each a stick length.
 *
 * Constraints:
 * 3 <=n <=50
 * 1 <=sticks[i] <= 10^9
 *
 * Output Format
 *
 * Print the lengths of the 3 chosen sticks as space-separated integers in non-decreasing order.
 *
 * If no non-degenerate triangle can be formed, print -1.
 *
 * Sample Input 0
 *
 * 5
 * 1 1 1 3 3
 * Sample Output 0
 *
 * 1 3 3
 * Explanation 0
 *
 * There are 2 possible unique triangles: (1, 1, 1) and (1, 3, 3)
 *
 * The second triangle has the largest perimeter, so we print its side lengths on a new line in non-decreasing order.
 *
 * Sample Input 1
 *
 * 3
 * 1 2 3
 * Sample Output 1
 *
 * -1
 * Explanation 1
 *
 * The triangle (1, 2, 3)  is degenerate and thus can't be constructed, so we print -1 on a new line.
 *
 * Sample Input 2
 *
 * 6
 * 1 1 1 2 3 5
 * Sample Output 2
 *
 * 1 1 1
 * Explanation 2
 *
 * The triangle (1,1,1) is the only valid triangle.
 *
 */
public class MaximumPerimeterTriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] sticks = new int[n];
        for (int i = 0; i < n; i++){
            sticks[i] = sc.nextInt();
        }
        Arrays.sort(sticks);
        int[] result = getMaxPerimeterTriangle(sticks, n);
        if (result != null)
            System.out.println(sticks[result[0]] + " " + sticks[result[1]] + " " + sticks[result[2]]);
        else
            System.out.println(-1);
    }

    /**
     * Time complexity in worst case is O(nlogn)
     * @param sticks
     * @param n
     * @return
     */
    static int[] getMaxPerimeterTriangle(int[] sticks, int n) {
        for (int a3 = n-1; a3 >= 2; a3--){
            //second largest side should have length >= largest side/2
            int a2 = binarySearchLowerBound(sticks, 0, a3-1, sticks[a3]/2);
            int a1 = a2-1;
            if (a2 > 0 && sticks[a1] > sticks[a3]-sticks[a2])
                return new int[] {a1, a2, a3};
        }
        return null;
    }

    /**
     * Find the largest index in inclusive range start-end with value larger or equal to key
     * @param array
     * @param start
     * @param end
     * @param key
     * @return
     */
    static int binarySearchLowerBound(int[] array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array[high] < key)
            return -1;
        while (low <= high) {
            if (array[mid] == key) {
                //to ensure returning max index satisfying the condition
                while (mid < end && array[mid + 1] >= key)
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
        /*
         * shameful stuck code, may work here but stuck in general
        while (low < end) {
            if (array[low + 1] >= key)
                low++;

        }
        */
        while (low < end && array[low + 1] >= key)
            low++;

        return low;
    }

    /**
     * Time complexity is O(n^2)
     * @param sticks
     * @param n
     * @return
     */
    static int[] naiveGetMaxPerimeterTriangle(int[] sticks, int n) {
        long max = 0;
        int[] result = new int[3];
        for (int a3 = 2; a3 < n; a3++){
            for (int a2 = 1; a2 < a3; a2++){
                int a1 = a2-1;
                if (sticks[a1] <= sticks[a3]-sticks[a2])
                    continue;
                if ((long) sticks[a1] + sticks[a2] + sticks[a3] >= max){
                    result[0] = a1;
                    result[1] = a2;
                    result[2] = a3;
                    max = (long) sticks[a1] + sticks[a2] + sticks[a3];
                }
            }
        }
        if (max != 0)
            return result;
        return null;
    }
}
