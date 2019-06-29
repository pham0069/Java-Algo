package data_structure.advanced.fenwick_tree;

import static data_structure.Assert.assertTrue;

/**
 * You are given a 2-D matrix in which each block contains 0 initially
 * The first block is defined by the coordinate (1,1)
 * and the last block is defined by the coordinate (N,N).
 * There are two types of queries.
 * 1. UPDATE x y W: updates the value of block (x,y) to W.
 * 2. QUERY x1 y1 x2 y2: calculates the sum of the value of blocks
 * whose x coordinate is between x1 and x2 (inclusive),
 * y coordinate between y1 and y2 (inclusive)
 *
 * Input Format
 * The first line will contain two integers N and M separated by a single space.
 * N defines the N * N matrix.
 * M defines the number of operations.
 * The next M lines will contain either
 * 1. UPDATE x y W
 * 2. QUERY  x1 y1 x2 y2
 *
 * Output Format
 * Print the result for each QUERY.
 *
 * Solution
 * Use 2D Fenwick tree
 */
public class SquareSummation {
    public static void main(String[] args){
        test2DFenwickTree();
        test2DPrefixSumTree();
    }

    public static void test2DFenwickTree(){
        int n = 4;
        TwoDimensionalFenwickTree tree = new TwoDimensionalFenwickTree(n, n);
        tree.updateValue(0, 0, 5);
        tree.updateValue(2, 3, 8);
        tree.updateValue(1, 2, 10);
        assertTrue(tree.getSum(1, 1) == 5, "tree.getSum(1, 1) should be 5");
        assertTrue(tree.getSum(2, 2) == 15, "tree.getSum(2, 2) should be 15");
        assertTrue(tree.getSum(3, 3) == 23, "tree.getSum(3, 3) should be 23");
        assertTrue(tree.getSum(1, 3) == 22, "tree.getSum(1, 3) should be 15");

    }

    public static void test2DPrefixSumTree(){
        int[][] array = {
            {0, 1, 2, 3},
            {5, 2, 9, 0},
            {8, 1, 7, 3},
            {0, 2, 4, 8}
        };
        TwoDimensionalPrefixSumTree tree = new TwoDimensionalPrefixSumTree(array);
        assertTrue(tree.getSum(1, 1) == 8, "tree.getSum(1, 1) should be 8");
        assertTrue(tree.getSum(2, 2) == 35, "tree.getSum(2, 2) should be 35");
        assertTrue(tree.getSum(3, 3) == 55, "tree.getSum(3, 3) should be 55");
        assertTrue(tree.getSum(1, 3) == 21, "tree.getSum(1, 3) should be 22");

    }

}

/**
 * 2D Fenwick tree is the efficient DS to update and get range-sum on a 2D array
 * Range sum is defined as sum of all elements in the rectangle defined by left upper corner
 * (x1, y1) and right lower corner (x2, y2)
 * 2D Fenwick Tree is built such that tree[i][j] is sum of all elements in the rectangle
 * defined by 2 corners parent(i), parent(j) to (i-1),(j-1)
 *
 * For example, tree[3][3] is sum of all elements in rect. defined by array[2][2] and array[2][2]
 * .i.e. tree[3][3] = array[2][2]
 * tree[3][2] is sum of all elements in rect. defined by array[2][0] and array[2][1]
 *.i.e. tree[3][2] = array[2][0] + array[2][1]
 *
 * Space complexity is O(mn)
 * Time complexity to create the tree (if initial array is not all-zeros) is O(mn*logm*logn)
 * Time complexity to update value is O(logm*logn)
 * Time complexity to get range-sum is O(logm*logn)
 */
class TwoDimensionalFenwickTree{
    private long[][] tree;
    private int[][] array;
    public TwoDimensionalFenwickTree(int m, int n){
        array = new int[m][n];
        tree = new long[m+1][n+1];
    }

    /**
     * update the value of array[x][y] to w
     * need to update tree[x+1][y+1] and all other affected tree nodes
     */
    public void updateValue(int x, int y, int w){
        int delta = w - array[x][y];
        updateValueBy(x, y, delta);
    }

    public void updateValueBy(int x, int y, int delta){
        array[x][y] += delta;
        for (int i = x+1; i < tree.length; i = getNextIndex(i)){
            for (int j = y+1; j < tree[0].length; j = getNextIndex(j)){
                tree[i][j]+= delta;
            }
        }
    }

    /**
     * get sum of elements in the rectangle defined by 2 corners (0,0) and (x, y)
     * i.e. sigma (array[i][j]) where 0<=i<=x, 0<=j<=y
     */
    public long getSum(int x, int y){
        long sum = 0;
        for (int i = x+1; i > 0; i = getParentIndex(i)){
            for (int j = y+1; j >0; j = getParentIndex(j)){
                sum+= tree[i][j];
            }
        }
        return sum;
    }

    /**
     * get sum of elements in the square defined by 2 corners (x1,y1) and (x2, y2)
     * i.e. sigma (array[i][j]) where x1<=i<=x2, y1<=j<=y2
     */
    public long getSum(int x1, int y1, int x2, int y2){
        long s1 = getSum(x2, y2);
        long s2 = getSum(x1-1, y2);
        long s3 = getSum(x2, y1-1);
        long s4 = getSum(x1-1, y1-1);
        return s1-s2-s3+s4;
    }

    private int getParentIndex(int i){
        return i - (i & -i);
    }
    private int getNextIndex(int i){
        return i + (i & -i);
    }
}

/**
 * Use to get prefix sum on a 2D array efficiently
 * but take O(n*n) time to update the array
 * Suitable if the array is immutable, i.e. no update queries, in dynamic programming
 *
 * tree[i][j] is sum of all elements in the rectangle defined by 2 corners 0, 0 to (i-1),(j-1)
 *
 * Space complexity is O(mn)
 * Time complexity to build the tree is O(mn)
 * Time complexity to get prefix-sum is O(1)
 */
class TwoDimensionalPrefixSumTree{
    private long[][] tree;
    public TwoDimensionalPrefixSumTree(int[][] array){
        tree = new long[array.length+1][array[0].length+1];
        buildTree(array);
    }
    public void buildTree(int[][] array){
        for (int i = 1; i <= array.length; i++){
            for (int j = 1; j <= array[0].length; j++){
                tree[i][j] = tree[i-1][j] + tree[i][j-1] + array[i-1][j-1] - tree[i-1][j-1];
            }
        }
    }
    public long getSum(int x, int y){
        long sum = 0;
        return tree[x+1][y+1];
    }
    public long getSum(int x1, int y1, int x2, int y2){
        return tree[x2+1][y2+1] - tree[x1][y2+1] - tree[x2][y1+1] + tree[x1][y1];
    }

}