package algorithm.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/connected-cell-in-a-grid/problem
 *
 * Consider a matrix where each cell contains either a 0 or a 1.
 * Any cell containing a 1 is called a filled cell.
 * Two cells are said to be connected if they are adjacent to each other horizontally, vertically, or diagonally.
 * In the following grid, all cells marked X are connected to the cell marked Y.
 *
 * XXX
 * XYX
 * XXX
 *
 * If one or more filled cells are also connected, they form a region.
 * Note that each cell in a region is connected to zero or more cells in the region
 * but is not necessarily directly connected to all the other cells in the region.
 *
 * Given an nxm matrix, find and print the number of cells in the largest region in the matrix.
 * Note that there may be more than one region in the matrix.
 *
 * For example, there are two regions in the following 3x3  matrix.
 * The larger region at the top left contains 3 cells. The smaller one at the bottom right contains 1.
 *
 * 110
 * 100
 * 001
 *
 */
public class ConnectedCellsInGrid {

    static class Pair{
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        List<Pair> getConnected(int n, int m) {
            List<Pair> result = new ArrayList<>();
            result.add(new Pair(x+1, y));
            result.add(new Pair(x-1, y));
            result.add(new Pair(x+1, y+1));
            result.add(new Pair(x-1, y+1));
            result.add(new Pair(x, y+1));
            result.add(new Pair(x+1, y-1));
            result.add(new Pair(x-1, y-1));
            result.add(new Pair(x, y-1));
            return result.stream().filter(p -> isValid(p, n, m)).collect(Collectors.toList());
        }

        boolean isValid(Pair p, int n, int m) {
            return p.x >= 0 && p.y >= 0 && p.x < n && p.y < m;
        }
    }
    static int connectedCell(int[][] matrix, int n, int m) {
        int maxRegion = 0;
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    int region = getRegion(matrix, n, m, i, j, visited);
                    maxRegion = Math.max(region, maxRegion);
                }
            }
        }
        return maxRegion;
    }

    static int getRegion(int[][] matrix, int n, int m, int i, int j, boolean[][] visited) {
        int region = 0;
        Stack<Pair> stack = new Stack<>();
        Pair cur = new Pair(i, j);
        stack.add(cur);
        visited[i][j] = true;

        while(!stack.isEmpty()) {
            cur = stack.peek();

            boolean shouldPop = true;
            for (Pair p : cur.getConnected(n, m)) {
                if (matrix[p.x][p.y] == 1 && !visited[p.x][p.y]) {
                    stack.add(p);
                    visited[p.x][p.y] = true;

                    shouldPop = false;

                    break;
                }
            }

            if (shouldPop) {
                Pair pop = stack.pop();
                //System.out.println(pop.x + " " + pop.y);
                region ++;
            }
        }
        return region;
    }

    public static void main(String[] args) {
        System.out.println(connectedCell(new int[][] {{1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 1, 0}, {1, 0, 0, 0}}, 4, 4));
    }

}
