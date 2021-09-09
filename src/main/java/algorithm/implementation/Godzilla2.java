package algorithm.implementation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//timeout
public class Godzilla2 {
    static final char GODZILLA = 'G';
    static final char RESIDENTIAL = 'R';
    static final char MECH = 'M';
    static final char EMPTY = '.';

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    static class Pair {
        final Cell cell;
        final int numberOfMoves;

        Pair(Cell cell, int numberOfMoves) {
            this.cell = cell;
            this.numberOfMoves = numberOfMoves;
        }
    }

    static class Cell {
        final int row;
        final int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Cell move(Direction direction) {
            switch (direction) {
                case NORTH:
                    return new Cell(row-1, col);
                case EAST:
                    return new Cell(row, col+1);
                case SOUTH:
                    return new Cell(row+1, col);
                case WEST:
                default:
                    return new Cell(row, col-1);

            }
        }

        @Override
        public boolean equals(Object that) {
            if (!(that instanceof Cell)) {
                return false;
            }
            return (this.row == ((Cell)that).row) && (this.col == ((Cell)that).col);
        }

        @Override
        public int hashCode() {
            return row*31 + col;
        }
    }

    static class Map {
        private final char[][] map;
        private final boolean[][] visited;
        private Cell godzilla;
        private final List<Cell> mechs;
        private final int m, n;
        int numberOfDestroyedCells = 0;
        int numberOfTurns = 0;

        java.util.Map<Cell, Integer> destroyed = new HashMap<>();

        Map(char[][] map, Cell godzilla, List<Cell> mechs) {
            this.m = map.length;
            this.n = map[0].length;
            this.map = map;
            this.visited = new boolean[m][n];
            this.godzilla = godzilla;
            this.mechs = mechs;


            updateGodzilla();
        }

        boolean tryMoveGodzilla() {
            return tryMoveGodzillaToResidential() || tryMoveGodzillaToUnvisited();
        }

        private boolean tryMoveGodzillaToResidential() {
            for (Direction direction : Direction.values()) {
                Cell next = godzilla.move(direction);
                if (isValidCell(next) && isResidential(next)) {
                    moveGodzilla(next);
                    destroyed.put(next, numberOfTurns);
                    numberOfDestroyedCells ++;
                    return true;
                }
            }
            return false;
        }

        private boolean tryMoveGodzillaToUnvisited() {
            for (Direction direction : Direction.values()) {
                Cell next = godzilla.move(direction);
                if (isValidCell(next) && !isVisited(next)) {
                    moveGodzilla(next);
                    return true;
                }
            }
            return false;
        }

        private void moveGodzilla(Cell next) {
            godzilla = next;
            numberOfTurns ++;
            updateGodzilla();
        }

        private boolean isValidCell(Cell cell) {
            return cell.row >= 0 && cell.row < m && cell.col >= 0 && cell.col < n;
        }

        private boolean isResidential(Cell cell) {
            return map[cell.row][cell.col] == RESIDENTIAL;
        }

        private boolean isVisited(Cell cell) {
            return visited[cell.row][cell.col];
        }

        private void updateGodzilla() {
            map[godzilla.row][godzilla.col] = EMPTY;
            visited[godzilla.row][godzilla.col] = true;
        }

        boolean canAnyMechDestroyGodzilla() {
            for (Cell mech : mechs) {
                if (canReachGodzillaWithinNumberOfTurns(mech)) {
                    return true;
                }
            }
            return false;
        }

        private boolean canReachGodzillaWithinNumberOfTurns(Cell mech) {
            if (getDistance(mech, godzilla) > numberOfTurns) {
                return false;
            }

            Deque<Pair> deque = new ArrayDeque<>();
            boolean[][] visited = new boolean[m][n];
            CellRange range = getCellRange(godzilla);

            deque.add(new Pair(mech, 0));
            visited[mech.row][mech.col] = true;

            while (deque.peek() != null) {
                Pair current = deque.poll();
                if (range.isWithinBoundary(current.cell)) {
                    return true;
                }
                if (current.numberOfMoves == numberOfTurns) {
                    continue;
                }
                for (Direction direction : Direction.values()) {
                    Cell next = current.cell.move(direction);
                    if (isValidCell(next) && !visited[next.row][next.col] && canMechMoveToCellAtNextTurn(next, current.numberOfMoves)) {
                        deque.add(new Pair(next, current.numberOfMoves+1));
                        visited[next.row][next.col] = true;
                    }
                }
            }
            return false;
        }

        private int getDistance(Cell from, Cell to) {
            return Math.abs(from.row - to.row) + Math.abs(from.col - to.col);
        }

        private boolean canMechMoveToCellAtNextTurn(Cell cell, int turn) {
            if (isResidential(cell)) {
                return false;
            }

            if (destroyed.getOrDefault(cell, -1) > turn) {
                return false;
            }

            return true;
        }

        /*
         * timeout
        List<Cell> destroyed = new ArrayList<>(); // record destroyed cell at step i, it could be null

        private boolean canMechMoveToCellAtNextTurn(Cell cell, int turn) {
            if (isResidential(cell)) {
                return false;
            }

            // cell is (residential) destroy from turn+1 onwards, thus it cannot be reached by mech at this turn
            for (int i = turn+1; i <= numberOfTurns; i++) {
                if (cell.equals(destroyed.get(i))) {
                    return false;
                }
            }

            return true;
        }*/

        private CellRange getCellRange(Cell cell) {
            CellRange range = new CellRange();
            range.center = cell;

            java.util.Map<Direction, Cell> boundaryMap = new HashMap<>();
            for (Direction direction : Direction.values()) {
                Cell boundary = cell;
                while(true) {
                    Cell next = boundary.move(direction);
                    if (!isValidCell(next) || isResidential(next)) {
                        boundaryMap.put(direction, boundary);
                        break;
                    } else {
                        boundary = next;
                    }
                }
            }

            range.minRow = boundaryMap.get(Direction.NORTH).row;
            range.maxRow = boundaryMap.get(Direction.SOUTH).row;
            range.minCol = boundaryMap.get(Direction.WEST).col;
            range.maxCol = boundaryMap.get(Direction.EAST).col;
            return range;
        }

        class CellRange {
            Cell center;
            int minRow, minCol, maxRow, maxCol;

            boolean isWithinBoundary(Cell cell) {
                return (cell.row == center.row && cell.col >= minCol && cell.col <= maxCol)
                        || (cell.col == center.col && cell.row >= minRow && cell.row <= maxRow);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = scanner.nextInt();
        for (int i = 0; i < test; i++) {
            int cols = scanner.nextInt();
            int rows = scanner.nextInt();
            scanner.nextLine();
            char[][] map = new char[rows][cols];
            Cell godzilla = null;
            List<Cell> mechs = new ArrayList<>();

            for (int row = 0; row < rows; row++) {
                String line = scanner.nextLine();
                for (int col = 0; col < cols; col++) {
                    map[row][col] = line.charAt(col);
                    if (map[row][col] == GODZILLA) {
                        godzilla = new Cell(row, col);
                    } else if (map[row][col] == MECH) {
                        mechs.add(new Cell(row, col));
                    }
                }
            }

            System.out.println(getMinNumberOfDestroyedCells(new Map(map, godzilla, mechs)));
        }
    }

    static int getMinNumberOfDestroyedCells(Map map) {
        while (true) {
            if (!map.tryMoveGodzilla()) {
                return map.numberOfDestroyedCells;
            }

            if (map.canAnyMechDestroyGodzilla()) {
                return map.numberOfDestroyedCells;
            }
        }
    }
}

