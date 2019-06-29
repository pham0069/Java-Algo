package algorithm.recursive;

import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/flood-fill-algorithm/0
 * https://www.hackerearth.com/practice/algorithms/graphs/flood-fill-algorithm/tutorial/
 * https://www.codeproject.com/Articles/6017/QuickFill-An-efficient-flood-fill-algorithm
 *
 *
 * Given a 2D screen, location of a pixel in the screen (x,y) and a color(K), your task is to replace color of the given
 * pixel and all adjacent(excluding diagonally adjacent) same colored pixels with the given color K.
 *
 * Example:
 * Input:
 {{1, 1, 1, 1, 1, 1, 1, 1},
 {1, 1, 1, 1, 1, 1, 0, 0},
 {1, 0, 0, 1, 1, 0, 1, 1},
 {1, 2, 2, 2, 2, 0, 1, 0},
 {1, 1, 1, 2, 2, 0, 1, 0},
 {1, 1, 1, 2, 2, 2, 2, 0},
 {1, 1, 1, 1, 1, 2, 1, 1},
 {1, 1, 1, 1, 1, 2, 2, 1},
 };
 * x=4, y=4, color=3

 * Output:
 {{1, 1, 1, 1, 1, 1, 1, 1},
 {1, 1, 1, 1, 1, 1, 0, 0},
 {1, 0, 0, 1, 1, 0, 1, 1},
 {1, 3, 3, 3, 3, 0, 1, 0},
 {1, 1, 1, 3, 3, 0, 1, 0},
 {1, 1, 1, 3, 3, 3, 3, 0},
 {1, 1, 1, 1, 1, 3, 1, 1},
 {1, 1, 1, 1, 1, 3, 3, 1}, };
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * Basic 4 Way Recursive Method
 * This is the most basic of all flood filling methods, as well as the simplest.
 * (i, j) --> (i-1, j), (i+1, j), (i, j-1), (i, j+1)
 * It is simple to implement by even a beginner programmer but recursive calls may cause overflow stack
 * Another weakness is that pixels are repeated sampled (i.e. (i, j) --> call (i-1, j) --> call (i, j) again)
 *
 * To allow leak on the diagonals, we can extend to 8 Way Recursive Method
 * This can cover the connected pixels in diagonal besides up, down, left, right.
 * (i, j) --> (i-1, j), (i+1, j), (i, j-1), (i, j+1),
 * (i-1, j-1), (i-1, j+1), (i+1, j-1), (i+1, j+1)
 * ============================
 * Recursive Scan Line Method
 * The recursive scan line method is a type of 4 way method, but is more efficient than the 4 or 8 way single pixel
 * recursive methods.

 Its strength: complete lines are scanned.

 Its weaknesses: repeated sampling of some lines and recursion (may overflow stack).
 *
 */
public class FloodFillAlgo {
    public static void main(String[] args) {
        int[][] matrix = {{1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 0},
                {1, 0, 0, 1, 1, 0, 1, 1},
                {1, 2, 2, 2, 2, 0, 1, 0},
                {1, 1, 1, 2, 2, 0, 1, 0},
                {1, 1, 1, 2, 2, 2, 2, 0},
                {1, 1, 1, 1, 1, 2, 1, 1},
                {1, 1, 1, 1, 1, 2, 2, 1},
        };
        int x = 4;
        int y = 4;
        int newValue = 3;
        int prevValue = matrix[x][y];

        if (prevValue != newValue)
            //floodFill(matrix, x, y, prevValue, newValue);
            floodFill2(matrix, x, y, y, prevValue, newValue);
        printMatrix(matrix);
    }

    static void floodFill(int[][] matrix, int i, int j, int prevValue, int newValue) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n)
            return;
        if (matrix[i][j] != prevValue)
            return;
        matrix[i][j] = newValue;
        floodFill(matrix, i-1, j, prevValue, newValue);
        floodFill(matrix, i+1, j, prevValue, newValue);
        floodFill(matrix, i, j-1, prevValue, newValue);
        floodFill(matrix, i, j+1, prevValue, newValue);
    }

    static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    static void floodFill2(int[][] matrix, int x, int y1, int y2, int prevValue, int newValue) {
        int yLeft, yRight;
        int m = matrix.length;
        int n = matrix[0].length;
        if(x < 0 || x > m)
            return;
        for(yLeft = y1; yLeft >= 0; --yLeft ) { // scan left
            if (matrix[x][yLeft] != prevValue)
                break;
            matrix[x][yLeft] = newValue;
        }
        if( yLeft < y1 ) {
            floodFill2(matrix, x-1, yLeft, y1, prevValue, newValue); // fill child
            floodFill2(matrix, x+1, yLeft, y1, prevValue, newValue); // fill child
            ++y1;
        }
        for( yRight = y2;  yRight <= n; ++yRight ) { // scan right
            if (matrix[x][yRight] != prevValue)
                break;
            matrix[x][yRight] = newValue;
        }
        if(  yRight > y2 ) {
            floodFill2(matrix, x-1, y2, yRight, prevValue, newValue); // fill child
            floodFill2(matrix, x+1, y2, yRight, prevValue, newValue); // fill child
            --y2;
        }
        for( yRight = y1; yRight <= y2 && yRight <= n; ++yRight ) {  // scan between
            if( matrix[x][yRight] == prevValue )
                matrix[x][yRight] = newValue;
            else {
                if( y1 < yRight ) {
                    // fill child
                    floodFill2(matrix, x-1, y1, yRight-1, prevValue, newValue);
                    // fill child
                    floodFill2(matrix, x+1, y1, yRight-1, prevValue, newValue);
                    y1 = yRight;
                }
                // Note: This function still works if this step is removed.
                for( ; yRight <= y2 && yRight <= n; ++yRight) { // skip over border
                    if (matrix[x][yRight] == prevValue) {
                        y1 = yRight--;
                        break;
                    }
                }
            }
        }
    }

}
