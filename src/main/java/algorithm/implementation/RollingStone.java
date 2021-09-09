package algorithm.implementation;

public class RollingStone {
    public static void main(String[] args) {
        //int[][] stone = {{1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}};
        int[][] stone = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}};
        print(rollStone(stone, 1));
        print(rollStone(stone, 2));
        print(rollStone(stone, 3));
    }

    static int[][] rollStone(int[][] stone, int steps) {
        switch (steps %4) {
            case 0:
                return stone;
            case 1:
                return turnRight(stone);
            case 3:
                return turnLeft(stone);
            default:
                //return turnRight(turnRight(stone));
                return flip(stone);
        }
    }

    static int[][] turnRight(int[][] stone){
        int m = stone.length;
        int n = stone[0].length;
        int[][] result = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = stone[m-j-1][i];
            }
        }
        return result;
    }

    static int[][] turnLeft(int[][] stone){
        int m = stone.length;
        int n = stone[0].length;
        int[][] result = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = stone[j][n-i-1];
            }
        }
        return result;
    }

    static int[][] flip(int[][] stone){
        int m = stone.length;
        int n = stone[0].length;
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = stone[m-i-1][n-j-1];
            }
        }
        return result;
    }



    static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print (arr[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
