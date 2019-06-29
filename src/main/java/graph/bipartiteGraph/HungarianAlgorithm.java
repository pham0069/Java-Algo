package graph.bipartiteGraph;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/hungarian-algorithm-assignment-problem-set-1-introduction/
 * https://brilliant.org/wiki/hungarian-matching/
 * https://en.wikipedia.org/wiki/Hungarian_algorithm#Matrix_interpretation
 *
 * Let there be n agents and n tasks.
 * Any agent can be assigned to perform any task, incurring some cost that may vary depending on the agent-task assignment.
 * It is required to perform all tasks by assigning exactly one agent to each task
 * and exactly one task to each agent in such a way that the total cost of the assignment is minimized.
 *
 * Brute force solution is to consider every possible assignment
 * Time complexity is O(n!).
 *
 * Hungarian algorithm, utilizes the following theorem:
 * If a number is added to or subtracted from all of the entries
 * of any one row or column of a cost matrix,
 * then an optimal assignment for the resulting cost matrix is also an optimal assignment for the original cost matrix.
 *
 */
public class HungarianAlgorithm {
    static int[][] test1() {
        return new int[][]{{25, 40, 35}, {40, 60, 35}, {20, 40, 25} };
    }

    static int[][] test2() {
        return new int[][]{{15, 40, 45}, {20, 60, 35}, {20, 40, 25} };
    }

    static int[][] test3() {
        return new int[][]{{108, 125, 150}, {150, 135, 175}, {122, 148, 250} };
    }

    public static void main(String[] args) {
        //getMinCost(test1());
        getMinCost(test3());
    }

    static void getMinCost(int[][] originalCost) {
        int[][] cost = copy(originalCost);

        reduce(cost);
        while(true) {
            List<Set<Integer>> coverLines = coverZerosWithMinLines(cost);
            Set<Integer> coverRows = coverLines.get(0);
            Set<Integer> coverCols = coverLines.get(1);

            // this means total number of cover rows and columns = n
            if (coverRows.size() + coverCols.size() == cost.length) {
                List<Entry> selection = getOptimalSelection(cost, coverRows, coverCols);
                printResult(originalCost, selection);
                break;
            } else {
                adjust(cost, coverRows, coverCols);
            }
        }
    }

    static void printResult(int[][] cost, List<Entry> selection) {
        int totalCost = 0;
        for (Entry entry : selection) {
            System.out.println(String.format("[%d,%d] %d", entry.row, entry.col, cost[entry.row][entry.col]));
            totalCost += cost[entry.row][entry.col];
        }
        System.out.println("Min cost = " + totalCost);
    }

    static int[][] copy(int[][] cost) {
        int[][] copy = new int[cost.length][cost[0].length];
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost[0].length; j++) {
                copy[i][j] = cost[i][j];
            }
        }
        return copy;
    }
    static void reduce(int[][] cost) {
        int n = cost.length;
        int rowMin, colMin;

        // subtract row min from each item on the row
        for (int i = 0; i < n; i++) {
            // find row min
            rowMin = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                rowMin = Math.min(rowMin, cost[i][j]);
            }

            // subtract row min
            for (int j = 0; j < n; j++) {
                cost[i][j] -= rowMin;
            }
        }

        // subtract col min from each item in the column
        for (int i = 0; i < n; i++) {
            //find col min
            colMin = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                colMin = Math.min(colMin, cost[j][i]);
            }

            // subtract col min
            for (int j = 0; j < n; j++) {
                cost[j][i] -= colMin;
            }
        }
    }

    /**
     * Follow the Wikipedia strategy
     * 0. First, attempt to assign a zero per row as many as possible.
     * 1. Mark all rows having no assignments.
     * 2. Mark all columns having zeros in newly marked row(s).
     * 3. Mark all rows having assignments in newly marked columns (row 1).
     * 4. Repeat the steps 2 and 3 until there are no new rows or columns being marked.
     *
     */
    static List<Set<Integer>> coverZerosWithMinLines(int[][] cost) {
        int n = cost.length;
        int[] markedRows = new int[n];
        int[] markedCols = new int[n];

        int[][] assignment = new int[n][n];
        Set<Integer> occupiedCols = new HashSet<>();

        Set<Integer> newlyMarkedRows = new HashSet<>();
        Set<Integer> newlyMarkedCols = new HashSet<>();

        // assign zero on each row and mark unassigned rows
        for (int i = 0; i < n; i++) {
            boolean assigned = false;
            for (int j = 0; j < n; j++) {
                if (cost[i][j] == 0 && !occupiedCols.contains(j)) {
                    assignment[i][j] = 1;
                    occupiedCols.add(j);
                    assigned = true;
                    break;
                }
            }
            if (!assigned) {
                newlyMarkedRows.add(i);
                markedRows[i] = 1;
            }
        }

        // loop to mark columns and rows until all zeros are covered
        while (true) {
            boolean newMark = false;
            for (int i : newlyMarkedRows) {
                for (int j = 0; j < n; j++) {
                    if (cost[i][j] == 0 && markedCols[j] == 0) {
                        newlyMarkedCols.add(j);
                        markedCols[j] = 1;
                        newMark = true;
                    }
                }
            }

            if (!newMark) {
                break;
            }

            newlyMarkedRows.clear();

            newMark = false;
            for (int i : newlyMarkedCols) {
                for (int j = 0; j < n; j++) {
                    if (cost[j][i] == 0 && assignment[j][i] == 1 && markedRows[j] == 0) {
                        newlyMarkedRows.add(j);
                        markedRows[j] = 1;
                        newMark = true;
                    }
                }
            }

            if (!newMark) {
                break;
            }

            newlyMarkedCols.clear();
        }

        Set<Integer> coverRows = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (markedRows[i] == 0) {
                coverRows.add(i);
            }
        }
        Set<Integer> coverCols = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (markedCols[i] == 1) {
                coverCols.add(i);
            }
        }
        return Lists.newArrayList(coverRows, coverCols);
    }

    static void adjust(int[][] cost, Set<Integer> coverRows, Set<Integer> coverCols) {
        int n = cost.length;

        // find min number among the uncovered items
        int minUncovered = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (!coverRows.contains(i)) {
                for (int j = 0; j < n; j++) {
                    if (!coverCols.contains(j)) {
                        minUncovered = Math.min(minUncovered, cost[i][j]);
                    }
                }
            }
        }

        //subtract minUncovered from all uncovered rows
        for (int i = 0; i < n; i++) {
            if (!coverRows.contains(i)) {
                for (int j = 0; j < n; j++) {
                    cost[i][j] -= minUncovered;
                }
            }
        }

        //add minUncovered to all covered columns
        for (int i = 0; i < n; i++) {
            if (coverCols.contains(i)) {
                for (int j = 0; j < n; j++) {
                    cost[j][i] += minUncovered;
                }
            }
        }
    }

    @Data
    static class Entry {
        final int row;
        final int col;
    }

    /**
     * First assign a zero to each row in coverRows (that are not in coverCols)
     * Try to assign rows with single zeros first
     * If all rows have at least 2 zeros available, choose a random col
     * And continue until finding zeros for all rows
     *
     * Second assign a zero to each col in coverCols (that are not in coverRows)
     */
    static List<Entry> getOptimalSelection(int[][] cost, Set<Integer> coverRows, Set<Integer> coverCols) {
        int n = cost.length;
        List<Entry> result = new ArrayList<>();

        Map<Integer, Set<Integer>> map = getZeroCols(cost, coverRows, coverCols);
        Set<Integer> occupiedCols = new HashSet<>();
        while (result.size() < coverRows.size()) {
            boolean assigned = false;
            Iterator<Map.Entry<Integer, Set<Integer>>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                int row = iter.next().getKey();
                Set<Integer> cols = map.get(row);
                cols.removeAll(occupiedCols);
                if (cols.size() == 1) {
                    int col = map.get(row).iterator().next();

                    result.add(new Entry(row, col));
                    occupiedCols.add(col);
                    iter.remove();
                    assigned = true;
                }
            }
            if (!assigned) {
                int row = map.keySet().iterator().next();
                int col = map.get(row).iterator().next();

                result.add(new Entry(row, col));
                occupiedCols.add(col);
                map.remove(row);
            }
        }

        map = getZeroRows(cost, coverCols, coverRows);
        Set<Integer> occupiedRows = new HashSet<>();
        while (result.size() < n) {
            boolean assigned = false;
            Iterator<Map.Entry<Integer, Set<Integer>>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                int col = iter.next().getKey();
                Set<Integer> rows = map.get(col);
                for (int i : occupiedRows) {
                    rows.add(i);
                }
                rows.removeAll(occupiedRows);

                if (rows.size() == 1) {
                    int row = map.get(col).iterator().next();

                    result.add(new Entry(row, col));
                    occupiedRows.add(row);
                    iter.remove();
                    assigned = true;
                }
            }
            if (!assigned) {
                int col = map.keySet().iterator().next();
                int row = map.get(col).iterator().next();

                result.add(new Entry(row, col));
                occupiedRows.add(row);
                map.remove(col);
            }
        }
        return result;
    }

    static Map<Integer, Set<Integer>> getZeroCols(int[][] cost, Set<Integer> rows, Set<Integer> excludeCols) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i : rows) {
            Set<Integer> cols = new HashSet<>();
            for (int j = 0; j < cost[0].length; j++) {
                if (cost[i][j] == 0 && !excludeCols.contains(j)) {
                    cols.add(j);
                }
            }
            map.put(i, cols);
        }
        return map;
    }

    static Map<Integer, Set<Integer>> getZeroRows(int[][] cost, Set<Integer> cols, Set<Integer> excludeRows) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i : cols) {
            Set<Integer> rows = new HashSet<>();
            for (int j = 0; j < cost.length; j++) {
                if (cost[j][i] == 0 && !excludeRows.contains(j)) {
                    rows.add(j);
                }
            }
            map.put(i, rows);
        }
        return map;
    }


}
