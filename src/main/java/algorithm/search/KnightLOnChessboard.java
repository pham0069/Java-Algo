package algorithm.search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/knightl-on-chessboard/problem
 *
 * KnightL is a chess piece that moves in an L shape.
 * We define the possible moves of KnightL(a, b) as any movement from some position (x1, y1) to some (x2, y2)
 * satisfying either of the following:
 * x2 = x1 +/- a and y2 = y1 +/- b, or
 * x2 = x1 +/- b and y2 = y1 +/- a
 *
 * Note that (a, b) and (b, a) allow for the same exact set of movements.
 *
 * Given the value of  for an  chessboard, answer the following question for each  pair where :
 *
 * What is the minimum number of moves it takes for KnightL(a, b) to get from position (0, 0) to position (n-1, n-1)?
 * If it's not possible for the Knight to reach that destination, the answer is -1 instead.
 */
public class KnightLOnChessboard {
    public static void main(String[] args) {

        int[][] result = knightlOnAChessboard(5);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        //System.out.println(minMoves(5, 3, 3));
        char c = 'a';
        int a = c;
        System.out.println(a);
    }
    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        List<Position> moveKnightL(int n, int a, int b) {
            Set<Position> l = new HashSet<>();
            l.add(new Position(x+a, y+b));
            l.add(new Position(x+a, y-b));
            l.add(new Position(x-a, y+b));
            l.add(new Position(x-a, y-b));
            l.add(new Position(x+b, y+a));
            l.add(new Position(x+b, y-a));
            l.add(new Position(x-b, y+a));
            l.add(new Position(x-b, y-a));
            return l.stream().filter(p -> isValid(p, n)).collect(Collectors.toList());
        }

        boolean isValid(Position p, int n) {
            return p.x >= 0 && p.y >= 0 && p.x < n && p.y < n;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * x + y;
            return result;
        }

        @Override
        public boolean equals(Object that) {
            if (!(that instanceof Position)) {
                return false;
            }
            return this.x == ((Position)that).x
                    && this.y == ((Position)that).y;
        }
    }
    static class Pair {
        Position position;
        int rank;
        Pair(Position position, int rank) {
            this.position = position;
            this.rank = rank;
        }
    }
    static int[][] knightlOnAChessboard(int n) {
        int[][] result = new int[n-1][n-1];
        for (int i = 0; i < n-1; i++) {
            for (int j = i; j < n-1; j++) {
                result[i][j] = minMoves(n, i+1, j+1);
            }
        }

        // because of symmetry
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < i; j++) {
                result[i][j] = result[j][i];
            }
        }

        return result;

    }

    static int minMoves(int n, int a, int b) {
        Deque<Pair> queue = new ArrayDeque<>();
        Set<Position> added = new HashSet<>();
        Position start = new Position(0, 0);
        queue.add(new Pair(start, 0));
        added.add(start);
        Position goal = new Position(n-1, n-1);
        while (!queue.isEmpty()) {
            Pair top = queue.pop();
            Position cur = top.position;

            if (cur.equals(goal)) {
                return top.rank;
            }

            int nextRank = top.rank+1;
            for (Position next : cur.moveKnightL(n, a, b)) {
                if (!added.contains(next)) {
                    queue.add(new Pair(next, nextRank));
                    added.add(next);
                }
            }
        }
        return -1;
    }
}

