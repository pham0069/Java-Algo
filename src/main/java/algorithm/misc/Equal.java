package algorithm.misc;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/equal/problem
 *
 * Christy is interning at HackerRank. One day she has to distribute some chocolates to her colleagues.
 * She is biased towards her friends and plans to give them more than the others. One of the program
 * managers hears of this and tells her to make sure everyone gets the same number.
 *
 * To make things difficult, she must equalize the number of chocolates in a series of operations. For
 * each operation, she can give 1, 3, or 5 chocolates to all but one colleague. Everyone who gets
 * chocolate in a round receives the same number of pieces.
 *
 * For example, assume the starting distribution is [1, 1, 5]. She can give 3 bars to the first two and
 * the distribution will be [4, 4, 5]. On the next round, she gives the same two 1 bar each, and everyone
 * has the same number: [5, 5, 5].
 *
 * Given a starting distribution, calculate the minimum number of operations needed so that every colleague
 * has the same number of chocolates.
 *
 * Input Format: The first line contains an integer t, the number of testcases.
 * Each testcase has 2 lines.
 * - The first line contains an integer n, the number of colleagues.
 * - The second line contains n space-separated integers denoting the number of chocolates each colleague has.
 *
 * Constraints: 1<=t<=100, 1<=n<=10000
 * Number of initial chocolates each colleague has < 1000
 *
 * Output Format: Print the minimum number of operations needed for each test case, one to a line.

 * Sample Input:
 1
 4
 2 2 3 7
 * Sample Output: 2
 * Explanation: Start with  [2, 2, 3, 7]
 * Add 1 to all but the 3rd element  --> [3, 3, 3, 8]
 * Add 5 to all but the 4th element  --> [8, 8, 8, 8]
 * Two operations were required.
 *
 * Giving N chocolates to everyone except the chosen person is the same as taking N chocolates away from the
 * chosen person, in the sense that the difference of chocolates between any two persons are the same after
 * either operations.
 * Let say the initial number of chocolates of n persons are a1, a2... an
 * The number of chocolates we need to take from n persons such that the final number of chocolates for each
 * person are equal is b1, b2... bn, i.e. a1 - b1 = a2 - b2 = ... an - bn, and b1, b2, ... bn >= 0
 * -> bi = ai - a1 + b1 -> the smaller b1, the smaller bi
 * Let say a1 is min among a1... an, then b1 is also min among b1... bn.
 * b1 >= 0 --> bi >= ai - a1
 *
 * Let say O(b) is the minimum number of operations needed to take b from a person. This is achieved by making
 * as many operations of taking away 5 as possible, followed by making as many operations of taking away 3 as
 * possible, and the rest is taking away 1 until the sum is b.
 * -> O(b) = b/5 + b%5/3 + b%5%3
 * -> The minimum number of operations needed to take bi from person i (i = 1..n) is O(b1) + O(b2) + ... + O(bn)
 *
 * The question is that given b >= B, is min O(b) = O(B)? The answer is no.
 * We have O(b) = b/5 + b %5/3 + b %5 %3 and O(B) = B/5 + B %5/3 + B %5 %3.
 * Though b/5 >= B/5, it's uncertain that b %5/3 + b %5 %3 > B %5/3 + B %5 %3.
 * B%5                  0   1   2   3   4
 * B%5/3 + B%5 %3       0   1   2   1   2
 * If (B%5) = 2 or 4, b = B+1 would result in smaller O(b)
 *
 * Coming back to the problem, we need to find min O(b1) + O(b2) + .... + O(bn) where b1 >= 0
 * To ensure min sum, we need to check b1 = 0 or 1
 *
 * Let say the problem is modified such that for each operation, Christy can give 1, 2, or 5 chocolates to all but one
 * colleague. In this case, O(b) = b/5 + b%5/2 +b%5%2
 * B%5                  0   1   2   3   4
 * B%5/2 + B%5%2        0   1   1   2   2
 * If (B%5) = 3, b = B+2 would result in smaller O(b)
 * If (B%5) = 3, b = B+1 would result in smaller O(b)
 * Therefore we need to check b1 = 0, 1 or 2
 */
public class Equal {
    //Time complexity is O(n)
    public static int findMinOperations(int[] bars) {
        //get min number in array bars
        int minBars = Integer.MAX_VALUE;
        for (int i = 0; i < bars.length; i++){
            if (bars[i] < minBars)
                minBars = bars[i];
        }
        //check which base value minimizes total number of operations
        int minOps = Integer.MAX_VALUE;
        for(int base = 0; base < 2; base++) {
            int ops = 0;
            for(int i = 0; i < bars.length; i++) {
                int delta = bars[i] - minBars + base;
                ops += delta / 5 + delta % 5 / 3 + delta % 5 % 3 / 1;
            }
            minOps = Math.min(ops,minOps);
        }
        return minOps;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while(t-- > 0) {
            int n = scanner.nextInt();
            int bars[] = new int[n];
            for(int i = 0; i < n; i++){
                bars[i] = scanner.nextInt();
            }
            System.out.println(findMinOperations(bars));
        }
    }
}