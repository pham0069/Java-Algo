package algorithm.greedy;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/largest-permutation/problem
 *
 * You are given an unordered array of unique integers incrementing from 1. You can swap any two elements a limited
 * number of times. Determine the largest lexicographical value array that can be created by executing no more than the
 * limited number of swaps.
 *
 * For example, if arr = {1, 2, 3, 4,} and the maximum swaps k=1, the following arrays can be formed by swapping the
 * with the other elements:
 * [2,1,3,4]
 * [3,2,1,4]
 * [4,2,3,1]
 * The highest value of the four (including the original) is [4,2,3,1]. If k>=2, we can swap to the highest possible
 * value: [4,3,2,1].
 *
 * Input Format
 *
 * The first line contains two space-separated integers n and k, the length of arr and the maximum swaps that can be
 * performed. The second line contains n unique space-separated integers arr[i] where 1 <= arr[i] <= n.
 *
 * Constraints
 * 1 <= n <= 10^5
 * 1 <= k <= 10^9
 *
 * Output Format
 *
 * Print the lexicographically largest permutation you can make with at most k swaps.
 * Sample Input 0
 *
 * 5 1
 * 4 2 3 5 1
 * Sample Output 0
 *
 * 5 2 3 4 1
 * Explanation 0
 *
 * You can swap any two numbers in [4,2,3,5,1] and see the largest permutation is [5,2,3,4,1]
 *
 * Sample Input 1
 *
 * 3 1
 * 2 1 3
 * Sample Output 1
 *
 * 3 1 2
 * Explanation 1
 *
 * With 1 swap we can get [1,2,3], [3,1,2] and [2,3,1]. Of these, [3,1,2] is the largest permutation.
 *
 * Sample Input 2
 *
 * 2 1
 * 2 1
 * Sample Output 2
 *
 * 2 1
 * Explanation 2
 *
 * We can see that [2,1] is already the largest permutation. We don't make any swaps.
 *
 *
 */
public class LargestPermutation {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] p = new int[n];
        int i, j, swaps = 0;
        for (i = 0; i < n; i++)
            p[i] = sc.nextInt();
        for (i = 0; i < n && swaps < k; i++){
            if (p[i] != n-i){
                j = findIndex(p, n-i, i+1);
                //cannot find, equivalent to largest permutation reached
                if (j == -1)
                    break;
                swap(p, i, j);
                swaps++;
            }
        }
        for (i = 0; i < n; i++)
            System.out.print(p[i] +" ");
    }

    public static int findIndex(int[] p, int key, int start){
        for (int i = start; i < p.length; i++){
            if (p[i] == key)
                return i;
        }
        return -1;
    }
    public static void swap(int[] p, int i, int j){
        int temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }
}
