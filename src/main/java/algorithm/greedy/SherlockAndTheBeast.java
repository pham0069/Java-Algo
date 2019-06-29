package algorithm.greedy;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-the-beast/problem
 *
 * Sherlock Holmes suspects his archenemy Professor Moriarty is once again plotting something diabolical. Sherlock's
 * companion, Dr. Watson, suggests Moriarty may be responsible for MI6's recent issues with their supercomputer, The
 * Beast.
 *
 * Shortly after resolving to investigate, Sherlock receives a note from Moriarty boasting about infecting The Beast
 * with a virus. He also gives him a clue: an integer. Sherlock determines the key to removing the virus is to find the
 * largest Decent Number having that number of digits.
 *
 * A Decent Number has the following properties:
 *
 * Its digits can only be 3's and/or 5's.
 * The number of 3's it contains is divisible by 5.
 * The number of 5's it contains is divisible by 3.
 * It is the largest such number for its length.
 * Moriarty's virus shows a clock counting down to The Beast's destruction, and time is running out fast. Your task is
 * to help Sherlock find the key before The Beast is destroyed!
 *
 * For example, the numbers 55533333 and 555555 are both decent numbers because there are 3 5's and 5 3's in the first,
 * and 6 5's in the second. They are the largest values for those length numbers that have proper divisibility of digit
 * occurrences.
 *
 * Input Format
 *
 * The first line is an integer, t, denoting the number of test cases.
 *
 * The next t lines each contain an integer n, the number of digits in the number.
 *
 * Constraints
 * 1 <= t <= 20
 * 1 <= n <=10^5
 *
 *
 * Output Format
 *
 * Print the Decent Number having n digits; if no such number exists, tell Sherlock by printing -1.
 *
 * Sample Input
 *
 * 4
 * 1
 * 3
 * 5
 * 11
 * Sample Output
 *
 * -1
 * 555
 * 33333
 * 55555533333
 * Explanation
 *
 * For n=1, there is no Decent Number having  digit (so we print -1).
 * For n=3, 555 is the only possible number. (Decent Number Property 3).
 * For n=5, 33333  is the only possible number. (Decent Number Property 2).
 * For n=11, 55555533333 is the Decent Number. All other permutations of these digits are not decent (Decent Number
 * Property 4).
 *
 * ==============================================================================================
 * This is equivalent to find x, y >= 0 such that 5x + 3y = n and y is max
 *    x = 2n - 3m
 *    y = 5m - 3n
 *    ==> 2n/3 >= m >= 3n/5, m max, m integer
 */
public class SherlockAndTheBeast {
    /* Find x, y >= 0, y max: 5x + 3y = n
   x = 2n - 3m
   y = 5m - 3n
   ==> 2n/3 >= m >= 3n/5, m max, m integer
*/
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0){
            int n = sc.nextInt();
            int upper = (int) Math.floor(2.0*n/3);
            int lower = (int) Math.ceil(3.0*n/5);
            if (upper >= lower){
                int x = 2*n - 3*upper;
                int y = 5*upper - 3*n;
                for (int i = 0; i < 3*y; i++)
                    System.out.print("5");
                for (int i = 0; i < 5*x; i++)
                    System.out.print("3");
            }
            else{
                System.out.print("-1");
            }
            System.out.println();
        }
    }
}
