package data_structure.queue;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/castle-on-the-grid/problem
 *
 * You are given a square grid with some cells open (.) and some blocked (X). Your playing piece can move along any row
 * or column until it reaches the edge of the grid or a blocked cell. Given a grid, a start and an end position,
 * determine the number of moves it will take to get to the end position.
 * For example, you are given a grid with sides  described as follows:
 ...
 .X.
 ...
 * Your starting position (startX, startY) = (0, 0) so you start in the top left corner. The ending position is (goalX,
 * goalY) = (1, 2). The path is (0, 0) -> (0, 2) -> (1, 2). It takes 2 moves to get to the goal.
 *
 * Function Description
 * Complete the minimumMoves function in the editor. It must print an integer denoting the minimum moves required to get
 * from the starting position to the goal.
 *
 * minimumMoves has the following parameter(s):
 * grid: an array of strings representing the rows of the grid
 * startX: an integer
 * startY: an integer
 * goalX: an integer
 * goalY: an integer
 * ---------------------------------------------------------------------------------------------------------------------
 * Use BFS to traverse from start point to goal point. 2 nodes are connected in 1 move if they are on a straight line
 * (horizontal or vertical) and there is no forbidden cell between them.
 * Since the problem is asking for the shortest number of turns rather than steps, we modify a classic BFS accordingly.
 * When getting a list of neighbors for a cell, rather than getting only the immediate surrounding neighbors, you can
 * get all neighbors in the northern path (until either the top of the grid is reached, or a forbidden cell is
 * encountered) and they would all have a distance of the current cells distance + 1. Repeat for east, south and west.
 * Once this is done, you'll observe that the target cell has a number relating to the minimum number of turns.
 */
public class CastleOnTheGrid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++){
            String s = sc.next();
            for (int j = 0; j < n; j++){
                grid[i][j] = s.charAt(j);
            }
        }
        int startX = sc.nextInt(), startY = sc.nextInt();
        int goalX = sc.nextInt(), goalY = sc.nextInt();

        getMinNumberOfMoves(grid, startX, startY, goalX, goalY);
        getMinNumberOfMoves2(grid, startX, startY, goalX, goalY);

    }

    /**
     * Use array as queue
     */
    static void getMinNumberOfMoves(char[][] grid, int startX, int startY, int goalX, int goalY) {
        int n = grid.length;
        int[][] distance = new int[n][n];
        distance[startX][startY] = 1;

        int[] queueX = new int[n*n];
        int[] queueY = new int[n*n];
        int e = 0;//index of point to process in queue
        queueX[e] = startX;
        queueY[e] = startY;
        e++;
        //put neighbors to the queue
        for (int i = 0; i < e; i++) {
            int x = queueX[i];
            int y = queueY[i];
            //go right
            for (int j = y + 1; j < n; j++) {
                if (grid[x][j] == 'X')
                    break;
                if (distance[x][j] == 0) {
                    //add (x, j) to end of queue
                    queueX[e] = x;
                    queueY[e] = j;
                    e++;
                    //update distance
                    distance[x][j] = distance[x][y] + 1;
                }
            }
            //go left
            for (int j = y - 1; j >= 0; j--) {
                if (grid[x][j] == 'X')
                    break;
                if (distance[x][j]  == 0) {
                    queueX[e] = x;
                    queueY[e] = j;
                    e++;
                    distance[x][j] = distance[x][y] + 1;
                }
            }
            //go up
            for (int j = x + 1; j < n; j++) {
                if (grid[j][y] == 'X')
                    break;
                if (distance[j][y] == 0) {
                    queueX[e] = j;
                    queueY[e] = y;
                    e++;
                    distance[j][y] = distance[x][y] + 1;
                }
            }
            //go down
            for (int j = x - 1; j >= 0; j--) {
                if (grid[j][y] == 'X')
                    break;
                if (distance[j][y] == 0) {
                    queueX[e] = j;
                    queueY[e] = y;
                    e++;
                    distance[j][y] = distance[x][y] + 1;
                }
            }
        }
        System.out.println(distance[goalX][goalY] - 1);
    }

    static void getMinNumberOfMoves2(char[][] grid, int startX, int startY, int goalX, int goalY) {
        int n = grid.length;
        Queue<Point> queue = new ArrayDeque<>();
        int[][] distance = initializeDistance(grid);

        Point start = new Point(startX, startY);
        queue.add(start);
        distance[startX][startY] = 0;
        while(!queue.isEmpty()){
            Point current = queue.remove();
            traverse(n, current, grid, distance, queue);
        }
        System.out.println(distance[goalX][goalY]);
    }

    static int[][] initializeDistance(char[][] grid) {
        int n = grid.length;
        int[][] distance = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = -1;
            }
        }
        return distance;
    }

    static void traverse(int n, Point p, char[][] grid, int[][] distance, Queue<Point> queue){
        int x = p.x;
        int y = p.y;

        int value = distance[x][y] + 1;
        for(int i = x; i < n ; i++){
            if (grid[i][y] == 'X')
                break;
            if (distance[i][y] == -1) {
                distance[i][y] = value;
                queue.add(new Point(i, y));
            }
        }
        for(int i = x; i >= 0; i--){
            if (grid[i][y] == 'X')
                break;
            if (distance[i][y] == -1) {
                distance[i][y] = value;
                queue.add(new Point(i, y));
            }
        }
        for(int i = y; i < n; i++){
            if (grid[x][i] == 'X')
                break;
            if (distance[x][i] == -1) {
                distance[x][i] = value;
                queue.add(new Point(x, i));
            }
        }
        for(int i = y; i >= 0 && distance[x][i]!=-1; i--){
            if (grid[x][i] == 'X')
                break;
            if (distance[x][i] == -1) {
                distance[x][i] = value;
                queue.add(new Point(x, i));
            }
        }
    }

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
