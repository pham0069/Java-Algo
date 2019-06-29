package algorithm.search;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/count-luck
 *
 * Ron and Hermione are deep in the Forbidden Forest collecting potion ingredients,
 * and they've managed to lose their way.
 * The path out of the forest is blocked,
 * so they must make their way to a portkey that will transport them back to Hogwarts.
 *
 * Consider the forest as an N*M grid.
 * Each cell is either empty (represented by .) or blocked by a tree (represented by X).
 * Ron and Hermione can move (together inside a single cell)
 * LEFT, RIGHT, UP, and DOWN through empty cells,
 * but they cannot travel through a tree cell.
 * Their starting cell is marked with the character M,
 * and the cell with the portkey is marked with a *.
 * The upper-left corner is indexed as (0, 0).
 *
 * .X.X......X
 * .X*.X.XXX.X
 * .XX.X.XM...
 * ......XXXX.
 *
 * In example above, Ron and Hermione are located at index (2, 7) and the portkey is at (1, 2).
 * Each cell is indexed according to Matrix Conventions.
 *
 * Hermione decides it's time to find the portkey and leave.
 * They start along the path and each time they have to choose a direction,
 * she waves her wand and it points to the correct direction.
 * Ron is betting that she will have to wave her wand exactly k times.
 * Can you determine if Ron's guesses are correct?
 *
 * The map from above has been redrawn with the path indicated as a series where M is the starting point
 * (no decision in this case), 1 indicates a decision point and 0 is just a step on the path:
 *
 * .X.X.10000X
 * .X*0X0XXX0X
 * .XX0X0XM01.
 * ...100XXXX.
 *
 * There are three instances marked with  where Hermione must use her wand.
 *
 * Note: It is guaranteed that there is only one path from the starting location to the portkey.
 *
 *
 * Since it's guaranteed there's only path -> just need to do depth first search until portkey is reached
 *
 */
public class CountLuck {
    static String countLuck(char[][] matrix, int k) {
        int[] start = getStartingPosition(matrix);
        Integer turns = depthFirstSearch(matrix, start[0], start[1], 0);
        if (turns != null && turns == k) {
            return "Impressed";
        }
        System.out.println(turns);
        return "Oops!";
    }

    static int[] getStartingPosition(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 'M') {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1};
    }

    static Integer depthFirstSearch(char[][] matrix, int row, int col, int turns) {
        int n = matrix.length;
        int m = matrix[0].length;

        //reach invalid position
        if (row < 0 || row >= n || col < 0 || col >= m) {
            return null;
        } else if (matrix[row][col] == 'X') { // stumble over tree
            return null;
        } else if (matrix[row][col] == '*') { // bingo
            return turns;
        }

        int[][] next = {
                {row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}
        };
        int r, c;

        // check if there are single or multiple paths to go from current position (row, col)
        int path = 0;
        for (int[] cell : next) {
            r = cell[0];
            c = cell[1];
            if ( r >= 0 && r < n && c >= 0 && c < m) {
                if (matrix[r][c] == '.' || matrix[r][c] == '*') {
                    path += 1;
                }
            }

        }

        // if there are multiple path, need to wave magic wand
        // to make a decision which direction to go?
        // mark this row/col as visited
        if (path > 1) {
            turns ++;
            matrix[row][col] = '1';
        } else {
            matrix[row][col] = '0';
        }

        for (int[] cell : next) {
            r = cell[0];
            c = cell[1];
            if ( r >= 0 && r < n && c >= 0 && c < m) {
                // proceed to search on unvisited cell
                if (matrix[r][c] != '1' && matrix[r][c] != '0') {
                    Integer found = depthFirstSearch(matrix, r, c, turns);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            char[][] matrix = new char[n][m];

            for (int i = 0; i < n; i++) {
                String matrixItem = scanner.nextLine();
                matrix[i] = matrixItem.toCharArray();
            }

            int k = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String result = countLuck(matrix, k);

            System.out.println(result);
        }

        scanner.close();
    }

}
