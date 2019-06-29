package algorithm.dynamic_proramming;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/fibonacci-modified/problem
 * We define a modified Fibonacci sequence using the following definition:
 * T[i+2] = T[i] + T[i+1]^2
 * Given three integers T[1], T[2], and n, compute and print T[n] of the modified Fibonacci sequence.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format
 * A single line of three space-separated integers describing the respective values of T[1], T[2], and n.
 * Constraints: 0 <= T1, T2 <= 2, 3 <= n <= 20
 * Note: T[n] may far exceed the range of a -bit integer.
 * Output Format
 * Print a single integer denoting the value of term T[n] in the modified Fibonacci sequence where the first two terms
 * are T[1] and T[2].
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample Input
 * 0 1 5
 * Sample Output
 * 5
 * Explanation
 * The first two terms of the sequence are 0 and 1, which gives us a modified Fibonacci sequence of 0, 1, 1, 2, 5, 27.
 * T[5] = 5, print 5.
 * ---------------------------------------------------------------------------------------------------------------------
 *
 */
public class ModifiedFibonacci {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        BigDecimal t1 = new BigDecimal(sc.nextInt());
        BigDecimal t2 = new BigDecimal(sc.nextInt());
        int n = sc.nextInt();
        BigDecimal t;
        for (int i = 3; i < n; i++){
            t = t1.add(t2.multiply(t2));
            t1 = t2;
            t2 = t;
        }
    }
}
