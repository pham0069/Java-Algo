package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/board-cutting/problem
 *
 * Alice gives Bob a board composed of 1x1 wooden squares and asks him to find the minimum cost of breaking the board b
 * ack down into its individual squares. To break the board down, Bob must make cuts along its horizontal and vertical
 * lines.
 *
 * To reduce the board to squares, Bob makes horizontal and vertical cuts across the entire board. Each cut has a given
 * cost, cost_y[i] or cost_x[i] for each cut along a row or column across one board, so the cost of a cut must be
 * multiplied by the number of segments it crosses. The cost of cutting the whole board down into 1x1 squares is the sum
 * of the costs of each successive cut.
 *
 * Can you help Bob find the minimum cost? The number may be large, so print the value modulo 10^9+7.
 *
 * For example, you start with a 2x2 board. There are two cuts to be made at a cost of cost_y[i] = 3 for the horizontal
 * and cost_x[i]= 1 for the vertical. Your first cut is across 1 piece, the whole board. You choose to make the
 * horizontal cut between rows 1 and 2 for a cost of 1x3 = 3. The second cuts are vertical through the two smaller
 * boards created in step 1 between columns 1 and 2. Their cost is 2x1=2. The total cost is 3+2=5 and 5 modulo... = 5.
 *
 * Input Format
 *
 * The first line contains an integer q, the number of queries.
 *
 * The following q sets of lines are as follows:
 *
 * The first line has two positive space-separated integers m and n, the number of rows and columns in the board.
 * The second line contains m-1 space-separated integers cost_y[i], the cost of a horizontal cut between rows i and (i+1)
 * of one board.
 * The third line contains  space-separated integers cost_x[j], the cost of a vertical cut between columns i and (i+1)
 * of one board.
 *
 * Constraints
 * 1<=q <= 20
 * 2 <= m, n <= 10^6
 * 0 <= cost <= 10^9
 *
 * Output Format
 *
 * For each of the q queries, find the minimum cost minCost of cutting the board into 1x1 squares and print the value of
 * minCost % (10^9+7)
 *
 * Sample Input 0
 *
 * 1
 * 2 2
 * 2
 * 1
 * Sample Output 0
 *
 * 4
 * Explanation 0
 * We have a 2x2 board, with cut costs y=2 and x=1. Our first cut is horizontal between y[2] and y[1], because that is
 * the line with the highest cost (2). Our second cut is vertical, at x[1]. Our first cut has a cost of 2 because we
 * are making a cut with cost 2 across 1 segment, the uncut board. The second cut also has a total cost of 2 but we are
 * making a cut of cost 1 across 2 segments. Our answer is 2+2 = 4.
 *
 * Sample Input 1
 *
 * 1
 * 6 4
 * 2 1 3 1 4
 * 4 1 2
 * Sample Output 1
 *
 * 42
 * Explanation 1
 * Our sequence of cuts is: y[5], x[1], y[3], y[1], x[3], y[2], y[4] and x[2].
 * Cut 1: Horizontal with cost 4 across 1 segment -> 4
 * Cut 2: Vertical with cost 4 across 2 segments -> 8
 * Cut 3: Horizontal with cost 3 across 2 segments -> 6
 * Cut 4: Horizontal with cost 2 across 2 segments -> 4
 * Cut 5: Vertical with cost 2 across 4 segments -> 8
 * Cut 6: Horizontal with cost 1 across 3 segments -> 3
 * Cut 7: Horizontal with cost 1 across 3 segments -> 3
 * Cut 8: Vertical with cost 1 across 6 segments -> 6
 * Total cost is 42.
 */
public class CuttingBoard {
    public static final int MODULE = 1000000007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        int m, n, verticalSegment, horizontalSegment;
        long totalCost;
        while (q-- > 0){
            m = sc.nextInt();
            n = sc.nextInt();
            Cut[] cuts = new Cut[m+n-2];
            for (int i = 0; i < m-1; i++){
                cuts[i] = new Cut(sc.nextInt(), true);
            }
            for (int i = m-1; i < m+n-2; i++){
                cuts[i] = new Cut(sc.nextInt(), false);
            }
            Arrays.sort(cuts);//sort in descending cost of cuts

            verticalSegment = 1; horizontalSegment = 1; totalCost = 0;
            for (int i = 0; i < m+n-2; i++){
                if (cuts[i].isVertical){
                    totalCost += ((long)cuts[i].cost) * horizontalSegment;
                    ++ verticalSegment;
                }
                else{
                    totalCost += ((long)cuts[i].cost) * verticalSegment;
                    ++ horizontalSegment;
                }
            }
            System.out.println(totalCost%MODULE);
        }
    }
}
class Cut implements Comparable <Cut>{
    int cost;
    boolean isVertical;
    Cut(int cost, boolean isVertical){
        this.cost = cost;
        this.isVertical = isVertical;
    }
    //sort in descending cost of cuts
    @Override public int compareTo(Cut c){
        return (c.cost-this.cost);
    }
}
