package maths.gcd;

import java.util.Scanner;

import static maths.gcd.GcdAndLcm.getGreatestCommonDivisor;
import static maths.gcd.GcdAndLcm.getLeastCommonMultiple;

/**
 * https://www.hackerrank.com/challenges/between-two-sets/problem
 *
 * Given two arrays of integers A and B, determine all integers X that satisfy two conditions:
 * 1. All the elements of the first array A are factors of X
 * 2. X is a factor of all elements of the second array B
 * Such numbers like X are referred to as being between the two arrays.
 * You must determine how many such numbers exist.
 *
 * Input Format
 * The first line contains two space-separated integers describing the respective values of N,
 * the number of elements in array A, and M, the number of elements in array B.
 * The second line contains n distinct space-separated integers describing A.
 * The third line contains M distinct space-separated integers describing B.
 *
 * Output Format
 * Print the number of integers that are considered to be between A and B.
 *
 * Sample Input
 2 3
 2 4
 16 32 96
 * Sample Output: 3 since there are 3 numbers (4, 8, 16) between {2, 4} and {16, 32, 96}
 */
public class BetweenTwoSets {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            b[i] = sc.nextInt();
        }
        System.out.println(getNumberOfIntBetween(a, b));
    }
    private static int getNumberOfIntBetween(int[] a, int[] b) {
        long lcma = GcdAndLcm.getLeastCommonMultiple(a);
        long gcdb = GcdAndLcm.getGreatestCommonDivisor(b);
        if (gcdb%lcma != 0)
            return 0;
        return getNumberOfPairsWithProduct(gcdb/lcma);
    }

    private static int getNumberOfPairsWithProduct(long n){
        long sqrt = (long) Math.sqrt(n);
        int numDivisors = 0;
        for (int i = 1; i <= sqrt;i++){
            if (n%i==0)
                numDivisors++;
        }
        if (sqrt*sqrt == n)
            return numDivisors*2-1;
        return numDivisors*2;
    }
}
