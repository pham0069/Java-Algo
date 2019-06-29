package algorithm.greedy;

/**
 * Alex is attending a barter market with N comic books and M coins. Alex wants to collect as many fiction books as
 * possible. He can take 2 following actions:
 * 1. Trade 1 comic book + X coins to get 1 fiction book
 * 2. Trade 1 comic book for Y coins
 * Given M, N, X, Y. Return the maximum fiction books that Alex can get.
 *
 * Let A be the number of comic books that Alex trades for fiction book, and B be the number of comic books that Alex
 * trades for coins. Our goal is to find max A. We have the following conditions:
 * 1. A + B <= N
 * 2. BY + M >= AX
 * This is equivalent to (N-A)Y + M >= AX, i.e. A <= (N*Y+M)/(X+Y)
 *
 * Beware of overflow
 */
public class BarterMarket {
    public static void main(String[] args) {

    }
}
