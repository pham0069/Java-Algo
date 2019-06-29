package algorithm.misc;

import java.util.Scanner;

/**
 * https://www.jessicayung.com/how-to-tackle-programming-problems-google-code-jam-2017-qualification-round-problem-a/
 *
 * We have a row of pancakes, some ‘happy side’ up and some blank side up.
 * We can flip precisely k consecutive pancakes at a time.
 * Print the minimum number of flips needed to make all the pancakes ‘happy side’ up.
 * If it is not possible, print ‘IMPOSSIBLE’.
 *
 *
 * Denote 'happy side' as 0, 'unhappy' as 1.
 * Given a row of 0s and 1s, flip so that all become 0s
 *
 * Strategy: going from left to right, find the left most 1 and flip K bits starting from it
 * do this until the end. If last K bits are not all 0s or 1s, then it's IMPOSSIBLE to flip all to 0s
 *
 * Proof:
 * 1. Order of flip does not matter
 *
 * 2. Denote original sequence as X, 0...0x..x (K last bits) sequence,
 * which we transform through a number of flips (z)
 * using our strategy above, as Y
 * Solvability of X and Y are the same
 * Note that X --z--> Y and Y --z--> Y
 * If Y is solvable then X is solvable
 * If Y is not solvable, but X is solvable, this conflicts
 * because Y can be transformed to X. If X is solvable, Y should be solvable
 *
 * 3. Need to prove that Y is not solvable if K last bits are not all 0s or 1s
 * Suppose last bit is 0 (if it's 1, can flip last K bits to make it 0)
 * Since not all bits are 0s or 1s, there must be a rightmost bit 1
 * To flip this bit, we need to involve
 * either the last bit(0)
 * or the bit before the last K bits (also 0)
 * but not both of them at the same time
 * (since the flip can handle K bits only)
 * ...0...1...0
 * If Y is solvable, we need to make N flips including this 1-bit where N is odd
 * Let say among these N flips, there are N1 flips involving the first 0-bit
 * and N2 flips involving the second 0-bit -> N = N1+N2
 * To keep them as 0s, N1 and N2 must be even -> N is even -> contradictory
 *
 */
public class OversizedPancake {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] c = sc.next().toCharArray();
        int k = sc.nextInt();

        int numberOfFlips = 0;
        int i = 0;
        for (i = 0; i < c.length-k; i++) {
            if (c[i]=='1'){
                flip(c, i, k);
                numberOfFlips++;
            }
        }
        if (isAll(c, i, k, '0')) {
            System.out.println(numberOfFlips);
        } else if (isAll(c, i, k, '1')) {
            numberOfFlips ++;
            System.out.println(numberOfFlips);
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }

    static void flip(char[] c, int start, int k) {
        for (int i = start; i < start+k; i++) {
            if (c[i] == '0') {
                c[i] = '1';
            } else {
                c[i] = '0';
            }
        }
    }

    static boolean isAll(char[]c, int start, int k, char r) {
        for (int i = start; i < start+k; i++) {
            if (c[i] != r) {
                return false;
            }
        }
        return true;
    }
}
