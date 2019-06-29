package algorithm.search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/red-knights-shortest-path/problem
 *
 * In ordinary chess, the pieces are only of two colors, black and white.
 * In our version of chess, we are including new pieces with unique movements.
 * One of the most powerful pieces in this version is the red knight.
 *
 * The red knight can move to six different positions based on its current position
 * (UpperLeft, UpperRight, Right, LowerRight, LowerLeft, Left) as shown in the figure below.
 *
 *     |UL|_|UR|
 *    _|_ | | _|_
 *   |L|_ |K| _|R|
 *     |_ |_| _|
 *     |LL|_|LR|
 *
 * The board is a grid of size nxn. Each cell is identified with a pair of coordinates (i, j),
 * where i is the row number and j is the column number, both zero-indexed.
 * Thus, (0, 0) is the upper-left corner and (n-1, n-1) is the bottom-right corner.
 *
 * Complete the function printShortestPath, which takes as input the grid size n,
 * and the coordinates of the starting and ending position (i_start, j_start) and (i_end, j_end), respectively, as input.
 * The function does not return anything.
 *
 * Given the coordinates of the starting position of the red knight and the coordinates of the destination,
 * print the minimum number of moves that the red knight has to make in order to reach the destination and after that,
 * print the order of the moves that must be followed to reach the destination in the shortest way.
 * If the destination cannot be reached, print only the word "Impossible".
 *
 * Note: There may be multiple shortest paths leading to the destination.
 * Hence, assume that the red knight considers its possible neighbor locations in the following order of priority:
 * UL, UR, R, LR, LL, L.
 * In other words, if there are multiple possible options, the red knight prioritizes the first move in this list,
 * as long as the shortest path is still achievable.
 *
 */
public class RedKnightShortestPath {
    static class State {
        Point point;
        List<String> moves;
        State(Point point, List<String> moves) {
            this.point = point;
            this.moves = moves;
        }
    }
    
    static class Pair {
        String move;
        Point point;
        Pair(String move, Point point) {
            this.move = move;
            this.point = point;
        }
    }

    static class Point {
        int i;
        int j;

        Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        List<Pair> getAllNextPoints(int n) {
            List<Pair> next = new ArrayList<>();
            next.add(new Pair("UL", new Point(i-2, j-1)));//UL
            next.add(new Pair("UR", new Point(i-2, j+1)));//UR
            next.add(new Pair("R", new Point(i, j+2)));//R
            next.add(new Pair("LR", new Point(i+2, j+1)));//LR
            next.add(new Pair("LL", new Point(i+2, j-1)));//LL
            next.add(new Pair("L", new Point(i, j-2)));//L
            return next.stream().filter(p -> p.point.isValid(n)).collect(Collectors.toList());
        }

        boolean isValid(int n) {
            return i >= 0 && i < n && j >= 0 && j < n;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) {
                return false;
            }
            Point that = (Point) o;
            return that.i == this.i && that.j == this.j;
        }

        public int hashCode() {
            return i*131 + j;
        }
    }

    static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
        Deque<State> deque = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();

        Point start = new Point(i_start, j_start);
        Point goal = new Point(i_end, j_end);
        deque.add(new State(start, new ArrayList<>()));
        visited.add(start);

        while (!deque.isEmpty()) {
            State state = deque.remove();
            Point point = state.point;
            if (point.equals(goal)) {
                printResult(state.moves);
                return;
            }

            for (Pair pair : point.getAllNextPoints(n)) {
                if (!visited.contains(pair.point)) {
                    List<String> newMoves = new ArrayList<>(state.moves.size() + 1);
                    state.moves.stream().forEach(newMoves::add);
                    newMoves.add(pair.move);
                    deque.add(new State(pair.point, newMoves));
                    visited.add(pair.point);
                }
            }
        }

        System.out.println("Impossible");
    }
    
    static void printResult(List<String> moves) {
        System.out.println(moves.size());
        System.out.println(moves.stream().collect(Collectors.joining(" ")));
    }

    

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String[] i_startJ_start = scanner.nextLine().split(" ");

        int i_start = Integer.parseInt(i_startJ_start[0]);

        int j_start = Integer.parseInt(i_startJ_start[1]);

        int i_end = Integer.parseInt(i_startJ_start[2]);

        int j_end = Integer.parseInt(i_startJ_start[3]);

        printShortestPath(n, i_start, j_start, i_end, j_end);

        scanner.close();
    }
}
