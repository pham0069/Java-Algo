package data_structure.advanced.fenwick_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * You need to find the inversion count of a given 2D matrix of size NxN.
 * Inversion count in a matrix is defined as the number of pairs satisfying
 * the following conditions : maxtrix[x2][y2] < matrix[x1][y1] where x1 ≤ x2, y1 ≤ y2
 * For example, input = {{7, 5},{3, 1}}
 * Output = 5 as there are five inversions (7, 5), (3, 1), (7, 3), (5, 1) and (7, 1)
 *
 * Naive solution is 4 loops, making time complexity as O(n^4)
 * Improving performance using 2D BIT in O(n^2*(logn)^2)
 */
public class TwoDimensionalInversionCount {
    public static void main(String[] args){
        int[][] matrix = {{7, 7},{3, 1}};
        System.out.println(getInversionCount(matrix));
    }

    private static int getInversionCount(int[][] matrix){
        List<Tuple> tuples = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                tuples.add(new Tuple(matrix[i][j], i, j));
        Collections.sort(tuples);
        TwoDimensionalFenwickTree bit = new TwoDimensionalFenwickTree(m, n);
        int inversionCount = 0;
        for (int i = 0; i < tuples.size(); i++){
            Tuple tuple = tuples.get(i);
            inversionCount += bit.getSum(tuple.row, tuple.col);
            bit.updateValueBy(tuple.row, tuple.col, 1);
        }
        return inversionCount;
    }
    static class Tuple implements Comparable<Tuple>{
        int value;
        int row, col;
        public Tuple(int value, int row, int col){
            this.value = value;
            this.row = row;
            this.col = col;
        }
        public int compareTo(Tuple that){
            if (that == null)
                return 1;
            if (this.value != that.value)
                return that.value - this.value;
            //sort in descending row and col index
            //to exclude pairs with equal value in inversion count
            if (this.row != that.row)
                return that.row - this.row;
            return that.col - this.col;
        }
    }

}
