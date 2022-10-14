package minima;

/**
 * https://leetcode.com/problems/trapping-rain-water-ii/
 *
 * Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map,
 * return the volume of water it can trap after raining.
 *
 * Solution: This is extension problem of trapping rain water 2D
 * Consider each row, we can find the water level holdable at each block (column)
 * This level can be kept if the block in front and behind this block (same column, but front and back row) are high enough
 * In other words, the water kept at this block = min (level, height of block in front, height of block in back)
 */
public class TrappingRainWater3D {

    public static void main(String[] args) {
        //int[][] array = {{1,4,3,1,3,2}, {3,2,1,3,2,4}, {2,3,3,2,3,1}};//4
        int[][] array = {{3,3,3,3,3},{3,2,2,2,3},{3,2,1,2,3},{3,2,2,2,3},{3,3,3,3,3}};//10
        System.out.println(getTrappedWater(array));
    }

    public static int getTrappedWater(int[][] array) {
        int total = 0;
        for (int i = 0; i < array.length; i ++) {
            int[] row = array[i];

            if (row.length <= 2) {
                continue;
            }
            int leftMax = row[0], rightMax = row[row.length-1];
            int lo = 1, hi = row.length -2;

            while (lo <= hi) {
                if (leftMax < rightMax) {
                    if (row[lo] < leftMax) {
                        int level = leftMax - row[lo];
                        total += getActualLevel(array, i, lo, level);
                    } else {
                        leftMax = row[lo];
                    }
                    lo++;
                } else {
                    if (row[hi] < rightMax) {
                        int level = rightMax - row[hi];
                        total += getActualLevel(array, i, hi, level);
                    } else {
                        rightMax = row[hi];
                    }
                    hi--;
                }
            }
        }

        return total;
    }

    public static int getActualLevel(int[][] array, int row, int column, int level) {
        // the water at the edge cannot be held
        if (row == 0 || row == array.length - 1) {
            return 0;
        }
        int front = array[row-1][column];
        int back = array[row+1][column];
        return Math.min(level, Math.min(front, back));
    }
}
