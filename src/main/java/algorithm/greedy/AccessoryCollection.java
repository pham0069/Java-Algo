package algorithm.greedy;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/accessory-collection/problem
 *
 * Victoria is splurging on expensive accessories at her favorite stores. Each store stocks A types of accessories,
 * where the ith accessory costs i dollars (1<=i<=A). Assume that an item's type identifier is the same as its cost,
 * and the store has an unlimited supply of each accessory.
 *
 * Victoria wants to purchase a total of : accessories according to the following rule:
 *
 * Any N-element subset of the purchased items must contain at least D different types of accessories.
 *
 * For example, if L=6, N=3, and D=2, then she must choose 6 accessories such that any subset of 3 of the 6 accessories
 * will contain at least 2 distinct types of items.
 *
 * Given L, A, N, and D values for T shopping trips, find and print the maximum amount of money that Victoria can spend
 * during each trip; if it's not possible for Victoria to make a purchase during a certain trip, print SAD instead.
 * You must print your answer for each trip on a new line.
 *
 * Input Format
 *
 * The first line contains an integer, T, denoting the number of shopping trips.
 * Each of the T subsequent lines describes a single shopping trip as four space-separated integers corresponding to L,
 * A, N, and D, respectively.
 *
 * Constraints
 * 1 <= T <= 10^6
 * 1 <= D <= N <= L <= 10^5
 * 1 <= A <= 10^9
 *
 * The sum of the L's for all T shopping trips < 8*10^6.
 *
 * Output Format
 *
 * For each shopping trip, print a single line containing either the maximum amount of money Victoria can spend; if
 * there is no collection of items satisfying her shopping rule for the trip's L, A, N, and D values, print SAD instead.
 *
 * Sample Input
 *
 * 2
 * 6 5 3 2
 * 2 1 2 2
 * Sample Output
 *
 * 24
 * SAD
 * Explanation
 *
 * Shopping Trip 1:
 * We know that:
 *
 * Victoria wants to buy L=6 accessories.
 * The store stocks the following A=5 types of accessories: {1, 2, 3, 4, 5}.
 * For any grouping of N=3 of her L accessories, there must be at least D=2 distinct types of accessories.
 * Victoria can satisfy her shopping rule and spend the maximum amount of money by purchasing the following set of
 * accessories: {3, 4,5,5,4, 3}. The total cost is 24, so we print 24 on a new line.
 *
 * Shopping Trip 2:
 * We know that:
 *
 * Victoria wants to buy L=2 accessories.
 * The store stocks  type of accessory: .
 * For any grouping of N=2 of her L accessories, there must be at least D=2 distinct types of accessories.
 * Because the store only carries 1 type of accessory, Victoria cannot make a purchase satisfying the constraint that
 * there be at least 2 distinct types of accessories. Because Victoria will not purchase anything, we print that she is
 * SAD on a new line.
 *
 */
public class AccessoryCollection {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int i = 0; i < T; i++){
            int L = in.nextInt();
            int A = in.nextInt();
            int N = in.nextInt();
            int D = in.nextInt();

            if (D == 1)   {
                System.out.println(((long)L)*A);
                continue;
            }

            int upper_limit = (N-1)/(D-1);
            int a1, m, k, ak;
            long max = -1, sum;
            for (int x = 1; x <= upper_limit; x++){
                a1 = N-1-(D-2)*x;
                m = (L-a1)/x;//k = m+2
                ak = L - a1 - m*x;
                if ((ak == 0 && m> A-1) || (ak!=0 && m >A-2) )
                    continue;
                //sum = ((long)a1)*A + ((long)x)*(m*A - m*(m+1)/2) + ((long)ak)*(A-m-1);
                sum = ((long)L)*A - ((long)x)*m*(m+1)/2 - ((long)ak)*(m+1);
                max = Math.max(sum, max);
            }
            System.out.println(max==-1?"SAD":max);
        }
    }

}
