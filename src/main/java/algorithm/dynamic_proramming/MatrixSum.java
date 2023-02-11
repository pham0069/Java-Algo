package algorithm.dynamic_proramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a matrix and a target,
 * return the number of non-empty submatrices that sum to target
 */
public class MatrixSum {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        int target = 5;


        //System.out.println(getCount(matrix, target));
        System.out.println(getCount2(matrix, target));
    }

    /**
     * O(m*m*n*n)
     */
    private static int getCount(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        Counter counter = new Counter();
        int[][][][] dynamicSum = new int[m][n][m][n];

        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j++) {
                fillSum(matrix, dynamicSum, m, n, i, j, counter, target);
            }
        }

        return counter.getCount();
    }

    private static void fillSum(int[][] matrix, int[][][][] dynamicSum,
                                int m, int n,
                                int i, int j,
                                Counter counter, int target) {
        for (int h = 0; h < m-i; h++) {
            for (int w = 0; w < n-j; w++) {
                if (w == 0) {
                    dynamicSum[i][j][h][w] = matrix[i+h][j];
                } else {
                    dynamicSum[i][j][h][w] = dynamicSum[i][j][h][w - 1]
                            + matrix[i + h][j + w];
                }
                if (dynamicSum[i][j][h][w] == target) {
                    counter.increment();
                }
            }
        }
    }

    private static class Counter {
        int i = 0;
        void increment() {
            i++;
        }

        int getCount() {
            return i;
        }
    }

    /**
     * O(m*n*min(m, n))
     */
    private static int getCount2(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int count = 0;

        // get accumulated sum for row -> O(m*n)
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                matrix[i][j] += matrix[i][j-1];
            }
        }

        // for any 2 columns -> O(n*n*m)
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {

                Map<Integer, Integer> countMap = new HashMap<>();
                int accumulateSum = 0;
                for (int row = 0; row < m;  row++) {
                    int rowSum = matrix[row][end];
                    if (start > 0) {
                        rowSum -= matrix[row][start-1];
                    }
                    accumulateSum += rowSum;

                    // check if any submatrix from some row i (>0) to this row has sum equal to target
                    count += countMap.getOrDefault(accumulateSum-target, 0);
                    countMap.putIfAbsent(accumulateSum, 0);
                    countMap.put(accumulateSum, countMap.get(accumulateSum) + 1);
                    // check if this submatrix (from row i = 0 to this row) has sum equal to target
                    if (accumulateSum == target) {
                        count += 1;
                    }
                }
            }
        }

        return count;
    }
}
