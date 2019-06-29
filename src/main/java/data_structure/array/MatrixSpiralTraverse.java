package data_structure.array;

/**
 * https://www.geeksforgeeks.org/print-a-given-matrix-in-spiral-form/
 *
 * Given a matrix mat[][] of size M*N. Traverse and print the matrix in spiral form, starting from element at index [0, 0]
 * and going in clockwise direction
 *
 * Example 0:
 * Input:
 1    2   3   4
 5    6   7   8
 9   10  11  12
 13  14  15  16
 * Output:
 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
 * Example 1:
 * Input:
 1   2   3   4  5   6
 7   8   9  10  11  12
 13  14  15 16  17  18
 * Output:
 1 2 3 4 5 6 12 18 17 16 15 14 13 7 8 9 10 11
 * Example 2:
 * Input:
 1   2   3
 4   5   6
 7   8   9
 10  11  12
 13  14  15
 16  17  18
 * Output:
 1 2 3 6 9 12 15 18 17 16 13 10 7 4 5 8 11 14
 *
 *
 */
public class MatrixSpiralTraverse {
    public static void main(String[] args) {

        int[][] matrix1 = {  {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        int[][] matrix2 = {
                {1, 2, 3, 4, 5, 6},
                {7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18}};
        int[][] matrix3 = {
                {1, 2, 3}, {4, 5, 6},
                {7, 8, 9}, {10, 11, 12},
                {13, 14, 15}, {16, 17, 18}};

        //printSpiralMatrix(matrix1);
        //printSpiralMatrix(matrix2);
        printSpiralMatrix2(matrix3);
    }
    static void printSpiralMatrix(int[][] matrix) {
        goSpiral(matrix, 0, 0, matrix.length-1, matrix[0].length-1);
        System.out.println();
    }
    static void goSpiral(int[][] matrix, int startRow, int startCol, int endRow, int endCol) {
        if (startRow > endRow || startCol > endCol)
            return;
        int i;
        //go right
        for (i = startCol; i <= endCol; i++)
            System.out.print(matrix[startRow][i] + " ");
        //go down
        for (i = startRow+1; i <= endRow; i++)
            System.out.print(matrix[i][endCol] + " ");
        //go left
        if (endRow != startRow) {
            for (i = endCol - 1; i >= startCol; i--)
                System.out.print(matrix[endRow][i] + " ");
        }
        //go up
        if (endCol != startCol) {
            for (i = endRow - 1; i > startRow; i--)
                System.out.print(matrix[i][startCol] + " ");
        }
        goSpiral(matrix, startRow+1, startCol+1, endRow-1, endCol-1);
    }

    /**
     * Iterative approach to avoid stack overflow if matrix size is big
     * @param matrix
     */
    static void printSpiralMatrix2(int[][] matrix) {
        int startRow = 0, startCol = 0, endRow = matrix.length-1, endCol = matrix[0].length-1;
        while(startRow <= endRow && startCol <= endCol) {
            int i;
            //go right
            for (i = startCol; i <= endCol; i++)
                System.out.print(matrix[startRow][i] + " ");
            //go down
            for (i = startRow+1; i <= endRow; i++)
                System.out.print(matrix[i][endCol] + " ");
            //go left
            if (endRow != startRow) {
                for (i = endCol - 1; i >= startCol; i--)
                    System.out.print(matrix[endRow][i] + " ");
            }
            //go up
            if (endCol != startCol) {
                for (i = endRow - 1; i > startRow; i--)
                    System.out.print(matrix[i][startCol] + " ");
            }
            ++startRow;
            ++startCol;
            --endRow;
            --endCol;
        }
        System.out.println();
    }

    static void spiralPrint(int m, int n, int a[][]) {
        int i, k = 0, l = 0;
        /*  k - starting row index
        m - ending row index
        l - starting column index
        n - ending column index
        i - iterator
        */

        while (k < m && l < n) {
            // Print the first row from the remaining rows
            for (i = l; i < n; ++i) {
                System.out.print(a[k][i]+" ");
            }
            k++;

            // Print the last column from the remaining columns
            for (i = k; i < m; ++i) {
                System.out.print(a[i][n-1]+" ");
            }
            n--;

            // Print the last row from the remaining rows */
            if ( k < m) {
                for (i = n-1; i >= l; --i) {
                    System.out.print(a[m-1][i]+" ");
                }
                m--;
            }

            // Print the first column from the remaining columns */
            if (l < n) {
                for (i = m-1; i >= k; --i) {
                    System.out.print(a[i][l]+" ");
                }
                l++;
            }
        }
    }
}
