package puzzle;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/count-ways-express-number-sum-consecutive-numbers/?ref=lbp
 *
 * Given a number N, find the number of ways to represent this number as a sum of 2 or more consecutive natural numbers.
 *
 * Examples:
 *
 * Input :15
 * Output :3
 * 15 can be represented as:
 * 1+2+3+4+5
 * 4+5+6
 * 7+8
 *
 * Input :10
 * Output :1
 * 10 can only be represented as:
 * 1+2+3+4
 *
 * Let say N = a + (a+1) + ... + (a+k-1) = (2a-1+k)*k/2
 * (2a-1+k)k = 2N
 * Note that k or (2a-1+k) odd
 * k >= 2, (2a-1+k) >= 3
 */

public class SumOfConsecutiveNumbers {
    public static void main(String[] args) {
        int n = 10;

        // find i odd divisible by n
        for (int i = 3; i < 2*n; i +=2) {
            if (n%i == 0) {
                int k = i;
                printPair(n, k);

                k = 2*n/i;
                printPair(n, k);
            }
        }
    }

    static void printPair(int n, int k) {
        if (k*k>2*n)
            return;
        int a =  (2*n/k-k+1)/2;
        System.out.println("a=" + a + ";k=" + k);
    }
}
