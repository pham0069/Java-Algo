package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/gold-mine-problem/
 *
 * Given a gold mine of n*m dimensions. Each field in this mine contains a positive integer which is the amount of gold
 * in tons. Initially the miner is at the first column but can be at any row. He can move only (right ->,right up /,
 * right down\), i.e. from a given cell, the miner can move to the cell diagonally up towards the right or right or
 * diagonally down towards the right. Find out maximum amount of gold he can collect.
 *
 * Sample Input 1 : mat[][] = {{1, 3, 3}, {2, 1, 4}, {0, 6, 4}};
 * Sample Output 1: 12
 * {(1,0)->(2,1)->(2,2)}
 * Sample Input 2: mat[][] = { {1, 3, 1, 5}, {2, 2, 4, 1}, {5, 0, 2, 3}, {0, 6, 1, 2}};
 * Sample Output 2: 16
 * (2,0) -> (1,1) -> (1,2) -> (0,3) OR
 * (2,0) -> (3,1) -> (2,2) -> (2,3)
 *
 * Sample Input 3: mat[][] = {{10, 33, 13, 15}, {22, 21, 04, 1}, {5, 0, 2, 3}, {0, 6, 14, 2}};
 * Sample Output 3: 83
 * ---------------------------------------------------------------------------------------------------------------------
 * Create a 2-D matrix sum[][] of the same as given matrix gold[][]
 * Amount of gold is positive, so we would like to cover maximum cells of maximum values under given constraints.
 * In every move, we move one step toward right side. So we always end up in last column. If we are at the last column,
 * then we are unable to move right.
 * If we are at the first row or last column, then we are unable to move right-up so just assign 0 otherwise assign
 * the value of sum[row-1][col+1] to right_up. If we are at the last row or last column, then we are unable to move
 * right down so just assign 0 otherwise assign the value of sum[row+1][col+1] to right up.
 * Now find the maximum of right, right_up, and right_down and then add it with that gold[row][col]. At last, find the
 * maximum of all rows and first column and return it.
 *
 * We only need to store the sum array in the previous col instead of the whole sum matrix.
 * Space complexity is O(n). Time complexity is O(m*n)
 */
public class GoldMining {
    public static void main(String[] args){
        //int[][] gold = {{1, 3, 3}, {2, 1, 4}, {0, 6, 4}};
        int[][] gold = { {1, 3, 1, 5}, {2, 2, 4, 1}, {5, 0, 2, 3}, {0, 6, 1, 2}};
        System.out.println(getMaxGold(gold));
    }
    public static long getMaxGold(int[][] gold){
        int rows = gold.length;
        long[][] sum = new long[rows][2];
        int prevIndex = 0, curIndex = 1;
        for (int c = 0; c < gold[0].length; c++){
            for (int r = 0; r < rows; r++){
                long prevGold = sum[r][prevIndex];
                if (r > 0)
                    prevGold = Math.max(prevGold, sum[r-1][prevIndex]);
                if (r < rows-1)
                    prevGold = Math.max(prevGold, sum[r+1][prevIndex]);
                sum[r][curIndex] = prevGold + gold[r][c];
            }
            prevIndex = 1 - prevIndex;
            curIndex = 1 - curIndex;
        }
        long max = 0;
        for (int r = 0; r < rows; r++) {
            max = Math.max(max, sum[r][prevIndex]);
        }
        return max;
    }
}
