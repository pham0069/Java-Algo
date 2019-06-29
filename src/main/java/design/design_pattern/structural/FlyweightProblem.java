package design.design_pattern.structural;

/**
 * Given a matrix where each cell should be able to report its position (row and col)
 * The straightforward approach is create an instance for each cell.
 * Designing objects down to the lowest levels of system granularity provides optimal flexibility
 *
 * However, if the matrix is big, i.e. lots of rows and cols, it can be unacceptable expensive in terms of performance
 * and memory
 */
public class FlyweightProblem {
    static class Gazillion {
        private static int num = 0;
        private int row, col;

        Gazillion(int maxPerRow) {
            row = num / maxPerRow;
            col = num % maxPerRow;
            num++;
        }

        void report() {
            System.out.print(" " + row + col);
        }
    }

    public static void main(String[] args) {
        int rows = 6, cols = 100;
        Gazillion[][] matrix = new Gazillion[rows][cols];
        for (int i=0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Gazillion(cols);
            }
        }
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                matrix[i][j].report();
            }
            System.out.println();
        }
    }
}
