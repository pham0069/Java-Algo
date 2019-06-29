package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/
 *
 * Given a value N, if we want to make change for N cents, and we have infinite supply of each of S = { S1, S2, .. , Sm}
 * valued coins, how many ways can we make the change? The order of coins doesn’t matter.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample input 1: N = 4 and S = {1,2,3}
 * Sample output 1: 4
 * Since there are four solutions: {1,1,1,1},{1,1,2},{2,2},{1,3}
 * Sample input 2: N = 10 and S = {2, 5, 3, 6}
 * Sample output 2: 5
 * Since there are five solutions: {2,2,2,2,2}, {2,2,3,3}, {2,2,6}, {2,3,5} and {5,5}
 * -------------------------------------------------------------------------------------------------------------------------
 * Denote the number of ways to make the change N cents using the coins {S1, S2... Si} as C(N, i)
 * C(N, m) = C(N, m-1) + C(N-m, m)
 * Use DP to calculate C(x, y) for x = 1..N, y = 1..m
 * Time complexity is O(mN). Space complexity is O(N)
 */
public class CoinChange {
    public static void main(String[] args){
        int n = 10;
        int[] s = {2, 5, 3, 6};
        System.out.println(getNumberOfCoinChange(n, s));
    }
    public static int getNumberOfCoinChange(int total, int[] coin){
        int[] table = new int[total+1];
        table[0] = 1;
        for(int i = 0; i < coin.length; i++)
            for(int j = coin[i]; j <= total; j++)
                table[j] += table[j-coin[i]];

        return table[total];
    }
}
