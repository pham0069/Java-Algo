package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/marcs-cakewalk/problem
 *
 * Marc loves cupcakes, but he also likes to stay fit. Each cupcake has a calorie count, and Marc can walk a distance to
 * expend those calories. If Marc has eaten j cupcakes so far, after eating a cupcake with C calories, he must walk at
 * least 2^j*C miles to maintain his weight.
 * For example, if he eats 3 cupcakes with calorie counts in the following order: 5, 10, 7, the miles he will need to
 * walk are 2^0*5 + 2^1*10 + 2^2*7 = 53. This is not the minimum, though, so we need to test other orders of consumption.
 * In this case, our minimum miles is calculated as 2^0*10 + 2^1*7 + 2^2*5 = 44.
 * Given the individual calorie counts for each of the cupcakes, find and print a long integer denoting the minimum number
 * of miles Marc must walk to maintain his weight. Note that he can eat the cupcakes in any order.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format
 * The first line contains an integer n, the number of cupcakes in calorie.
 * The second line contains n space-separated integers calorie[i].
 *
 * Constraints: 1<= n <= 40, 1<= c[i] <= 1000
 *
 * Output Format
 * Print a long integer denoting the minimum number of miles Marc must walk to maintain his weight.
 *
 * Sample Input 0
 3
 1 3 2
 * Sample Output 0
 11
 * Explanation: eating the cakes in the order of calorie 3, 2, 1 gives the lowest calories of 11
 * ---------------------------------------------------------------------------------------------------------------------
 * Sort the cake calories in descending order. The highest-calorie cake should be consumed first to minimize the distance
 * that Marc needs to walk.
 * Time complexity is O(nlogn) for sorting
 */
public class MarcCakewalk {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] calories = new int[n];
        for (int i = 0; i < n; i++){
            calories[i] = sc.nextInt();
        }
        Arrays.sort(calories);
        long totalWalk = 0;
        long factor = 1;
        for (int i = n-1; i >= 0; i--){
            totalWalk += factor * calories[i];
            factor *= 2;
        }
        System.out.println(totalWalk);

    }
}
