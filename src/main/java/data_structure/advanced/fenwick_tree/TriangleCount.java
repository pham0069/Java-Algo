package data_structure.advanced.fenwick_tree;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/counting-triangles-in-a-rectangular-space-using-2d-bit/
 * Given a 2D plane where 0 <= x, y < max. There are two possible types of queries on the plane:
 * 1. INSERT x y: insert a point (x, y) coordinate
 * 2. TRIANGLE x1 y1 x2 y2: print the number of triangles that can be formed, by joining the points
 * inside the rectangle, described by two points (x1, y1) and (x2, y2), where (x1, y1) is the lower
 * left corner and (x2, y2) is the upper right corner. We will represent it as {(x1, y1), (x2, y2)}
 * Degenerated triangles (3 points on same line) is included.
 *
 * Input Format
 * The first line contains an integer T, the number of test-cases. T testcases follow.
 * For each test case, the first line will contain two integers N and Q separated by a single space.
 * N defines the boundary coordinate of 2D plane.
 * Q defines the number of queries.
 * The next Q lines will contain either
 * 1. INSERT x y
 * 2. TRIANGLE  x1 y1 x2 y2
 *
 * Output Format
 * Print the result for each TRIANGLE.
 */
public class TriangleCount {
    public static void main(String[] args){
        testTriangleCount();
    }

    public static void testTriangleCount() {
        Scanner sc = new Scanner(System.in);
        int test = sc.nextInt();
        try{
            for (int i = 0; i < test; i++){
                int max = sc.nextInt();
                int q = sc.nextInt();
                TwoDimensionalFenwickTree tree = new TwoDimensionalFenwickTree(max, max);
                for (int j = 0; j < q; j++){
                    String op = sc.next();
                    if (op.equals("INSERT")){
                        int x = sc.nextInt();
                        int y = sc.nextInt();
                        tree.updateValue(x, y, 1);
                    }
                    else if (op.equals("TRIANGLE")){
                        int x1 = sc.nextInt();
                        int y1 = sc.nextInt();
                        int x2 = sc.nextInt();
                        int y2 = sc.nextInt();
                        long numberOfPoints = tree.getSum(x1, y1, x2, y2);
                        System.out.println(getNumberOfTriangles(numberOfPoints));
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static long getNumberOfTriangles(long numberOfPoints){
        return numberOfPoints*(numberOfPoints-1)*(numberOfPoints-2)/6;
    }
}
