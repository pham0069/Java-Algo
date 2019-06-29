package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/grid-challenge/problem
 *
 * Given a square grid of characters in the range ascii[a-z], rearrange elements of each row alphabetically, ascending.
 * Determine if the columns are also in ascending alphabetical order, top to bottom. Return YES if they are or NO if
 * they are not.
 *
 * For example, given:
 *
 * a b c
 * a d e
 * e f g
 * The rows are already in alphabetical order. The columns a a e, b d f and c e g are also in alphabetical order, so the
 * answer would be YES. Only elements within the same row can be rearranged. They cannot be moved to a different row.
 *
 * Input Format:
 *
 * The first line contains t, the number of testcases.
 *
 * Each of the next t sets of lines are described as follows:
 * - The first line contains n_i, the number of rows and columns in the grid.
 * - The next n lines contains a string of length n
 *
 * Constraints:
 * 1 <= t <= 100
 * 1 <= n <= 100
 *
 * Each string consists of lowercase letters in the range ascii[a-z]
 *
 * Output Format
 *
 * For each test case, on a separate line print YES if it is possible to rearrange the grid alphabetically ascending in
 * both its rows and columns, or NO otherwise.
 *
 * Sample Input
 *
 * 1
 * 5
 * ebacd
 * fghij
 * olmkn
 * trpqs
 * xywuv
 * Sample Output
 *
 * YES
 * Explanation
 *
 * The 5x5 grid in the 1 test case can be reordered to
 *
 * abcde
 * fghij
 * klmno
 * pqrst
 * uvwxy
 * This fulfills the condition since the rows 1, 2, ..., 5 and the columns 1, 2, ..., 5 are all lexicographically sorted.
 */
public class GridChallenge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++){
            int n = sc.nextInt();
            char[][] grid = new char[n][n];
            for (int j = 0; j < n; j++){
                grid[j] = sc.next().toCharArray();
                Arrays.sort(grid[j]);
            }

            String result = areAllColumnsInDescendingOrder(grid, n) ? "YES":"NO";
            System.out.println(result);
        }

    }

    private static boolean areAllColumnsInDescendingOrder(char[][] grid, int n) {
        for (int col = 0; col < n; col++){
            for (int i = 0; i < n-1; i++){
                if (grid[i][col] > grid[i+1][col]){
                    return false;
                }
            }
        }
        return true;
    }
}
