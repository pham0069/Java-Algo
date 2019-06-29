package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-minimax/problem
 *
 * Watson gives Sherlock an array of integers. Given the endpoints of an integer range, for all M in that inclusive
 * range, determine the minimum( abs(arr[i]-M) for all 1<=i<=array length)). Once that has been determined for all
 * integers in the range, return the M which generated the maximum of those values. If there are multiple M's that
 * result in that value, return the lowest one.
 *
 * For example, your array arr = [3, 5, 7, 0] and your range is from p=6 to q=8 inclusive.
 *
 * M	|arr[1]-M|	|arr[2]-M|	|arr[3]-M|	|arr[4]-M|	Min
 * 6	   3		   1		   1		   3		 1
 * 7	   4		   2		   0		   2		 0
 * 8	   5		   3		   1		   1		 1
 *
 * We look at the Min column and see the maximum of those three values is 1. Two M's result in that answer so we choose
 * the lower value, 6.
 *
 * Input Format
 *
 * The first line contains an integer n, the number of elements in arr.
 * The next line contains n space-separated integers arr[i].
 * The third line contains two space-separated integers p and q, the inclusive endpoints for the range of M.
 *
 * Constraints
 * 1 <= n <= 100
 * 1 <= arr[i] <= 10^9
 * 1 <= p <= q <= 10^9
 *
 *
 * Output Format
 *
 * Print the value of M on a line.
 *
 * Sample Input
 *
 * 3
 * 5 8 14
 * 4 9
 * Sample Output
 *
 * 4
 * Explanation
 *
 *
 * M	|arr[1]-M|	|arr[2]-M|	|arr[3]-M|	Min
 * 4	   1		   4		   10		 1
 * 5	   0		   3		    9		 0
 * 6	   1		   2		    8		 1
 * 7	   2		   1		    7		 1
 * 8	   3		   0		    6		 0
 * 9	   4		   1		    5		 1
 * For M = 4, 6, 7, or 9, the result is 1. Since we have to output the smallest of the multiple solutions, we print 4.
 *
 */
public class SherlockAndMiniMax {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++){
            a[i] = sc.nextInt();
        }
        int p = sc.nextInt(), q = sc.nextInt();
        Arrays.sort(a);

        int m = 0;

        int l = maxLowerIndex(a, p);
        int h = minHigherIndex(a, q);
        int max = 0, temp;
        if (l == -1){
            m = p;
            max = a[0] - p;
        }
        else if (l!=-1 && l+1< n){
            m = (p >= (a[l+1]+a[l])/2)?p:((a[l+1]+a[l])/2);
            max = a[l+1]-m;
        }
        else{
            m = p;
            max = p - a[l];
        }
        int start = (l==-1?0:l+1);
        int end = (h==-1?n-1:h-1);

        if (start < n){
            for (int i = start; i < end; i++){
                temp = (a[i+1]-a[i])/2;
                if (temp > max){
                    max = temp;
                    m = (a[i+1]+a[i])/2;
                }
            }
        }
        if (h == -1){
            if (q-a[n-1] > max)
                m = q;
        }
        else if (h!=-1 && h>= 1){
            int m1 = (q <= (a[h]+a[h-1])/2)?q:((a[h]+a[h-1])/2);
            if (m1-a[h-1] > max)
                m = m1;
        }
        else{
            if (a[0]-q > max)
                m = q;
        }

        System.out.println(m);
    }
    public static int maxLowerIndex(int[] a, int key){
        if (a[0] >= key)
            return -1;
        int start = 0, end = a.length-1, mid;
        while (start+1 < end){
            mid = (start + end)/2;
            if (a[mid] < key)
                start = mid;
            else
                end = mid;
        }
        return start;
    }
    public static int minHigherIndex(int[] a, int key){
        if (a[a.length-1] <= key)
            return -1;
        int start = 0, end = a.length-1, mid;
        while (start+1 < end){
            mid = (start + end)/2;
            if (a[mid] > key)
                end = mid;
            else
                start = mid;
        }
        return end;
    }
}
