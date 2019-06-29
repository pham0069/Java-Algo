package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/greedy-florist/problem
 *
 * A group of friends want to buy a bouquet of flowers. The florist wants to maximize his number of new customers and
 * the money he makes. To do this, he decides he'll multiply the price of each flower by the number of that customer's
 * previously purchased flowers plus 1. The first flower will be original price, (0+1)x original price, the next will be
 * (1+1)x original price and so on.
 *
 * Given the size of the group of friends, the number of flowers they want to purchase and the original prices of the
 * flowers, determine the minimum cost to purchase all of the flowers.
 *
 * For example, if there are k=3 friends that want to buy n=4 flowers that cost c={1, 2, 3, 4}, each will buy one of the f
 * lowers priced {2, 3, 4} at the original price. Having each purchased flower, the first flower in the list, x = 1, will
 * now cost (current purchase + prev purchase)x1 = (1+1)x1 = 2. The total cost will be 2+3+4+2=11.
 *
 * Input Format
 *
 * The first line contains two space-separated integers n andk , the number of flowers and the number of friends.
 * The second line contains n space-separated positive integers c[i], the original price of each flower.
 *
 * Constraints
 * 1 <= n, k <= 100
 * 1 <= c[i] <= 10^6
 * answer < 2^31
 * 0 <= i < n
 *
 * Output Format
 *
 * Print the minimum cost to buy all n flowers.
 *
 * Sample Input 0
 *
 * 3 3
 * 2 5 6
 * Sample Output 0
 *
 * 13
 * Explanation 0
 *
 * There are n=3 flowers with costs c = {2, 5, 6} and k=3 people in the group. If each person buys one flower, the total
 * cost of prices paid is 2+5+13 dollars. Thus, we print 13 as our answer.
 *
 * Sample Input 1
 *
 * 3 2
 * 2 5 6
 * Sample Output 1
 *
 * 15
 * Explanation 1
 *
 * There are n=3 flowers with costs c={2, 5, 6} and k=2 people in the group. We can minimize the total purchase cost
 * like so:
 * 1. The first person purchases 2 flowers in order of decreasing price; this means they buy the more expensive flower (5)
 * first at price (0+1)x5=5 dollars and the less expensive flower (2) second at price (1+1)x4 dollars.
 * 2. The second person buys the most expensive flower at price (0+1)x6=6 dollars.
 * We then print the sum of these purchases, which is 5+4+6=15, as our answer.
 *
 * Sample Input 2
 *
 * 5 3
 * 1 3 5 7 9
 * Sample Output 2
 *
 * 29
 * Explanation 2
 *
 * The friends buy flowers for 9, (7, 3) and (5, 1) for a cost of 9 + 7 + 3*(1+1) + 5 + 1*(1+1) = 29 .
 */
public class GreedyFlorist {
    public static void main( String args[] ){
        Scanner in = new Scanner(System.in);
        int n, k;
        n = in.nextInt();
        k = in.nextInt();

        int c[] = new int[n];
        for(int i=0; i<n; i++){
            c[i] = in.nextInt();
        }

        Arrays.sort(c);
        int result = 0;
        for (int i = 0; i < n; i++){
            result += c[n-1-i]*(i/k+1);
        }
        System.out.println( result );

    }
}
