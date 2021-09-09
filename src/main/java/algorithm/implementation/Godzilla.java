package algorithm.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Outpost 24
 *
 * Godzilla is rampaging through Tokyo again. Luckily, the Superior Defender Mech Force (SDMF) has sent its mech units to stop Godzilla’s attack. Mech units are gigantic bipedal robots, usually piloted by Japanese teenagers, that carry weapons of superior destruction. The weapons are so powerful that one hit should neutralize the Godzilla threat.
 *
 * The SDMF faces two challenges. First, the mech units are so big that they cannot walk over any residential sectors, as they would then trample the people of Tokyo. Second, the weapons of the mech units are so powerful that the pilots cannot afford to fire them at Godzilla while there are any residential sectors between the firing mech and Godzilla.
 *
 * The SDMF wants to run some simulations before it faces the Godzilla threat. The simulations are based on a two-dimensional map of the area of Tokyo where Godzilla is running amock. The passing of time happens between rounds, where in each round there are two turns. In the first turn, Godzilla gets to do a move. In the second turn, the mech units are allowed a move. In a single move, Godzilla moves one sector on the map in the directions North, East, South or West.
 *
 * Godzilla is lacking in brain matter and has a very predictable movement scheme. First, Godzilla does not move into a sector that he already visited. Each round, on the first turn:
 *
 * It looks for any houses to destroy that are adjacent to him. If the sector adjacent to him is a residential sector, it will move to that sector and destroy the sector. When a residential sector is destroyed, it becomes a ruined sector instead. Godzilla looks around him in the order North, East, South, West. So, it will first look to the North to see whether there is a residential sector. If so, it will move there. Otherwise, he will look East to see if there is a residential sector to destroy, and so on.
 *
 * In the event that there are no residential sectors adjacent to Godzilla, he will try to move North. If that move would move him outside the map or into a sector he already visited, he will instead try to move East. If he cannot move East, he will move South. In the case that he cannot move South, he will try to move West instead. The terrifying presence of a mech will not stop Godzilla from moving into the sector that is occupied by a mech unit. In that case, Godzilla is considered to be in range of the mech unit’s weapons of superior destruction, in the next turn.
 *
 * If Godzilla has no move options left, he will instead start roaring and wail his arms, inflicting no damage to Tokyo city.
 *
 * Each round, on the second turn, each mech unit can either stay at its position, or move to an adjacent non-residential sector, or a ruined sector. It cannot move outside the map. When moving, a mech unit moves one sector in one of the directions North, East, South or West. It is allowed to move into the space of another mech unit. At the moment that a mech unit is able to fire its weapons at Godzilla, it will do so on the mech units’ turn. Mech units can move and fire in the same turn. A mech can fire its weapons at Godzilla if there is a straight horizontal or vertical line between the mech and Godzilla, and the line is not obstructed by any residential sectors.
 *
 * Given a map of the Tokyo area where Godzilla is rampaging and the starting location of the mechs, determine the minimum number of residential sectors that will be destroyed before Godzilla can be neutralized by a mech unit.
 *
 *
 *
 * Input
 * The input starts with a line containing an integer 𝑇, the number of test cases. Then for each test case:
 *
 * One line containing two space-separated integers 𝐿 (3≤𝐿≤1000) and 𝑊 (3≤𝑊≤1000), the length and width of the map of Tokyo in sectors, respectively.
 *
 * 𝑊 lines, containing 𝐿 characters each, describing the map of Tokyo. The characters are:
 *
 * ‘.’: a non-residential sector;
 *
 * ‘R’: a residential sector;
 *
 * ‘G’: the starting position of Godzilla. This is always a non-residential sector;
 *
 * ‘M’: the starting position of a mech unit. This is always a non-residential sector.
 *
 * The sector at the top-left corner of the map represents the North-West corner of Tokyo, while the sector at the bottom-right corner of the map represents the South-East corner of Tokyo.
 *
 * The simulation will contain at most 100 mech units.
 *
 * It will always be possible for Godzilla to be neutralized by a mech unit.
 *
 *
 *
 * Output
 * For each test case, output one line containing a single integer: the minimum number of residential sectors that will be destroyed before Godzilla can be neutralized by a mech unit.
 *
 *
 * Sample input
 5
 3 3
 RR.
 G..
 M.R
 7 5
 M...RR.
 ...G...
 ...RRR.
 .......
 ..RR..M
 5 2
 M.RGR
 ..RRR
 5 2
 M.RGR
 ...RR
 5 2
 M.RGR
 ....R

 * Sample output
 *
 1
 2
 4
 3
 2
 */

// no timeout due to saving mech traversal instead of traversing from the beginning when testing each round

public class Godzilla {
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
        private final int m, n;

        private final char[][] map;
        private final boolean[][] godzillaVisited;
        private Cell godzilla;

        private final List<List<Cell>> mechTraversal;
        private final boolean[][] mechVisited;
        private final List<Cell> mechs;

        int numberOfDestroyedCells = 0;
        int numberOfRounds = 0;
        boolean destroyedInThisRound = false;

        Map(char[][] map, Cell godzilla, List<Cell> mechs) {
            this.m = map.length;
            this.n = map[0].length;
            this.map = map;

            this.godzillaVisited = new boolean[m][n];
            this.godzilla = godzilla;
            initializeGodzilla();

            this.mechVisited = new boolean[m][n];
            this.mechs = mechs;
            this.mechTraversal = new ArrayList<>();
            initializeMechTraversal();
        }

        int getMinNumberOfDestroyedCells() {
            while (true) {
                if (!tryMoveGodzilla()) {
                    return numberOfDestroyedCells;
                }

                if (canAnyMechDestroyGodzilla()) {
                    return numberOfDestroyedCells;
                }
            }
        }

        private void initializeGodzilla() {
            updateGodzilla();
        }

        private void initializeMechTraversal() {
            mechTraversal.add(mechs); // number of moves = 0
            for (Cell mech : mechs) {
                mechVisited[mech.row][mech.col] = true;
            }
        }

        boolean tryMoveGodzilla() {
            destroyedInThisRound = false;
            return tryMoveGodzillaToResidential() || tryMoveGodzillaToUnvisited();
        }

        private boolean tryMoveGodzillaToResidential() {
            for (Direction direction : Direction.values()) {
                Cell next = godzilla.move(direction);
                if (isValidCell(next) && isResidential(next)) {
                    moveGodzilla(next);
                    numberOfDestroyedCells ++;
                    destroyedInThisRound = true;
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
            numberOfRounds++;
            updateGodzilla();
        }

        private boolean isValidCell(Cell cell) {
            return cell.row >= 0 && cell.row < m && cell.col >= 0 && cell.col < n;
        }

        private boolean isResidential(Cell cell) {
            return map[cell.row][cell.col] == RESIDENTIAL;
        }

        private boolean isVisited(Cell cell) {
            return godzillaVisited[cell.row][cell.col];
        }

        private void updateGodzilla() {
            map[godzilla.row][godzilla.col] = EMPTY;
            godzillaVisited[godzilla.row][godzilla.col] = true;
        }

        boolean canAnyMechDestroyGodzilla() {
            CellRange range = getCellRange(godzilla);

            // check if any traversed cell becomes cleared with godzilla
            boolean existingMechsWithinBoundary = isMechTraversalWithinBoundary(range);
            if (existingMechsWithinBoundary) {
                return true;
            }

            return tryTraverseMech(range);
        }

        private boolean isMechTraversalWithinBoundary(CellRange range) {
            if (!destroyedInThisRound) {
                return false;
            }

            return mechTraversal.stream().flatMap(List::stream).anyMatch(range::isWithinBoundary);
        }

        private boolean tryTraverseMech(CellRange range) {
            List<Cell> traversedCells = new ArrayList<>();
            for (Cell cell : mechTraversal.get(numberOfRounds -1)) {
                for (Direction direction : Direction.values()) {
                    Cell next = cell.move(direction);
                    if (isValidCell(next) && !mechVisited[next.row][next.col] && !isResidential(next)) {
                        if (range.isWithinBoundary(next)) {
                            return true;
                        }
                        traversedCells.add(next);
                        mechVisited[next.row][next.col] = true;
                    }
                }
            }
            mechTraversal.add(traversedCells);
            return false;
        }

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

            System.out.println(new Map(map, godzilla, mechs).getMinNumberOfDestroyedCells());
        }
    }
}