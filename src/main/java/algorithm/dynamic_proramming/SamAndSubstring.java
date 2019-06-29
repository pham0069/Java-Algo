package algorithm.dynamic_proramming;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/sam-and-substrings/problem
 * Samantha and Sam are playing a numbers game. Given a number as a string, no leading zeros, determine the sum of all
 * integer values of substrings of the string. For example, if the string is 42, the substrings are 4, 2 and 42. Their
 * sum is 48.
 * Given an integer as a string, sum all of its substrings cast as integers. As the number may become large, return the
 * value modulo 10^9+7.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format
 * A single line containing an integer as a string without leading zeros.
 * Constraints
 * 1 <= n <= 2*10^5
 * Output Format
 * A single line which is sum of the substrings,
 *---------------------------------------------------------------------------------------------------------------------
 * Sample Input 0
 * 16
 * Sample Output 0
 * 23
 * Explanation 0
 * The substring of number 16 are 16, 1 and 6 which sums to 23.
 * Sample Input 1
 * 123
 * Sample Output 1
 * 164
 * Explanation 1
 * The sub-strings of 123 are 1, 2, 3, 12, 23, 123 which sums to 164.
 * ---------------------------------------------------------------------------------------------------------------------
 * Present the integer as an array of digits that compose the integer.
 * DP subproblem is sum of all substring integer ending at the ith digit.
 */
public class SamAndSubstring {
    private static final int MOD = 1000000007;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String n = sc.next();
        long sum = 0;
        long subSum = 0;
        int digit;
        for (int i = 0; i <  n.length(); i++) {
            digit = n.charAt(i) - 48;
            subSum = (subSum*10 + (i+1)*digit) % MOD;
            sum = (sum + subSum) % MOD;
        }
        System.out.println(sum);
    }
}
