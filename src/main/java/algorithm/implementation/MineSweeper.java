package algorithm.implementation;

public class MineSweeper {
    public static void main(String[] args) {
        int[][] arr = {{1, 0}, {1, 0}};
        int[][] result = toCountMineMatrix(arr);
        print(result);
    }

    static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print (arr[i][j]+ " ");
            }
            System.out.println();
        }
    }

    static int[][] toCountMineMatrix(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = getMineCount(arr, i, j, m, n);
            }
        }
        return result;
    }

    static int getMineCount(int[][] arr, int row, int col, int m, int n) {
        int count = 0;
        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (isValidPos(i, j, m, n)) {
                    count += arr[i][j];
                }
            }
        }
        count -= arr[row][col]; // dont count the mine in this position
        return count;
    }

    static boolean isValidPos(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
