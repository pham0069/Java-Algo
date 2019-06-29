package data_structure.advanced.fenwick_tree;

import java.util.Scanner;

/**
 * You are given a 3-D matrix in which each block contains 0 initially
 * The first block is defined by the coordinate (1,1,1)
 * and the last block is defined by the coordinate (N,N,N).
 * There are two types of queries.
 * 1. UPDATE x y z W: updates the value of block (x,y,z) to W.
 * 2. QUERY x1 y1 z1 x2 y2 z2: calculates the sum of the value of blocks
 * whose x coordinate is between x1 and x2 (inclusive),
 * y coordinate between y1 and y2 (inclusive)
 * and z coordinate between z1 and z2 (inclusive).
 *
 * Input Format
 * The first line contains an integer T, the number of test-cases. T testcases follow.
 * For each test case, the first line will contain two integers N and M separated by a single space.
 * N defines the N * N * N matrix.
 * M defines the number of operations.
 * The next M lines will contain either
 * 1. UPDATE x y z W
 * 2. QUERY  x1 y1 z1 x2 y2 z2
 *
 * Output Format
 * Print the result for each QUERY.
 *
 * Solution
 * Use 3D Fenwick tree
 */
public class CubeSummation {
    public static void main(String[] args) {
        test3DFenwickTree();
    }

    public static void test3DFenwickTree() {
        Scanner sc = new Scanner(System.in);
        int test = sc.nextInt();
        try{
            for (int i = 0; i < test; i++){
                int n = sc.nextInt();
                int m = sc.nextInt();
                ThreeDimensionalFenwickTree tree = new ThreeDimensionalFenwickTree(n, n, n);
                for (int j = 0; j < m; j++){
                    String op = sc.next();
                    if (op.equals("UPDATE")){
                        int x = sc.nextInt() -1;
                        int y = sc.nextInt() -1;
                        int z = sc.nextInt() -1;
                        int val = sc.nextInt();
                        tree.updateValue(x, y, z, val);
                    }
                    else if (op.equals("QUERY")){
                        int x1 = sc.nextInt() -1;
                        int y1 = sc.nextInt() -1;
                        int z1 = sc.nextInt() -1;
                        int x2 = sc.nextInt() -1;
                        int y2 = sc.nextInt() -1;
                        int z2 = sc.nextInt() -1;
                        long result = tree.getSum(x1, y1, z1, x2, y2, z2);
                        System.out.println(result);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
/**
 * 2D Fenwick tree is the efficient DS to update and get range-sum on a 2D array
 * Range sum is defined as sum of all elements in the rectangle defined by left upper corner
 * (x1, y1) and right lower corner (x2, y2)
 * 2D Fenwick Tree is built such that tree[i][j] is sum of all elements in the rectangle
 * defined by 2 corners parent(i), parent(j) to (i-1),(j-1)
 *
 * Space complexity is O(mn)
 * Time complexity to create the tree (if initial array is not all-zeros) is O(mn*logm*logn)
 * Time complexity to update value is O(logm*logn)
 * Time complexity to get range-sum is O(logm*logn)
 */
class ThreeDimensionalFenwickTree{
    private long[][][] tree;
    private int[][][] array;
    public ThreeDimensionalFenwickTree(int m, int n, int p){
        array = new int[m][n][p];
        tree = new long[m+1][n+1][p+1];
    }

    /**
     * update the value of array[x][y][z] to w
     * need to update tree[x+1][y+1][z+1] and all other affected tree nodes
     */
    public void updateValue(int x, int y, int z, int w){
        int delta = w - array[x][y][z];
        array[x][y][z] = w;
        for (int i = x+1; i < tree.length; i = getNextIndex(i)){
            for (int j = y+1; j < tree[0].length; j = getNextIndex(j)){
                for (int k = z+1; k < tree[0][0].length; k = getNextIndex(k))
                    tree[i][j][k]+= delta;
            }
        }
    }

    /**
     * get sum of elements in the rectangle defined by 2 corners (0,0) and (x, y)
     * i.e. sigma (array[i][j]) where 0<=i<=x, 0<=j<=y
     */
    public long getSum(int x, int y, int z){
        long sum = 0;
        for (int i = x+1; i > 0; i = getParentIndex(i)){
            for (int j = y+1; j > 0; j = getParentIndex(j)){
                for (int k = z+1; k > 0; k = getParentIndex(k)) {
                    sum += tree[i][j][k];
                }
            }
        }
        return sum;
    }

    /**
     * get sum of elements in the square defined by 2 corners (x1,y1) and (x2, y2)
     * i.e. sigma (array[i][j][k]) where x1<=i<=x2, y1<=j<=y2, z1<=k<=z2
     */
    public long getSum(int x1, int y1, int z1, int x2, int y2, int z2){
        long s = getSum(x2, y2, z2);
        long s1 = getSum(x1-1, y2, z2);
        long s2 = getSum(x2, y1-1, z2);
        long s3 = getSum(x2, y2, z1-1);
        long s4 = getSum(x1-1, y1-1, z2);
        long s5 = getSum(x1-1, y2, z1-1);
        long s6 = getSum(x2, y1-1, z1-1);
        long s7 = getSum(x1-1, y1-1, z1-1);
        return s-s1-s2-s3+s4+s5+s6-s7;
    }

    private int getParentIndex(int i){
        return i - (i & -i);
    }
    private int getNextIndex(int i){
        return i + (i & -i);
    }
}
