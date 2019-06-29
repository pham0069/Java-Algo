package algorithm.recursive;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://practice.geeksforgeeks.org/problems/replace-os-with-xs/0
 * https://www.geeksforgeeks.org/given-matrix-o-x-replace-o-x-surrounded-x/
 * https://www.geeksforgeeks.org/flood-fill-algorithm-implement-fill-paint/
 *
 * Given a matrix of size NxM where every element is either ‘O’ or ‘X’, replace ‘O’ with ‘X’ if surrounded by ‘X’.
 * A ‘O’ (or a set of ‘O’) is considered to be by surrounded by ‘X’ if there are ‘X’ at locations just below, just
 * above, just left and just right of it.
 *
 * Example 1:
 * Input: mat[N][M] =   {{'X', 'O', 'X', 'X', 'X', 'X'},
                         {'X', 'O', 'X', 'X', 'O', 'X'},
                         {'X', 'X', 'X', 'O', 'O', 'X'},
                         {'O', 'X', 'X', 'X', 'X', 'X'},
                         {'X', 'X', 'X', 'O', 'X', 'O'},
                         {'O', 'O', 'X', 'O', 'O', 'O'},
                         };
 * Output: mat[N][M] =  {{'X', 'O', 'X', 'X', 'X', 'X'},
                         {'X', 'O', 'X', 'X', 'X', 'X'},
                         {'X', 'X', 'X', 'X', 'X', 'X'},
                         {'O', 'X', 'X', 'X', 'X', 'X'},
                         {'X', 'X', 'X', 'O', 'X', 'O'},
                         {'O', 'O', 'X', 'O', 'O', 'O'},
                         };
 * Example 2:
 * Input: mat[N][M] =   {{'X', 'X', 'X', 'X'}
                         {'X', 'O', 'X', 'X'}
                         {'X', 'O', 'O', 'X'}
                         {'X', 'O', 'X', 'X'}
                         {'X', 'X', 'O', 'O'}
                         };
 * Output: mat[N][M] =  {{'X', 'X', 'X', 'X'}
                         {'X', 'X', 'X', 'X'}
                         {'X', 'X', 'X', 'X'}
                         {'X', 'X', 'X', 'X'}
                         {'X', 'X', 'O', 'O'}
                         };
 * ---------------------------------------------------------------------------------------------------------------------
 * Input:
 * The first line of input contains an integer T denoting the no of test cases.
 * Then T test cases follow.
 * The first line of each test case contains two integers N and M denoting the size of the matrix.
 * Then in the next line are N*M space separated values of the matrix.
 *
 * Output:
 * For each test case print the space separated values of the new matrix.
 *
 * Constraints:
 * 1<=T<=10
 * 1<=mat[][]<=100
 * 1<=n,m<=20
 *
 * Example:
 * Input:
 3
 1 5
 X O X O X
 3 3
 X X X X O X X X X
 6 6
 X O X X X X X O X X O X X X X O O X O X X X X X X X X O X O O O X O O O
 * Output:
 X O X O X
 X X X X X X X X X
 X O X X X X X O X X X X X X X X X X O X X X X X X X X O X O O O X O O O
 * ---------------------------------------------------------------------------------------------------------------------
 * All the Os connected to border Os cannot be replaced into X
 * By traversing all elements in matrix, we can find out these connected Os and exclude them from the replacement
 *
 *
 */
public class ReplaceOWithX {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t > 0) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            char[][] matrix = new char[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    matrix[i][j] = sc.next().charAt(0);
            replaceSurrounded(matrix, n, m);
            t --;
        }
    }

    /**
     * Both time and space complexity is O(n*m).
     */
    static void replaceSurrounded(char[][] matrix, int n, int m) {
        boolean[][] connectedToBorderOs = new boolean[n][m];
        IntStream.range(0, m).forEach(i -> {
            if(matrix[0][i]=='O')
                connectedToBorderOs[0][i] = true;
            if(matrix[n-1][i]=='O')
                connectedToBorderOs[n-1][i] = true;
        });
        IntStream.range(0, n).forEach(i -> {
            if(matrix[i][0]=='O')
                connectedToBorderOs[i][0] = true;
            if(matrix[i][n-1]=='O')
                connectedToBorderOs[i][n-1] = true;
        });
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (matrix[i][j] == 'O'){
                    if ((i > 0 && connectedToBorderOs[i-1][j])
                            || (i < n-1 && connectedToBorderOs[i+1][j])
                            || (j > 0 && connectedToBorderOs[i][j-1])
                            || (j < m-1 && connectedToBorderOs[i][j+1]))
                        connectedToBorderOs[i][j] = true;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 'O' && !connectedToBorderOs[i][j])
                    System.out.print('X' + " ");
                else
                    System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Recursively replace prevVal to newVal of this cell and connected cell
     */
    static void floodFillUtil(char matrix[][], int i, int j, char prevVal, char newVal) {
        int n = matrix.length;
        int m = matrix[0].length;
        // Base cases
        if (i < 0 || i >= n || j < 0 || j >= m)
            return;

        if (matrix[i][j] != prevVal)
            return;

        // Change color
        matrix[i][j] = newVal;

        // Recur for north, east, south and west
        floodFillUtil(matrix, i-1, j, prevVal, newVal);
        floodFillUtil(matrix, i+1, j, prevVal, newVal);
        floodFillUtil(matrix, i, j-1, prevVal, newVal);
        floodFillUtil(matrix, i, j+1, prevVal, newVal);
    }

    /**
     * Time complexity is O(n*m). Space complexity is O(1)
     */
    static void replaceSurrounded2(char matrix[][], int n, int m) {
        // Step 1: Replace all 'O' with '-'
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (matrix[i][j] == 'O')
                    matrix[i][j] = '-';

        // Step 2: FloodFill for all '-' lying on edges
        for (int i = 0; i < n; i++) // Left side
            if (matrix[i][0] == '-')
                floodFillUtil(matrix, i, 0, '-', 'O');
        for (int i = 0; i < n; i++) // Right side
            if (matrix[i][m - 1] == '-')
                floodFillUtil(matrix, i, m - 1, '-', 'O');
        for (int j = 0; j < m; j++) // Top side
            if (matrix[0][j] == '-')
                floodFillUtil(matrix, 0, j, '-', 'O');
        for (int j = 0; j < m; j++) // Bottom side
            if (matrix[n - 1][j] == '-')
                floodFillUtil(matrix, n - 1, j, '-', 'O');

        // Step 3: Replace all '-' with 'X'
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (matrix[i][j] == '-')
                    System.out.print('X' + " ");
                else
                    System.out.print(matrix[i][j] + " ");
    }
}
