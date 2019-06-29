package minima;
import java.util.Scanner;

/**
 *https://www.hackerrank.com/challenges/candies/problem
 *
 * Alice is a kindergarten teacher. She wants to give some candies to the children in her class.  All the children sit
 * in a line and each of them has a rating score according to his or her performance in the class.  Alice wants to give
 * at least 1 candy to each child. If two children sit next to each other, then the one with the higher rating must get
 * more candies. If two children sitting next to each other have the same rank, they can get different number of candies.
 * Alice wants to minimize the total number of candies she must buy.
 * For example, assume her students' ratings are [4, 6, 4, 5, 6, 2]. She gives the students candy in the following minimal amounts: [1, 2, 1, 2, 3, 1]. She must buy a minimum of 10 candies.
 * n: an integer, the number of children in the class
 * arr: an array of integers representing the ratings of each student
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format:
 * The first line contains an integer, n, the number of children in the class.
 * Each of the next n lines contains an integer arr[i] indicating the rating of the student at position i.
 * Constraints: 1 <= n <= 10^5, 1 <= arr[i] <= 10^5
 * Output Format:
 * Output a single line containing the minimum number of candies Alice must buy.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample Input 0
 3
 1
 2
 2
 * Sample Output 0
 4
 * Explanation 0
 * Optimal distribution is 1, 2, 1, which makes the total number as 4
 *
 * Sample Input 1
 * 10
 * 2
 * 4
 * 2
 * 6
 * 1
 * 7
 * 8
 * 9
 * 2
 * 1
 * Sample Output 1
 * 19
 * Explanation 1
 * Optimal distribution is 1, 2, 1, , 1, 2, 3, 4, 2, 1, which makes the total number as 19
 * ---------------------------------------------------------------------------------------------------------------------
 * A solution is sweeping two times: forward to give an optimal number of candies that ensures increasing ranks interval
 * would be distributed with increasing candies, backward to fix the optimal number of candies for decreasing ranks
 * interval.
 * Imagine the rank-position graph as zig-zag line, Each local minima point should be given 1 candy. The points on the
 * right side of the valley is handled in the forward sweep. Those on the left side is handled in the backward sweep.
 *
 */
public class Candies {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] rank = new int[n];
        int[] candy = new int[n];
        for (int i = 0; i < n ;i ++){
            rank[i] = sc.nextInt();
        }
        candy[0] = 1;
        //forward sweep
        for (int i = 1; i < n ;i ++){
            if (rank[i] > rank[i-1])
                candy[i] = candy[i-1] + 1;
            else
                candy[i] = 1;
        }
        //backward sweep
        for (int i = n-2; i >= 0 ;i --){
            if (rank[i] > rank[i+1])
                candy[i] = Math.max(candy[i], candy[i+1] + 1);
        }
        long sum = 0;
        for (int i = 0; i < n ;i ++)
            sum += candy[i];
        System.out.println(sum);
    }
}
