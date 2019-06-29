package algorithm.dynamic_proramming;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-cost/problem
 *
 * Given an array B of length n, find array A of same length such that
 * (1) 1<= A[i] <= B[i] for 1<=i<=n
 * (2) S = sum of |A[i] - A[i-1]| for 2 <= i<= n is maximized
 *
 * For example, if array B is {1, 2, 3}, the candidates for array A that satisfies (1) are:
 * {1, 1, 1}, {1, 1, 2}, {1, 1, 3}
 * {1, 2, 1}, {1, 2, 2}, {1, 2, 3}
 * The sum S is calculated as follows:
 * |1-1| + |1-1| = 0	|1-1| + |2-1| = 1	|1-1| + |3-1| = 2
 * |2-1| + |1-2| = 2	|2-1| + |2-2| = 1	|2-1| + |3-2| = 2
 * S max is 2.
 *
 * Input Format:
 * The first line contains the integer T, the number of test cases.
 * Each of the next T pairs of lines is a test case where:
 * - The first line contains an integer n, the length of  array B
 * - The next line contains n space-separated integers, which is B's content
 * Constraints:
 * 1<= T <=20, 1< n <= 10^5, 1<= B[i] <= 100
 * Output Format:
 * For each test case, print the maximum sum on a separate line.
 *
 * Sample Input
 2
 5
 10 1 10 1 10
 5
 100 2 100 2 100
 * Sample Output
 * 36
 * 396
 *
 * First, it is proved that S is maximized when A[i] is equal to 1 or B[i] (by contradiction)
 * Second, apply DP. Denote S[k] = Sigma(|A[i] - A[i-1]|) for 2 <= i<= k
 * U[k] = max S[k] given A[k] = 1
 * V[k] = max S[k] given A[k] = B[k]
 * Max (S[k]) = max (U[k], V[k])
 * To calculate max(S[k+1]), we calculate U[k+1], V[k+1] by the following formulas:
 * U[k+1] = max ((V[k]+|1-B[k]|), U[k])
 * V[k+1] = max ((V[k]+|B[k+1]-B[k]|), (U[k]+ |B[k+1]-1|))
 *
 * Time complexity is O(n).
 * Space complexity is O(1).
 * To reduce space complexity, we dont need to store the whole array for B, U, V.
 * We just need to store the previous u, v, b and the current b to calculate the current u, v
 */
public class SherlockAndCost {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0){
            int n = sc.nextInt();
            int bPrev = sc.nextInt();
            int uPrev = 0, vPrev = 0;
            int b, u, v;
            while (n-- > 1) {
                b = sc.nextInt();
                u = Math.max(vPrev + Math.abs(1-bPrev), uPrev);
                v = Math.max(vPrev + Math.abs(b-bPrev), uPrev + b-1);

                bPrev = b;
                uPrev = u;
                vPrev = v;
            }
            System.out.println(Math.max(uPrev, vPrev));
        }
    }
}
