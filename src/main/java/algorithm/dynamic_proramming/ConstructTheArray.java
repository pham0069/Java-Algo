package algorithm.dynamic_proramming;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/construct-the-array/problem
 * Your goal is to find the number of ways to construct an array such that consecutive positions contain different values.
 * Specifically, we want to construct an array with n elements such that each element is between 1 and k, inclusive.
 * We also want the first and last elements of the array to be 1 and x.
 * Given n, k and x, find the number of ways to construct such an array. Since the answer may be large, only find it modulo
 * 10^9+7.
 * For example, for n = 4, k = 3, x = 2, there are 3 such arrays:
 * {1, 2, 1, 2}     {1, 2, 3, 2}        {1, 3, 1, 2}
 *
 * Input Format: One line with  integers value for n, k, x respectively
 * Output Format: Print out the number of arrays mod (10^9+7)
 * Constraints: 3 <= n <= 10^5, 2<= k <= 10^5, 1 <= x <=k
 * For 20% test cases, n <= 1000, k <= 100
 *
 * Sample Input:
 761 99 1
 * Sample Output:
 236568308
 *
 * Denote S[i-1] = number of arrays of length (i-1) that starts with 1, ends with x and having different consecutive elements
 * Number of arrays of length (i-1) starting with 1 and having different consecutive elements is: (k-1)^(i-2)
 * This is because when a[0] is fixed to 1, there are (k-1) possible choices for a[1], (k-1) possible choices for a[2]
 * ... and (k-1) possible choices for a[i-2]
 * S[i] = (k-1)^(i-2) - S[i-1]
 * Base case is S[2] = 1, if x != 1 and S[2] = 0 if x = 1. We should start the problem from i = 3.
 * (k-1) ^(i-1) can be calculated along when we traverse the length from 3 to n
 * Time complexity is O(n). Space complexity is O(1).
 *
 * It can be deduced that S[n] = (k-1)^(n-2) - (k-1)^(n-3) + (k-1)^(n-4) - ...
 * = Sigma (-1)^(n-i)*(k-1)^(i) (for i=1...n-2) + (-1)^n*S[2]
 * = (-1)^n * {Sigma (1-k)^i (for i = 1..n-2) + S[2]}
 * = (-1)^n * [(1-k)^(n-1) - (1-k)]/(-k) + (-1)^n*S[2]
 * Though (1-k))^(n-1) can be calculated in O(log n), the division by (-k) is not straightforward when working with MOD.
 */
public class ConstructTheArray {
    private static final int MOD = 1000000007;
    public static void main(String[] args) {
        getNumberOfArrays();
    }

    private static void getNumberOfArrays() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int x = sc.nextInt();
        long s = x==1 ? 0 : 1;
        long exponent = 1;
        for (int i = 3; i <= n; i++){
            exponent = (exponent * (k-1)) % MOD;
            s = (exponent - s + MOD) % MOD;
        }
        System.out.println(s);
    }
    private static long getExponent(int base, int power){
        long result = 1;
        long longBase = base;
        while (power > 0) {
            if ((power & 1) == 1)//odd
                result = (result * longBase + MOD) % MOD;
            power = power >> 1;//divide by 2
            longBase = (longBase * longBase) % MOD;//square
        }
        return result;
    }
}
